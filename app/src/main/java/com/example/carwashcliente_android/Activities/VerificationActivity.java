package com.example.carwashcliente_android.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carwashcliente_android.Models.UsuarioModel;
import com.example.carwashcliente_android.R;

import com.example.carwashcliente_android.MainActivity;
import com.example.carwashcliente_android.Retrofit.ApiService;
import com.example.carwashcliente_android.Retrofit.RetrofitClient;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationActivity extends Activity {

    private TextView tvMessage;
    private String archivoPath;
    private EditText etCode;
    private String verificationId;
    private String nombre,email, phone, password, action;
    private UsuarioModel usuario;
    private final ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        usuario = new UsuarioModel();

        tvMessage = findViewById(R.id.tvMessage);
        etCode = findViewById(R.id.etCode);
        Button btnVerify = findViewById(R.id.btnVerify);
        Button btnResend = findViewById(R.id.btnResend);

        // Obtener datos del Intent
        nombre = getIntent().getStringExtra("nombre");
        email = getIntent().getStringExtra("email");
        phone = getIntent().getStringExtra("phone");
        password = getIntent().getStringExtra("password");
        archivoPath = getIntent().getStringExtra("archivo");
        action = getIntent().getStringExtra("action");

        tvMessage.setText("Ingresa el código enviado al\n " + email);
        verificarCorreo();

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigoIngresado = etCode.getText().toString();

                if (!codigoIngresado.isEmpty()) {
                    if (codigoIngresado.equals(String.valueOf(usuario.getCodigo()))) {
                        Toast.makeText(getApplicationContext(), "✅ Código correcto", Toast.LENGTH_LONG).show();
                        registroCliente();
                    } else {
                        Toast.makeText(getApplicationContext(), "❌ Código incorrecto", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "⚠️ Ingresa un codigo", Toast.LENGTH_LONG).show();
                }
            }
        });


        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void verificarCorreo() {
        HashMap<String, String> body = new HashMap<>();
        body.put("correo", email);

        Call<UsuarioModel> call = apiService.verificar(body);
        call.enqueue(new Callback<UsuarioModel>() {
            @Override
            public void onResponse(Call<UsuarioModel> call, Response<UsuarioModel> response) {
                if (response.isSuccessful()) {
                    usuario = response.body();
                    if (usuario != null) {
                        int codigo = usuario.getCodigo();
                        Log.d("Respuesta", "Código recibido: " + codigo);
                    }
                } else {
                    // Aquí mostramos más detalles del error
                    Log.e("Retrofit", "Error: " + response.message() + ", Código: " + response.code());

                    try {
                        String errorResponse = response.errorBody().string(); // Obtener el cuerpo del error
                        Log.e("Retrofit", "Detalles del error: " + errorResponse);
                    } catch (Exception e) {
                        Log.e("Retrofit", "Error al obtener el cuerpo del error: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<UsuarioModel> call, Throwable t) {
                Log.e("Retrofit", "Fallo en la solicitud: " + t.getMessage());
            }
        });

    }


    private void registroCliente(){
        File videoFile;
        HashMap<String,RequestBody> body = new HashMap<>();
        body.put("nombre", RequestBody.create(MediaType.parse("text/plain"), nombre));
        body.put("correo", RequestBody.create(MediaType.parse("text/plain"), email));
        body.put("telefono", RequestBody.create(MediaType.parse("text/plain"), phone));
        body.put("contrasena", RequestBody.create(MediaType.parse("text/plain"), password));
        if(archivoPath == null){
            videoFile = null;

            Call<Void> call = apiService.registroCliente(body);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "🎉 Registro exitoso", Toast.LENGTH_SHORT).show();
                        Log.d("Retrofit", "Funciono: " + response.body());
                    }else{
                        Log.e("Retrofit", "Error: " + response.message() + ", Código: " + response.code());

                        try {
                            String errorResponse = response.errorBody().string(); // Obtener el cuerpo del error
                            Log.e("Retrofit", "Detalles del error: " + errorResponse);
                        } catch (Exception e) {
                            Log.e("Retrofit", "Error al obtener el cuerpo del error: " + e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("Retrofit", "Fallo en la solicitud: " + t.getMessage());
                }
            });
        }else{
            videoFile = new File(archivoPath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("video/*"), videoFile);
            MultipartBody.Part videoPart = MultipartBody.Part.createFormData("video", videoFile.getName(), requestFile);

            Call<Void> call = apiService.registroCliente(body, videoPart);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "🎉 Registro exitoso", Toast.LENGTH_SHORT).show();
                        Log.d("Retrofit", "Funciono: " + response.body());
                    }else{
                        Log.e("Retrofit", "Error: " + response.message() + ", Código: " + response.code());

                        try {
                            String errorResponse = response.errorBody().string(); // Obtener el cuerpo del error
                            Log.e("Retrofit", "Detalles del error: " + errorResponse);
                        } catch (Exception e) {
                            Log.e("Retrofit", "Error al obtener el cuerpo del error: " + e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("Retrofit", "Fallo en la solicitud: " + t.getMessage());
                }
            });
        }


    }
}
