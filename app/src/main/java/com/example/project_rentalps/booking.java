package com.example.project_rentalps;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_rentalps.data.CustomerData;
import com.example.project_rentalps.data.KasirData;
import com.example.project_rentalps.data.PlayStationData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class booking extends AppCompatActivity {


    private Spinner spPS;
    private EditText etJamM, etJamA;
    private ImageButton btnJamM, btnJamA;
    private TextView hargaTextView;
    private Button btnPesan;
    private long harga = 0;

    private PlayStationData.PlayStation ps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Initialize UI components
        spPS = findViewById(R.id.spPS);
        etJamM = findViewById(R.id.etJamM);
        etJamA = findViewById(R.id.etJamA);
        btnJamM = findViewById(R.id.btnJamM);
        btnJamA = findViewById(R.id.btnJamA);
        hargaTextView = findViewById(R.id.hargaTextView);
        btnPesan = findViewById(R.id.btnPesan);

        // Setup Spinner with PS types
//        String[] psTypes = {"PS2", "PS3", "PS3", "PS3", "PS3", "PS3", "PS3", "PS3", "PS3", "PS3", "PS5", "PS5", "PS4", "PS5"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, psTypes);
//        spPS.setAdapter(adapter);
//
//        // Set OnItemSelectedListener for the Spinner to update the price when a PS type is selected
//        spPS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                updateTotalPrice();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // Do nothing here
//            }
//        });

        ps = PlayStationData.getPlayStations().get(0);
        List<PlayStationData.PlayStation> playStations = PlayStationData.getPlayStations();
        List<String> psTypes = PlayStationData.getSpinnerValue(playStations);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, psTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spPS.setAdapter(adapter);

        // Set OnItemSelectedListener for the Spinner to update the price when a PS type is selected
        spPS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Tampilkan hasil pemilihan tipe PlayStation
                ps = PlayStationData.getPlayStations().get(position);
                hargaTextView.setText("Rp. " + ps.getPrice());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        // Set OnClickListener for Time Pickers
        btnJamM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] jam = {Calendar.getInstance().get(Calendar.HOUR_OF_DAY)};
                final int[] menit = {Calendar.getInstance().get(Calendar.MINUTE)};
                showTimePicker(etJamM, jam, menit);
            }
        });

        btnJamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] jam = {Calendar.getInstance().get(Calendar.HOUR_OF_DAY)};
                final int[] menit = {Calendar.getInstance().get(Calendar.MINUTE)};
                showTimePicker(etJamA, jam, menit);
            }
        });

        // Set OnClickListener for "Pesan Sekarang" button
        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check for valid input before proceeding
                if (isValidInput()) {
                    // Perform booking
                    performBooking();
                    Intent intent = new Intent(booking.this, History.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(booking.this, "Invalid input. Please check your selection.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Time Picker method
    private void showTimePicker(final EditText editText, final int[] jam, final int[] menit) {
        Calendar calendar = Calendar.getInstance();
        jam[0] = calendar.get(Calendar.HOUR_OF_DAY);
        menit[0] = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(booking.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                jam[0] = hourOfDay;
                menit[0] = minute;

                // Format the time and set it to the corresponding EditText
                String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", jam[0], menit[0]);
                editText.setText(formattedTime);

                // Update the total price when the time is set
                updateTotalPrice();
            }
        }, jam[0], menit[0], true);

        dialog.show();
    }

    // Update the total price based on the selected PS type and time duration
    private void updateTotalPrice() {
        int pricePerHour = Integer.parseInt(ps.getPrice());

        String startTime = etJamM.getText().toString();
        String endTime = etJamA.getText().toString();

        long durationInMinutes = calculateDurationInMinutes(startTime, endTime);
        long totalPrice = (durationInMinutes * pricePerHour) / 60;

        // Update the TextView with the calculated price
        harga = totalPrice;
        hargaTextView.setText("Harga: " + totalPrice);
    }

    // Placeholder method to calculate the duration in hours
    private long calculateDurationInMinutes(String startTime, String endTime) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");

            Date date1 = format.parse(startTime);
            Date date2 = format.parse(endTime);

            long difference = date2.getTime() - date1.getTime();

            return(difference / 1000) / 60;
        } catch (ParseException e) {
            Log.e(":Anjay", e.getMessage());
        }

        return 0;
    }

    // Check if the input is valid (PS type selected and valid time range)
    private boolean isValidInput() {
        return spPS.getSelectedItemPosition() != AdapterView.INVALID_POSITION &&
                !etJamM.getText().toString().isEmpty() &&
                !etJamA.getText().toString().isEmpty();
    }

    // Perform booking by executing AsyncTask
    private void performBooking() {
        String selectedPsType = spPS.getSelectedItem().toString();
        String startTime = etJamM.getText().toString();
        String endTime = etJamA.getText().toString();

        // Create a JSON object with the booking data
        JSONObject jsonBookingData = new JSONObject();
        try {
            // Add necessary data to the JSON object
            jsonBookingData.put("id_ps", ps.getId());
            jsonBookingData.put("username", KasirData.getUsername()); // Now it retrieves the saved username
            jsonBookingData.put("username_cus", CustomerData.getUsername()); // Replace with actual method// Replace with actual method
            jsonBookingData.put("waktu_awal", startTime);
            jsonBookingData.put("waktu_akhir", endTime);
            jsonBookingData.put("harga", harga);
            jsonBookingData.put("status", "MENUNGGU KONFIRMASI");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Execute AsyncTask to perform booking
        new BookingAsyncTask().execute(jsonBookingData.toString());
    }


    // AsyncTask to handle booking
    private class BookingAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String urlString = "https://rentalpowergames.my.id/API/booking.php";
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

            // Display the result of booking using Toast
            Toast.makeText(booking.this, result, Toast.LENGTH_SHORT).show();

            // If booking is successful, you can navigate to another activity or perform additional actions
            if (result.equals("Booking successful!")) {
                // Example: Start a new activity
                Intent intent = new Intent(booking.this, History.class);
                startActivity(intent);
                finish();  // Optional: close the Booking activity to prevent going back
            }
        }
    }


    // Placeholder methods (replace with actual implementations)
    private int getPsIdFromType(String psType) {
        // Implement this method to get the ID of the PS type from your data source
        // For now, return a placeholder value
        return 1;
    }

    private String getUsername() {
        // Implement this method to get the username from your data source
        // For now, return a placeholder value
        return "user123";
    }

    private String getCustomerUsername() {
        // Implement this method to get the customer's username from your data source
        // For now, return a placeholder value
        return "customer123";
    }

    private String getPemesananId() {
        // Implement this method to get the booking ID from your data source
        // For now, return a placeholder value
        return "booking123";
    }
}