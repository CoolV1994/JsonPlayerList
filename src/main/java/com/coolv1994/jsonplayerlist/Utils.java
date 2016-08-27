package com.coolv1994.jsonplayerlist;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

/**
 *
 * @author Vinnie
 */
public class Utils {
    private static final Gson gson = new Gson();
    private static IPlugin plugin;

    public static void setPlugin(IPlugin plugin) {
        Utils.plugin = plugin;
    }

    public static void postPlayerList() {
        postData("data=" + gson.toJson(plugin.getStats()));
    }

    public static void postStatus(boolean online) {
        postData("online=" + online);
    }

    public static void postData(String arguments) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(plugin.getConfigString("url"));
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("Content-Length", Integer.toString(arguments.getBytes().length));
            connection.setRequestProperty("User-Agent", plugin.getConfigString("agent"));
            connection.setUseCaches(false);

            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(arguments);
                wr.flush();
            }

            try (InputStream is = connection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = rd.readLine()) != null) {
                    if (line.isEmpty()) {
                        continue;
                    }
                    plugin.getLogger().log(Level.INFO, "Response: {0}", line);
                }
            }
        } catch (Exception e) {
            plugin.getLogger().severe(e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
