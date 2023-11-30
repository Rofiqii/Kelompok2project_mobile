package com.example.project_rentalps.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayStationData {
    private static List<PlayStation> playStations = new ArrayList<>();

    public static void parseResponse(JSONArray response) {
        playStations.clear(); // Bersihkan data sebelum mengisi dengan yang baru

        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject psObject = response.getJSONObject(i);
                String id = psObject.getString("id_ps");
                String type = psObject.getString("tipe_ps");
                String price = psObject.getString("harga");

                PlayStation playStation = new PlayStation(id, type, price);
                playStations.add(playStation);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getSpinnerValue(List<PlayStation> playStations) {
        List<String> names = new ArrayList<>();
        int urutan = 0;

        for (PlayStation ps : playStations) {
            ++urutan;
            names.add(urutan + " " + ps.getType());
        }
        return names;
    }

    public static List<PlayStation> getPlayStations() {
        return playStations;
    }

    public static class PlayStation {
        private String id;
        private String type;
        private String price;

        public PlayStation(String id, String type, String price) {
            this.id = id;
            this.type = type;
            this.price = price;
        }

        public String getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public String getPrice() {
            return price;
        }
    }
}
