package com.example.carwashcliente_android.Fragments;

import androidx.fragment.app.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashcliente_android.Activities.LoginActivity;
import com.example.carwashcliente_android.Activities.RegisterActivity;
import com.example.carwashcliente_android.Activities.RegistroVehiculo;
import com.example.carwashcliente_android.Adapters.UbicacionAdapter;
import com.example.carwashcliente_android.Adapters.VehiculoAdapter;
import com.example.carwashcliente_android.Config.ClientManager;
import com.example.carwashcliente_android.Models.UbicacionModel;
import com.example.carwashcliente_android.Models.VehiculoModel;
import com.example.carwashcliente_android.R;
import com.example.carwashcliente_android.Retrofit.ApiService;
import com.example.carwashcliente_android.Retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehiculoFragment extends Fragment {

    private RecyclerView recyclerView;
    private VehiculoAdapter adapter;
    private List<VehiculoModel> vehiculosList;
    private final ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private ClientManager clientManager;
    private FloatingActionButton fabAgregarVehiculo;

    public VehiculoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehiculo, container, false);
        fabAgregarVehiculo = view.findViewById(R.id.fab_agregar_vehiculo);

        fabAgregarVehiculo.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), RegistroVehiculo.class);
            intent.putExtra("accion", 1);
            startActivity(intent);
        });

        clientManager = new ClientManager(getContext());

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.recyclerVehiculos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializa la lista y el adapter vacío
        vehiculosList = new ArrayList<>();
        adapter = new VehiculoAdapter(vehiculosList, getContext());
        recyclerView.setAdapter(adapter);

        cargarVehiculos();

        return view;
    }

    public void cargarVehiculos(){
        vehiculosList = new ArrayList<>();
        String token= "Bearer " + clientManager.getClientToken();
        Call<List<VehiculoModel>> call = apiService.listaVehiculo(token);
        call.enqueue(new Callback<List<VehiculoModel>>() {
            @Override
            public void onResponse(Call<List<VehiculoModel>> call, Response<List<VehiculoModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    vehiculosList.clear();
                    vehiculosList.addAll(response.body());
                    VehiculoModel ubi = vehiculosList.get(0);

                    adapter = new VehiculoAdapter(vehiculosList, getContext());
                    recyclerView.setAdapter(adapter);
                    Log.d("Retrofit", "Vehiculos mostrados");
                } else {
                    try {
                        String errorBody = response.errorBody().string();

                        JSONObject jsonObject = new JSONObject(errorBody);
                        String mensaje = jsonObject.getString("mensaje");

                        Toast.makeText(getContext(), "⚠️ " + mensaje, Toast.LENGTH_LONG).show();
                        Log.e("Retrofit", "Mensaje recibido: " + mensaje);
                    } catch (Exception e) {
                        Log.e("Retrofit", "Error al parsear errorBody: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<VehiculoModel>> call, Throwable t) {
                Log.e("Retrofit", "Fallo en la solicitud: " + t.getMessage());
            }
        });
    }
}
