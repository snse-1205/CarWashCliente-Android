package com.example.carwashcliente_android.Activities;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import com.example.carwashcliente_android.R;

public class RegisterActivity  {

    private EditText etEmail, etPhone, etPassword, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.clone(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        Button btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String phone = etPhone.getText().toString();
            String password = etPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();

            if(validateRegistration(email, phone, password, confirmPassword)) {
                Intent intent = new Intent(this, VerificationActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("phone", phone);
                intent.putExtra("password", password);
                intent.putExtra("action", "register");
                startActivity(intent);
            }
        });
    }

    private void startActivity(Intent intent) {
    }

    private boolean validateRegistration(String email, String phone,
                                         String password, String confirmPassword) {
        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Email inválido");
            return false;
        }
        if(phone.isEmpty() || phone.length() < 9) {
            etPhone.setError("Teléfono inválido");
            return false;
        }
        if(password.isEmpty() || password.length() < 6) {
            etPassword.setError("Mínimo 6 caracteres");
            return false;
        }
        if(!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Las contraseñas no coinciden");
            return false;
        }
        return true;
    }
}
