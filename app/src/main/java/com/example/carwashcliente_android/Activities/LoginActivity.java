package com.example.carwashcliente_android.Activities; // Asegúrate que coincida con tu estructura

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carwashcliente_android.Config.ClientManager;
import com.example.carwashcliente_android.Config.SocketManager;
import com.example.carwashcliente_android.R;
import com.example.carwashcliente_android.Retrofit.ApiService;
import com.example.carwashcliente_android.Models.UsuarioModel;
import com.example.carwashcliente_android.Retrofit.RetrofitClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    // Declaración correcta de vistas
    EditText txtCorreo, txtPassword;
    ClientManager clientManager;
    private final ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

    private Button btnLogin, btnRegister, btnForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Llamada obligatoria al padre
        setContentView(R.layout.activity_login); // Asegúrate que el layout existe
        clientManager = new ClientManager(this);
        // Inicialización correcta de vistas
        txtCorreo = findViewById(R.id.etEmail);
        txtPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);

        // Configuración de listeners
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(v -> Login());

        verificarLogin();
        obtenerToken();
    }

    private void obtenerToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    System.out.println("Fetching FCM registration token failed");
                    return;                }
                // Get new FCM registration token
                String token = task.getResult();
                Log.d("TokenNoti",token);}});
    }

    private void verificarLogin() {
        String token= "Bearer " + clientManager.getClientToken();
        Call<Void> call = apiService.verifiarSesion(token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.e("Retrofit", "HOLIS :D : " + response.message());
                    if (SocketManager.getSocket() == null || !SocketManager.getSocket().connected()) {
                        SocketManager.initSocket();
                        SocketManager.connect();
                    }

                    Intent intent =new Intent(getApplicationContext(), PantallaPrincipal.class);
                    startActivity(intent);
                    finish();
                }else{
                    Log.e("Retrofit", "Fallo en la solicitud: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Retrofit", "Fallo en la solicitud: " + t.getMessage());
            }
        });
    }

    public void Login(){
        if(txtCorreo.getText().toString().trim().isEmpty() || txtPassword.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(),"Todos los campos son obligatorios",Toast.LENGTH_SHORT).show();

        }else{
            String correo = txtCorreo.getText().toString();
            String clave = txtPassword.getText().toString();
            HashMap<String,String> body = new HashMap<>();
            body.put("correo",correo);
            body.put("contrasena",clave);
            Call<UsuarioModel> call = apiService.login(body);
            call.enqueue(new Callback<UsuarioModel>() {
                @Override
                public void onResponse(Call<UsuarioModel> call, Response<UsuarioModel> response) {
                    if(response.isSuccessful()){
                        UsuarioModel usuarioModel =response.body();
                        Log.d("UsuarioModel", usuarioModel.toString());

                        clientManager.saveClientData(
                                usuarioModel.getId(),
                                usuarioModel.getUsername(),
                                usuarioModel.getToken());

                        SocketManager.initSocket();
                        SocketManager.connect();
                        SocketManager.notifyUserConnected(usuarioModel.getUsername());

                        Log.d("Retrofit", "Inicio de secion exitoso");
                        Intent intent =new Intent(getApplicationContext(), PantallaPrincipal.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Log.e("Retrofit", "Fallo en la solicitud: " + response.message());
                    }
                }
                @Override
                public void onFailure(Call<UsuarioModel> call, Throwable t) {
                    Log.e("Retrofit", "Fallo en la solicitud: " + t.getMessage());
                }
            });
        }

    }
}