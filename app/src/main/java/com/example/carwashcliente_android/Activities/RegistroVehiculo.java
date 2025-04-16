package com.example.carwashcliente_android.Activities;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carwashcliente_android.Config.ClientManager;
import com.example.carwashcliente_android.R;
import com.example.carwashcliente_android.Retrofit.ApiService;
import com.example.carwashcliente_android.Retrofit.RetrofitClient;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroVehiculo extends AppCompatActivity {
    private final ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private ClientManager clientManager;
    private EditText etPlaca,etAnio,etColor;
    private Spinner spinnerMarca, spinnerModelo;
    private Button btnGuardarVehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_vehiculo);
        clientManager = new ClientManager(this);

        etPlaca=findViewById(R.id.etPlaca);
        etAnio=findViewById(R.id.etAnio);
        etColor=findViewById(R.id.etColor);
        spinnerMarca=findViewById(R.id.spinnerMarca);
        spinnerModelo=findViewById(R.id.spinnerModelo);

        btnGuardarVehiculo=findViewById(R.id.btnGuardarVehiculo);

        btnGuardarVehiculo.setOnClickListener(v -> {
            String placa = etPlaca.getText().toString();
            String color = etColor.getText().toString();
            String anio = etAnio.getText().toString();
            if (validateRegistration(placa, color, anio)) {
                crearVehiculo();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void crearVehiculo(){
        String token= "Bearer " + clientManager.getClientToken();
        HashMap<String, String> body = new HashMap<>();
        body.put("placa", etPlaca.getText().toString());
        body.put("color", etColor.getText().toString());
        body.put("modelo", "1"); //TODO:colocar el spinner del modelo con su ID
        body.put("year", etAnio.getText().toString());
        Call<Void> call = apiService.agregarVehiculo(token,body);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("Retrofit", "Vehiculo agregado correctamente");
                } else {
                    try {
                        String errorBody = response.errorBody().string();

                        JSONObject jsonObject = new JSONObject(errorBody);
                        String mensaje = jsonObject.getString("mensaje");

                        Log.e("Retrofit", "Mensaje recibido: " + mensaje);
                    } catch (Exception e) {
                        Log.e("Retrofit", "Error al parsear errorBody: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Retrofit", "Fallo en la solicitud: " + t.getMessage());
            }
        });
    }
    private boolean validateRegistration(String placa, String color, String anio) {
        if(placa.isEmpty()){
            etPlaca.setError("Placa inválida");
            return false;
        }
        if(color.isEmpty()) {
            etColor.setError("Color inválido");
            return false;
        }
        if(anio.isEmpty()) {
            etAnio.setError("Año inválido");
            return false;
        }
        return true;
    }

}