package com.example.project_rentalps;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_rentalps.data.CustomerData;
import com.example.project_rentalps.data.KasirData;
import com.example.project_rentalps.data.PlayStationData;


public class Login extends AppCompatActivity {

    private RequestQueue requestQueue;
    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;
    private void saveUsername(String username) {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username);
        editor.apply();
    }
    // Function to get username from SharedPreferences
    public static String getUsername(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return preferences.getString("username", "");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest psRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://rentalpowergames.my.id/API/ps.php",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            PlayStationData.parseResponse(response.getJSONArray("ps"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Anjay", error.toString());
                    }
                }
        );

        JsonObjectRequest kasirRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://rentalpowergames.my.id/API/kasir.php",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        KasirData.parseResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Anjay", error.toString());
                    }
                }
        );

        requestQueue.add(psRequest);
        requestQueue.add(kasirRequest);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    new LoginAsyncTask().execute(username, password);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the registration activity
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }

        });
    }

    private class LoginAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];

            try {
                URL url = new URL("https://rentalpowergames.my.id/API/login.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoOutput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("username_cus", username);
                jsonParam.put("password_cus", password);

                OutputStream os = urlConnection.getOutputStream();
                os.write(jsonParam.toString().getBytes());
                os.flush();

                StringBuilder response = new StringBuilder();
                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                } else {
                    response.append("Error: ").append(responseCode);
                }

                return response.toString();

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResult = new JSONObject(result);
                String status = jsonResult.getString("status");

                if ("ok".equals(status)) {
                    // Login successful, save username and navigate to BookingActivity
                    String username = jsonResult.getJSONObject("customer").getString("username_cus");
                    saveUsername(username);

                    CustomerData.parseResponse(jsonResult);

                    Intent intent = new Intent(Login.this, booking.class);
                    intent.putExtra("customer", jsonResult.getJSONObject("customer").toString());
                    startActivity(intent);
                } else {
                    // Login failed, show an error message
                    Toast.makeText(Login.this, jsonResult.getString("pesan"), Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
