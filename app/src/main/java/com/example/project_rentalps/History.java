package com.example.project_rentalps;

//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//public class History extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_history);
//    }
//}
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class History extends AppCompatActivity {
    private TextView textViewTitle;
    private ListView listViewHistory;
    private Button buttonClearHistory;
    private Button buttonBack;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        textViewTitle = findViewById(R.id.textViewTitle);
        listViewHistory = findViewById(R.id.listViewHistory);
        buttonClearHistory = findViewById(R.id.buttonClearHistory);
        buttonBack = findViewById(R.id.buttonBack);
        buttonLogout = findViewById(R.id.buttonLogout);

        // Mengatur warna latar belakang
        findViewById(android.R.id.content).setBackgroundColor(Color.parseColor("#610C9F"));

        // Menjalankan AsyncTask untuk mengambil data dari API
        new FetchDataTask().execute("https://rentalpowergames.my.id/API/histori.php");

        // Implementasi logika tombol
        buttonClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tambahkan logika clear history di sini
                Toast.makeText(History.this, "Clear History Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the registration activity
                Intent intent = new Intent(History.this, booking.class);
                startActivity(intent);
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tambahkan logika logout di sini
                logout();
            }
        });
    }

    private void logout() {
        // Tambahkan logika logout di sini
        // Misalnya, hapus informasi login dari SharedPreferences dan arahkan ke halaman login
        // Pastikan untuk menyesuaikan dengan kebutuhan aplikasi Anda
        Toast.makeText(this, "Logout Clicked", Toast.LENGTH_SHORT).show();

        // Untuk contoh, arahkan kembali ke halaman login
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish(); // Tutup activity saat ini agar pengguna tidak dapat kembali ke halaman sebelumnya
    }

    private class FetchDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return fetchData(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve data. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONArray jsonArray = new JSONArray(result);

                // Jika ada data histori, tampilkan judul dan daftar pada ListView
                if (jsonArray.length() > 0) {
                    JSONObject jsonObject = jsonArray.getJSONObject(0); // Ambil data pertama sebagai contoh
                    String username = jsonObject.getString("username");
                    textViewTitle.setText("History Pemesanan - " + username);

                    // Ambil data histori
                    String idPemesanan = jsonObject.getString("id_pemesanan");
                    String idPs = jsonObject.getString("id_ps");
                    String waktuAwal = jsonObject.getString("waktu_awal");
                    String waktuAkhir = jsonObject.getString("waktu_akhir");
                    String totalHarga = jsonObject.getString("total_harga");
                    String tanggal = jsonObject.getString("tanggal");
                    String status = jsonObject.getString("status");

                    // Menampilkan data pada ListView
                    String[] historyItems = {
                            "ID Pemesanan: " + idPemesanan,
                            "ID PS: " + idPs,
                            "Waktu Awal: " + waktuAwal,
                            "Waktu Akhir: " + waktuAkhir,
                            "Total Harga: " + totalHarga,
                            "Tanggal: " + tanggal,
                            "Status: " + status
                    };

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(History.this,
                            android.R.layout.simple_list_item_1, historyItems);
                    listViewHistory.setAdapter(adapter);
                } else {
                    // Jika tidak ada data histori, beri tahu pengguna
                    textViewTitle.setText("History Pemesanan - Tidak ada data histori.");
                }

            } catch (JSONException e) {
                Log.e("HistoryActivity", "Error parsing JSON", e);
            }
        }

        private String fetchData(String urlString) throws IOException {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } finally {
                urlConnection.disconnect();
            }

            return result.toString();
        }
    }
}
