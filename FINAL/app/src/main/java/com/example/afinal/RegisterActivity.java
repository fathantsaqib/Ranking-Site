package com.example.afinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        TextInputEditText regNimInput = findViewById(R.id.nim2);
        TextInputEditText regEmailInput = findViewById(R.id.email);
        TextInputEditText regPassInput = findViewById(R.id.password2);
        Button buttonReg = findViewById(R.id.btnRegister);
        LinearLayout linearLayout = findViewById(R.id.mainRegister);

        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);


        buttonReg.setOnClickListener(v ->{
            String nim = regNimInput.getText().toString();
            String password = regPassInput.getText().toString();
            String email = regEmailInput.getText().toString();

            if (nim.isEmpty()) {
                regNimInput.setError("Please fill this field");
            } else if (email.isEmpty()) {
                regEmailInput.setError("Please fill this field");
            } else if (password.isEmpty()) {
                regPassInput.setError("Please fill this field");
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("nim", nim);
                editor.putString("email", email);
                editor.putString("password", password);
                editor.apply();

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

                Toast.makeText(RegisterActivity.this, "Berhasil membuat akun.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}