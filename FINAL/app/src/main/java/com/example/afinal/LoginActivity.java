package com.example.afinal;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        TextInputEditText nimInput = findViewById(R.id.nim);
        TextInputEditText emailInput = findViewById(R.id.email2);
        TextInputEditText passInput = findViewById(R.id.password);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvReg = findViewById(R.id.register);
        LinearLayout linearLayout = findViewById(R.id.mainLogin);


        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        String NIM = sharedPreferences.getString("nim", "");
        String EMAIL = sharedPreferences.getString("email", "");
        String PASSWORD = sharedPreferences.getString("password", "");

        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        btnLogin.setOnClickListener(v -> {
            String nim = nimInput.getText().toString();
            String email = emailInput.getText().toString();
            String password = passInput.getText().toString();

            if (nim.isEmpty()) {
                nimInput.setError("Please fill this field");
            } else if (email.isEmpty()) {
                emailInput.setError("Please fill this field");
            } else if (password.isEmpty()) {
                passInput.setError("Please fill this field");
            } else if (nim.equals(NIM) && password.equals(PASSWORD) && email.equals(EMAIL)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.apply();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "NIM atau Password salah.", Toast.LENGTH_SHORT).show();
            }

        });

        tvReg.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

    }
}