package net.servokio.q4mc.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Resolve {
    public static ServerInfo getServerInfo(String addr){
        try{

            StringBuilder result = new StringBuilder();
            URL url = new URL("https://mcapi.us/server/status?ip="+addr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()))) {
                    for (String line; (line = reader.readLine()) != null; ) {
                        result.append(line);
                    }
            }

            int responseCode = conn.getResponseCode();
            if(responseCode == 200){
                JsonObject jp = JsonParser.parseString(result.toString()).getAsJsonObject();
                return new ServerInfo(jp);
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

