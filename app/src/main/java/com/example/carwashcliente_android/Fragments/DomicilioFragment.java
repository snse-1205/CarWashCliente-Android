package com.example.carwashcliente_android.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.carwashcliente_android.Activities.RegistroUbicacion;
import com.example.carwashcliente_android.Adapters.UbicacionAdapter;
import com.example.carwashcliente_android.Adapters.VehiculoAdapter;
import com.example.carwashcliente_android.Config.ClientManager;
import com.example.carwashcliente_android.Models.Cotizacion;
import com.example.carwashcliente_android.Models.UbicacionModel;
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


public class DomicilioFragment extends Fragment {


    private RecyclerView recyclerView;
    private UbicacionAdapter adapter;
    private final ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private ClientManager clientManager;
    private List<UbicacionModel> ubicacionList;

    Cotizacion cotizacion;

    public DomicilioFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_domicilio, container, false);
        clientManager = new ClientManager(getContext());

        if (getArguments() != null) {
            cotizacion = (Cotizacion) getArguments().getSerializable("cotizacion");
            if (cotizacion != null) {

                Log.d("Cotizacion modalidad",cotizacion.getModalidad()+"");
            }
        }

        Button btnAgregar = view.findViewById(R.id.btnAgregarDomicilio);
        Button btnCancelar = view.findViewById(R.id.btnCancelarDomicilio);
        recyclerView = view.findViewById(R.id.recyclerUbicaciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cargarUbicacion();

        btnAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), RegistroUbicacion.class);
            startActivity(intent);
        });

        btnCancelar.setOnClickListener(v ->
                requireActivity().getOnBackPressedDispatcher().onBackPressed()
        );

        return view;
    }

    public void cargarUbicacion(){
        ubicacionList = new ArrayList<>();
        String token= "Bearer " + clientManager.getClientToken();
        Call<List<UbicacionModel>> call = apiService.listaUbicaciones(token);
        call.enqueue(new Callback<List<UbicacionModel>>() {
            @Override
            public void onResponse(Call<List<UbicacionModel>> call, Response<List<UbicacionModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ubicacionList.clear();
                    ubicacionList.addAll(response.body());
                    UbicacionModel ubi = ubicacionList.get(0);
                    Log.d("Ubicaicones",ubi.toString());
                    Log.d("cantidad",ubicacionList.size()+"");
                    adapter = new UbicacionAdapter(ubicacionList, getContext(), ubicaciones -> {
                        cotizacion.setUbicacion(ubicaciones.getId());

                        Fragment next = new SeleccionVehiculoFragment();
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
                    Log.d("Retrofit", "Ubicaciones mostradas");
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
            public void onFailure(Call<List<UbicacionModel>> call, Throwable t) {
                Log.e("Retrofit", "Fallo en la solicitud: " + t.getMessage());
            }
        });
    }

}