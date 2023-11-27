package com.example.project_rentalps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Login extends AppCompatActivity {
    private EditText  etUsername, etPassword, etNama, etNoHP;

    private Button btnRegister, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
                String username_cus = etUsername.getText().toString();
                String password_cus = etPassword.getText().toString();

                if(!(username_cus.isEmpty() || password_cus.isEmpty())){

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, Db_Contract.urlLogin + "username_cus=" + username_cus + "&password=" + password_cus, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("Selamat Datang")){
                                Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(), booking.class));

                            }else{
                                Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

                        }
                    });

                    requestQueue.add(stringRequest);


                }else{
                    Toast.makeText(getApplicationContext(),"Ada yang kosong", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}