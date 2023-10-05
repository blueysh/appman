package me.blueysh.appman.util;

import lineman.Lineman;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class DataGetter {
    public static JSONObject getData(Lineman lineman, String appId) {
        try {
            HttpsURLConnection conn = (HttpsURLConnection) new URL("https://assets.blueysh.me/lib/appman/app/%s.json".formatted(appId)).openConnection();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder rawData = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    rawData.append(line);
                }
                return new JSONObject(rawData.toString());
            }
        } catch (IOException e) {
            // May handle the exception appropriately later, e.g., log it or throw it
            // lineman.getLogger().severe("An unexpected error was encountered. No changes made.");
            return null;
        }
    }
}
