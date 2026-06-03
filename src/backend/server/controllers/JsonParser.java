package backend.server.controllers;

import java.util.HashMap;
import java.util.Map;

public class JsonParser {

    // parser simplu (fără librării externe)
    public static Map<String, String> parse(String json) {

        Map<String, String> map = new HashMap<>();

        json = json.replace("{", "").replace("}", "").replace("\"", "");

        String[] pairs = json.split(",");

        for (String pair : pairs) {
            String[] kv = pair.split(":");
            if (kv.length == 2) {
                map.put(kv[0].trim(), kv[1].trim());
            }
        }

        return map;
    }
}