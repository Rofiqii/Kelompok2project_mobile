package com.example.project_rentalps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Register extends AppCompatActivity {

    private EditText etUsername, etNama, etPassword, etPertanyaan, etJawaban;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etNama = findViewById(R.id.etNama);
        etPassword = findViewById(R.id.etPassword);
        etPertanyaan = findViewById(R.id.etPertanyaan);
        etJawaban = findViewById(R.id.etJawaban);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve user input
                String username = etUsername.getText().toString().trim();
                String nama = etNama.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String pertanyaan = etPertanyaan.getText().toString().trim();
                String jawaban = etJawaban.getText().toString().trim();

                // Create a JSON object with the user input
                JSONObject jsonInput = new JSONObject();
                try {
                    jsonInput.put("username_cus", username);
                    jsonInput.put("fullname_cus", nama);
                    jsonInput.put("password_cus", password);
                    jsonInput.put("pertanyaan", pertanyaan);
                    jsonInput.put("jawaban", jawaban);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Execute AsyncTask to perform registration
                new RegisterAsyncTask().execute(jsonInput.toString());
            }
        });
    }

    private class RegisterAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String urlString = "https://rentalpowergames.my.id/API/register.php";
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);

                // Write JSON data to the output stream
                OutputStream outputStream = urlConnection.getOutputStream();
                outputStream.write(params[0].getBytes());
                outputStream.flush();
                outputStream.close();

                // Get the response from the server
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response
                    // This is a simplified example; you might want to handle the response more robustly
                    return "Registration successful!";
                } else {
                    return "Registration failed. HTTP Code: " + responseCode;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Error during registration: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Display the result of registration (e.g., show a Toast message)
            // This is a simplified example; you might want to handle the result more appropriately
            // based on your application's requirements
            Toast.makeText(Register.this, result, Toast.LENGTH_SHORT).show();

            // If registration is successful, navigate to the Login activity
            if (result.equals("Registration successful!")) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();  // Optional: close the Register activity to prevent going back
            }
        }

    }
}
