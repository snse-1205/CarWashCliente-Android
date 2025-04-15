package com.example.carwashcliente_android.Activities;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.carwashcliente_android.Config.SocketManager;
import com.example.carwashcliente_android.Models.UsuarioModel;
import com.example.carwashcliente_android.R;
import com.example.carwashcliente_android.Retrofit.ApiService;
import com.example.carwashcliente_android.Retrofit.RetrofitClient;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity  extends AppCompatActivity {

    private EditText etNombre,etEmail, etPhone, etPassword, etConfirmPassword;
    private Uri videoUri;
    private String archivoPath=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //super.clone(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        Button btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String email = etEmail.getText().toString();
            String phone = etPhone.getText().toString();
            String password = etPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();
            if(videoUri == null){
                archivoPath = null;
            }else{
            archivoPath = getRealPathFromURI(videoUri);}

            if(validateRegistration(nombre,email, phone, password, confirmPassword)) {
                Intent intent = new Intent(this, VerificationActivity.class);
                intent.putExtra("nombre", nombre);
                intent.putExtra("email", email);
                intent.putExtra("phone", phone);
                intent.putExtra("password", password);
                intent.putExtra("action", "register");
                intent.putExtra("archivo",archivoPath);
                startActivity(intent);
            }
        });
    }

    private String getRealPathFromURI(Uri contentUri) {
        String result = null;
        String[] proj = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
            cursor.close();
        }
        return result;
    }

    private boolean validateRegistration(String nombre, String email, String phone,
                                         String password, String confirmPassword) {
        if(nombre.isEmpty()){
            etNombre.setError("Nombre inválido");
            return false;
        }
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
