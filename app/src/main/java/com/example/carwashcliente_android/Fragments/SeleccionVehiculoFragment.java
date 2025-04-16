package com.example.carwashcliente_android.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.carwashcliente_android.Models.Cotizacion;
import com.example.carwashcliente_android.Adapters.VehiculoAdapter;
import com.example.carwashcliente_android.Adapters.VehiculoCotizacionAdapter;
import com.example.carwashcliente_android.Config.ClientManager;
import com.example.carwashcliente_android.Models.VehiculoModel;
import com.example.carwashcliente_android.R;
import com.example.carwashcliente_android.Retrofit.ApiService;
import com.example.carwashcliente_android.Retrofit.RetrofitClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SeleccionVehiculoFragment extends Fragment {

    private RecyclerView recyclerView;
    private VehiculoCotizacionAdapter adapter;
    private List<VehiculoModel> vehiculosList;
    private final ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private ClientManager clientManager;

    public SeleccionVehiculoFragment() {
        // Required empty public constructor
    }

    Cotizacion cotizacion;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seleccion_vehiculo, container, false);

        if (getArguments() != null) {
            cotizacion = (Cotizacion) getArguments().getSerializable("cotizacion");
            if (cotizacion != null) {

                Log.d("Cotizacion modalidad",cotizacion.getModalidad()+"");
            }
        }

        Button agregar = view.findViewById(R.id.btnAgregarVehiculo);

        clientManager = new ClientManager(getContext());

        recyclerView = view.findViewById(R.id.recyclerVehiculosCotizacion);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cargarVehiculos();

        agregar.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedorFragments, new SeleccionServiciosFragment())
                    .addToBackStack(null)
                    .commit();
        });

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

                    adapter = new VehiculoCotizacionAdapter(vehiculosList, vehiculo -> {
                        cotizacion.setIdCarro(vehiculo.getId());

                        Fragment next = new SeleccionServiciosFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("cotizacion", cotizacion);
                        next.setArguments(bundle);

                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.contenedorFragments, next)
                                .addToBackStack(null)
                                .commit();
                    });

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