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

public class Register extends AppCompatActivity {

    private EditText etUsername, etPassword, etNama, etNoHP;

    private Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etNama = findViewById(R.id.etNama);
        etNoHP = findViewById(R.id.etNoHP);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username_cus = etUsername.getText().toString();
                String password_cus = etPassword.getText().toString();
                String fullname_cus = etNama.getText().toString();
                String no_hp_cus = etNoHP.getText().toString();

                if (!(username_cus.isEmpty() || password_cus.isEmpty() || fullname_cus.isEmpty() || no_hp_cus.isEmpty())){

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Db_Contract.urlRegister, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                            startActivity(new Intent());

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    {
                        @Override
                        protected HashMap<String, String> getParams() throws AuthFailureError{
                            HashMap<String, String> params = new HashMap<>();

                            params.put("username_cus", username_cus);
                            params.put("password_cus", password_cus);
                            params.put("fullname_cus", fullname_cus);
                            params.put("no_hp_cus", no_hp_cus);

                            return params;
                        }
                    };

                }else{
                    Toast.makeText(getApplicationContext(),"Ada yang kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}