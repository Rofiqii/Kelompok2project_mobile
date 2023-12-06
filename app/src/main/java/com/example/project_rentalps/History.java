package com.example.project_rentalps;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_rentalps.data.CustomerData;
import com.example.project_rentalps.data.HistoryData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    private ListView historyListView;
    private List<HistoryData> historyList;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyListView = findViewById(R.id.historyListView);
        historyList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(History.this);

        // Fetch history data from API
        JSONObject jsonHistory = new JSONObject();
        try {
            jsonHistory.put("username_cus", CustomerData.getUsername());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            getHistoryData();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void displayHistoryList() {
        HistoryAdapter adapter = new HistoryAdapter(this, historyList);
        historyListView.setAdapter(adapter);
    }

    private void getHistoryData() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("username_cus", CustomerData.getUsername());

        JsonArrayRequest historyRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://rentalpowergames.my.id/API/histori.php?username_cus=" + CustomerData.getUsername(),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject history = response.getJSONObject(i);

                                historyList.add(new HistoryData(
                                    history.getString("username_cus"),
                                    history.getString("id_pemesanan"),
                                    history.getString("id_ps"),
                                    history.getString("waktu_awal"),
                                    history.getString("waktu_akhir"),
                                    history.getString("total_harga"),
                                    history.getString("tanggal"),
                                    history.getString("status")
                                ));

                                displayHistoryList();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Anjays", error.toString());
                    }
                }
        );

        requestQueue.add(historyRequest);
    }

//    private void parseHistoryData()
}
