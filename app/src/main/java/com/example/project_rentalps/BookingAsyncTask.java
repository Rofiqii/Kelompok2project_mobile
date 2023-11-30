package com.example.project_rentalps;

import android.os.AsyncTask;
import android.util.Log;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookingAsyncTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String urlString = "https://rentalpowergames.my.id/API/booking.php";
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);

            // Write JSON data to the output stream
            try (OutputStream outputStream = urlConnection.getOutputStream()) {
                outputStream.write(params[0].getBytes());
                outputStream.flush();
            }

            // Get the response from the server
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                // This is a simplified example; you might want to handle the response more robustly
                return "Booking successful!";
            } else {
                return "Booking failed. HTTP Code: " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error during booking: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // Log the result for debugging
        Log.d("BookingAsyncTask", "Server Response: " + result);

        // You can handle the result as needed
        // For example, you might want to notify the user or perform additional actions

        // If booking is successful, you can navigate to another activity or perform additional actions
        if (result.equals("Booking successful!")) {
            // Example: Start a new activity
            // Intent intent = new Intent(context, YourNextActivity.class);
            // context.startActivity(intent);
        }
    }
}
