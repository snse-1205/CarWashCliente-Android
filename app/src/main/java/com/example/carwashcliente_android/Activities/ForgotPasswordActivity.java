package com.example.carwashcliente_android.Activities;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carwashcliente_android.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmail = findViewById(R.id.etEmail);
        Button btnSendCode = findViewById(R.id.btnSendCode);

        btnSendCode.setOnClickListener(v -> {
            String email = etEmail.getText().toString();

            if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Email inválido");
                return;
            }
        });
    }
}
