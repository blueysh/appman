package me.blueysh.appman.command;

import lineman.Command;
import lineman.Lineman;
import lineman.Logger;
import me.blueysh.appman.util.DataGetter;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;

public class UninstallCommand implements Command {
    @Override
    public void run(Lineman lineman, String[] strings) {
        if (strings.length < 1) {
            lineman.getLogger().err("No app id provided. Usage: appman uninstall [app id]");
            return;
        }
        String appId = strings[0];
        Logger logger = lineman.getLogger();
        logger.println(Logger.Color.RED + "UNINSTALL " + Logger.Color.RESET + "'" + appId + "'");
        logger.info("Getting info for '%s'...".formatted(appId));

        JSONObject data = DataGetter.getData(lineman, appId);

        if (data == null) {
            logger.err("Data for '%s' could not be downloaded, or none exists.".formatted(appId));
            return;
        }

        String appName = data.getString("appName");
        String appSupportDirName = data.getString("appSupportDirName");

        logger.success("Found '%s' by '%s'.".formatted(appName, data.getString("developerName")));

        try {
            String appPath = "/Applications/%s.app".formatted(appName);
            String appSupportPath = System.getProperty("user.home") + "/Library/Application Support/%s".formatted(appSupportDirName);

            // Delete the app directory
            deleteDirectory(appPath);
            // Delete the app support directory
            deleteDirectory(appSupportPath);

            logger.success("'%s' has been uninstalled.".formatted(appName));
        } catch (IOException e) {
            logger.severe("An unexpected exception was encountered: " + e.getMessage());
        }
    }

    private void deleteDirectory(String path) throws IOException {
        Path directoryPath = Path.of(path);
        if (Files.exists(directoryPath)) {
            Files.walk(directoryPath)
                    .map(Path::toFile)
                    .sorted((f1, f2) -> -f1.compareTo(f2))
                    .forEach(File::delete);
        }
    }
}