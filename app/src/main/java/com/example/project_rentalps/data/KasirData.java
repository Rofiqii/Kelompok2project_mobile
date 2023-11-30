package com.example.project_rentalps.data;

import org.json.JSONException;
import org.json.JSONObject;

public class KasirData {
    private static String id;
    private static String username;

    public static void parseResponse(JSONObject response) {
        try {
            id = response.getString("id");
            username = response.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String getId() {
        return id;
    }

    public static String getUsername() {
        return username;
    }
}
