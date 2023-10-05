package me.blueysh.appman.command;

import lineman.Command;
import lineman.Lineman;
import lineman.Logger;
import me.blueysh.appman.util.DataGetter;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class InstallCommand implements Command {
    @Override
    public void run(Lineman lineman, String[] strings) {
        if (strings.length < 1) {
            lineman.getLogger().err("No app id provided. Usage: appman install [app id]");
            return;
        }

        String appId = strings[0];
        Logger logger = lineman.getLogger();
        logger.println(Logger.Color.GREEN + "INSTALL " + Logger.Color.RESET + "'" + appId + "'");
        logger.info("Getting info for '%s'...".formatted(appId));

        JSONObject data = DataGetter.getData(lineman, appId);

        if (data == null) {
            logger.err("Data for '%s' could not be downloaded, or none exists.".formatted(appId));
            return;
        }

        String appName = data.getString("appName");
        String developerName = data.getString("developerName");
        JSONObject versions = data.getJSONObject("versions");
        String intelLink = getLink(versions, "intel");
        String siliconLink = getLink(versions, "silicon");

        logger.success("Found '%s' by '%s'.".formatted(appName, developerName));
        logger.info("Versions available:");

        if (intelLink != null) {
            logger.info("  - intel");
        }
        if (siliconLink != null) {
            logger.info("  - silicon");
        }

        logger.println("Select version: ");
        Scanner input = new Scanner(System.in);
        String targetVersion = input.nextLine().toLowerCase();

        String targetVersionLink;

        switch (targetVersion) {
            case "intel" -> {
                if (intelLink == null) {
                    logger.err("'%s' has no Intel version.".formatted(appId));
                    return;
                }
                logger.info("Installing Intel version...");
                targetVersionLink = intelLink;
            }
            case "silicon" -> {
                if (siliconLink == null) {
                    logger.err("'%s' has no Apple Silicon version.".formatted(appId));
                    return;
                }
                logger.info("Installing Apple Silicon version...");
                targetVersionLink = siliconLink;
            }
            default -> {
                logger.err("Invalid version '%s'.".formatted(targetVersion));
                return;
            }
        }

        try {
            HttpsURLConnection fileDownloadConn = (HttpsURLConnection) new URL(targetVersionLink).openConnection();
            byte[] fileData = fileDownloadConn.getInputStream().readAllBytes();
            String fileName = "/Applications/appman_install-%s.%s".formatted(appId, data.getString("downloadsAs"));
            Files.write(Path.of(fileName), fileData);

            new Thread(() -> {
                try {
                    Process process = Runtime.getRuntime().exec("open " + fileName);
                    process.waitFor();
                    Files.deleteIfExists(Path.of(fileName));
                    logger.success("'%s' has been installed.".formatted(appName));
                } catch (Exception e) {
                    logger.severe("An unexpected error occurred when downloading the target file.");
                }
            }).start();
        } catch (Exception e) {
            logger.severe("An unexpected error occurred when downloading the target file.");
        }
    }

    private String getLink(JSONObject versions, String version) {
        return versions.has(version) && !versions.get(version).toString().equalsIgnoreCase("null") ? versions.getString(version) : null;
    }
}
