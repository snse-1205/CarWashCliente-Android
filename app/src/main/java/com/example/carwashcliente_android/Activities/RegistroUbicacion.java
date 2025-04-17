package com.example.carwashcliente_android.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carwashcliente_android.Config.ClientManager;
import com.example.carwashcliente_android.R;
import com.example.carwashcliente_android.Retrofit.ApiService;
import com.example.carwashcliente_android.Retrofit.RetrofitClient;
import com.example.carwashcliente_android.Utils.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroUbicacion extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    private MapView mapView;
    private GoogleMap googleMap;
    private Util utils;
    private double latitudSeleccionada = 0.0;
    private double longitudSeleccionada = 0.0;
    private final ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private ClientManager clientManager;
    private EditText nombre,descripcion;
    private Button btnContinuar, btnCancelar, btnUbicacionActual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_ubicacion);
        clientManager = new ClientManager(this);

        mapView = findViewById(R.id.mapView);
        btnContinuar = findViewById(R.id.btnGuardarUbicacion);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnUbicacionActual = findViewById(R.id.btnUbicacionActual);
        nombre = findViewById(R.id.editNombreUbicacion);
        descripcion = findViewById(R.id.editDescripcionUbicacion);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(map -> {
            googleMap = map;
            habilitarUbicacion();

            LatLng destino = new LatLng(15.551188, -88.011831);
            googleMap.addMarker(new MarkerOptions().position(destino).title("Destino"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destino, 15));

            googleMap.setOnMapClickListener(latLng -> {
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Ubicación seleccionada"));

                latitudSeleccionada = latLng.latitude;
                longitudSeleccionada = latLng.longitude;
            });
        });

        btnUbicacionActual.setOnClickListener(v -> {
            utils = new Util();
            latitudSeleccionada = utils.getLatitud(RegistroUbicacion.this, RegistroUbicacion.this);
            longitudSeleccionada = utils.getLongitud(RegistroUbicacion.this, RegistroUbicacion.this);

            // Puedes también centrar el mapa aquí si quieres
            if (googleMap != null) {
                LatLng actual = new LatLng(latitudSeleccionada, longitudSeleccionada);
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(actual).title("Ubicación actual"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actual, 15));
            }
        });

        btnContinuar.setOnClickListener(v -> {
            String nombreUbicacion = nombre.getText().toString().trim();

            if (nombreUbicacion.isEmpty()) {
                nombre.setError("Nombre inválido");
                nombre.requestFocus();
            } else if (latitudSeleccionada == 0.0 || longitudSeleccionada == 0.0) {
                Toast.makeText(this, "Por favor selecciona una ubicación en el mapa o usa tu ubicación actual", Toast.LENGTH_SHORT).show();
            } else {
                agregarUbicacion();
            }
        });


        btnCancelar.setOnClickListener(v ->
                this.getOnBackPressedDispatcher().onBackPressed()
        );

        if (!tienePermisosUbicacion()) {
            solicitarPermisosUbicacion();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutAgregarUbicacion), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void agregarUbicacion(){
        String token= "Bearer " + clientManager.getClientToken();
        String nombres = nombre.getText().toString();
        String descri = "";

        if(!descripcion.getText().toString().isEmpty()){
            descri = descripcion.getText().toString();
        }

        String lati = latitudSeleccionada+"";
        String longi = longitudSeleccionada+"";

        HashMap<String, String> body = new HashMap<>();
        body.put("nombre", nombres);
        body.put("referencia", descri);
        body.put("lat", lati);
        body.put("lon", longi);

        Call<Void> call = apiService.registroUbicacion(token, body);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("Retrofit", "Ubicacion agregada correctamente");
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

    private boolean tienePermisosUbicacion() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void solicitarPermisosUbicacion() {
        requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, LOCATION_PERMISSION_REQUEST_CODE);
    }

    private void habilitarUbicacion() {
        if (tienePermisosUbicacion() && googleMap != null) {
            try {
                googleMap.setMyLocationEnabled(true);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (tienePermisosUbicacion()) {
                habilitarUbicacion();
            }
        }
    }

    // Ciclo de vida del MapView
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
