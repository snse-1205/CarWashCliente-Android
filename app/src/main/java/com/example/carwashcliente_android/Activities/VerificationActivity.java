package com.example.carwashcliente_android.Activities;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.carwashcliente_android.R;

import androidx.annotation.NonNull;

import com.example.carwashcliente_android.MainActivity;

import java.util.concurrent.TimeUnit;

public class VerificationActivity extends Activity {

    private TextView tvMessage;
    private EditText etCode;
    private String verificationId;
    private String email, phone, password, action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        tvMessage = findViewById(R.id.tvMessage);
        etCode = findViewById(R.id.etCode);
        Button btnVerify = findViewById(R.id.btnVerify);
        Button btnResend = findViewById(R.id.btnResend);

        // Obtener datos del Intent
        email = getIntent().getStringExtra("email");
        phone = getIntent().getStringExtra("phone");
        password = getIntent().getStringExtra("password");
        action = getIntent().getStringExtra("action");

        tvMessage.setText("Ingresa el código enviado al\n+51 " + phone);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
