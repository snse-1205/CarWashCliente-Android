package com.example.carwashcliente_android.Activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carwashcliente_android.Config.ClientManager;
import com.example.carwashcliente_android.Models.UsuarioModel;
import com.example.carwashcliente_android.Models.VehiculoModel;
import com.example.carwashcliente_android.R;
import com.example.carwashcliente_android.Retrofit.ApiService;
import com.example.carwashcliente_android.Retrofit.RetrofitClient;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroVehiculo extends AppCompatActivity {
    private final ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private ClientManager clientManager;
    private EditText etPlaca,etAnio,etColor;
    private Spinner spinnerMarcas, spinnerModelos;
    private Button btnGuardarVehiculo;
    private static int accion=0, id;
    private List<VehiculoModel.Modelo> listaModelos = new ArrayList<>();
    private String nombreMarcaRecibida = "", placa="",anio="", color="";
    private String nombreModeloRecibido = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_vehiculo);
        clientManager = new ClientManager(this);

        etPlaca=findViewById(R.id.etPlaca);
        etAnio=findViewById(R.id.etAnio);
        etColor=findViewById(R.id.etColor);
        spinnerMarcas=findViewById(R.id.spinnerMarca);
        spinnerModelos=findViewById(R.id.spinnerModelo);

        btnGuardarVehiculo=findViewById(R.id.btnGuardarVehiculo);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("accion") && intent.hasExtra("id")) {
            accion = intent.getIntExtra("accion",0);
            if(accion==2){
                Log.e("Pantalla Actualizar", "Estamos en Actualizar");
                id = intent.getIntExtra("id",-1);
                nombreMarcaRecibida = intent.getStringExtra("nombreMarca");
                nombreModeloRecibido = intent.getStringExtra("nombreModelo");
                placa = intent.getStringExtra("placa");
                anio = intent.getStringExtra("anio");
                color = intent.getStringExtra("color");
                llenarCamposParaModificar(nombreMarcaRecibida,nombreModeloRecibido,placa,anio, color);
                btnGuardarVehiculo.setText("Actualizar");
            }

        } else {
            Log.e("Pantalla Actualizar", "No se recibio el entero");
        }

        llamarMarcas();

        btnGuardarVehiculo.setOnClickListener(v -> {
            String placa = etPlaca.getText().toString();
            String color = etColor.getText().toString();
            String anio = etAnio.getText().toString();
            if (validateRegistration(placa, color, anio)) {
                if (accion == 2) {
                    actualizarVehiculo();
                } else {
                    crearVehiculo();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void llenarCamposParaModificar(String marca, String modelo, String placa, String anio, String color){
        etPlaca.setText(placa);  // ← Corregido
        etColor.setText(color);
        etAnio.setText(anio);
    }


    private void actualizarVehiculo(){
        String token = "Bearer " + clientManager.getClientToken();

        int idContacto = id;
        Log.e("Retrofit", "Id: "+idContacto);
        if(idContacto==-1){
            Log.e("Intent Update", "No se obtuvo el valor");
            return;
        }

        int modeloSeleccionado = spinnerModelos.getSelectedItemPosition();
        String idModelo = "";

        if (modeloSeleccionado >= 0 && modeloSeleccionado < listaModelos.size()) {
            idModelo = String.valueOf(listaModelos.get(modeloSeleccionado).getId()); // 👈 Aquí tienes el ID real
        }

        HashMap<String, String> body = new HashMap<>();
        body.put("id",idContacto+"");
        body.put("placa", etPlaca.getText().toString());
        body.put("color", etColor.getText().toString());
        body.put("modelo", idModelo);
        body.put("year", etAnio.getText().toString());

        Call<VehiculoModel> call = apiService.actualizarCarro(token, body);
        call.enqueue(new Callback<VehiculoModel>() {
            @Override
            public void onResponse(Call<VehiculoModel> call, Response<VehiculoModel> response) {
                if (response.isSuccessful()) {
                    Log.d("Retrofit", "Vehiculo agregado correctamente");
                } else {
                    String errorBody = response.errorBody().toString();
                    Log.e("Retrofit", "Respuesta de error NO JSON: " + errorBody);
                    Toast.makeText(getApplicationContext(), "⚠️ Error inesperado del servidor", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<VehiculoModel> call, Throwable t) {
                Log.e("Retrofit", "Falla en la solicitud: " + t.getMessage());
            }
        });
    }

    private void llamarMarcas(){
        Call<List<VehiculoModel.Marca>> call = apiService.listarMarcaModelo();
        call.enqueue(new Callback<List<VehiculoModel.Marca>>() {
            @Override
            public void onResponse(Call<List<VehiculoModel.Marca>> call, Response<List<VehiculoModel.Marca>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<VehiculoModel.Marca> listaMarcas = response.body();
                    cargarSpinnerMarcas(listaMarcas);
                    Log.d("Retrofit", "Carga exitosa de marcas");
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
            public void onFailure(Call<List<VehiculoModel.Marca>> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage());
            }
        });
    }

    private void crearVehiculo() {
        String token = "Bearer " + clientManager.getClientToken();

        int modeloSeleccionado = spinnerModelos.getSelectedItemPosition();
        String idModelo = "";

        if (modeloSeleccionado >= 0 && modeloSeleccionado < listaModelos.size()) {
            idModelo = String.valueOf(listaModelos.get(modeloSeleccionado).getId()); // 👈 Aquí tienes el ID real
        }

        HashMap<String, String> body = new HashMap<>();
        body.put("placa", etPlaca.getText().toString());
        body.put("color", etColor.getText().toString());
        body.put("modelo", idModelo); // ✅ ahora se envía el ID correcto
        body.put("year", etAnio.getText().toString());

        Call<Void> call = apiService.agregarVehiculo(token, body);
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

    private void cargarSpinnerMarcas(List<VehiculoModel.Marca> marcas) {
        List<String> nombresMarcas = new ArrayList<>();
        int indiceMarcaSeleccionada = -1;

        for (int i = 0; i < marcas.size(); i++) {
            VehiculoModel.Marca marca = marcas.get(i);
            nombresMarcas.add(marca.getNombre());

            // Buscar la marca que coincide con el nombre recibido
            if (marca.getNombre().equalsIgnoreCase(nombreMarcaRecibida)) {
                indiceMarcaSeleccionada = i;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresMarcas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMarcas.setAdapter(adapter);

        if (indiceMarcaSeleccionada != -1) {
            spinnerMarcas.setSelection(indiceMarcaSeleccionada);
            cargarSpinnerModelos(marcas.get(indiceMarcaSeleccionada).getModelos());
        }

        spinnerMarcas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<VehiculoModel.Modelo> modelos = marcas.get(position).getModelos();
                cargarSpinnerModelos(modelos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    private void cargarSpinnerModelos(List<VehiculoModel.Modelo> modelos) {
        listaModelos = modelos;

        List<String> nombresModelos = new ArrayList<>();
        int indiceModeloSeleccionado = -1;

        for (int i = 0; i < modelos.size(); i++) {
            VehiculoModel.Modelo modelo = modelos.get(i);
            nombresModelos.add(modelo.getNombre());

            // Buscar el modelo que coincide con el nombre recibido
            if (modelo.getNombre().equalsIgnoreCase(nombreModeloRecibido)) {
                indiceModeloSeleccionado = i;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresModelos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerModelos.setAdapter(adapter);

        if (indiceModeloSeleccionado != -1) {
            spinnerModelos.setSelection(indiceModeloSeleccionado);
        }
    }


}