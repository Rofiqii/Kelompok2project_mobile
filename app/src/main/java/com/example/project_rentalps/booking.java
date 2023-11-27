package com.example.project_rentalps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

public class booking extends AppCompatActivity {
    private EditText etJamM, etJamA;
    private ImageButton btnJamM, btnJamA;
    private int jam, menit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        etJamM = findViewById(R.id.etJamM);
        btnJamM = findViewById(R.id.btnJamM);
        etJamA = findViewById(R.id.etJamA);
        btnJamA = findViewById(R.id.btnJamA);
        btnJamM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                jam = calendar.get(Calendar.HOUR_OF_DAY);
                menit = calendar.get(Calendar.MINUTE);

                TimePickerDialog dialog;
                dialog = new TimePickerDialog(booking.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        jam = hourOfDay;
                        menit = minute;

                        if(jam <= 12) {
                            etJamM.setText(String.format(Locale.getDefault(), "%d:%d am", jam, menit));
                        }else{
                            etJamM.setText(String.format(Locale.getDefault(), "%d:%d am", jam, menit));
                        }
                    }
                }, jam, menit,true);
                dialog.show();
                dialog = new TimePickerDialog(booking.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        jam = hourOfDay;
                        menit = minute;

                        if(jam <= 12) {
                            etJamA.setText(String.format(Locale.getDefault(), "%d:%d am", jam, menit));
                        }else{
                            etJamA.setText(String.format(Locale.getDefault(), "%d:%d am", jam, menit));
                        }
                    }
                }, jam, menit,true);
                dialog.show();
            }
        });
    }
}