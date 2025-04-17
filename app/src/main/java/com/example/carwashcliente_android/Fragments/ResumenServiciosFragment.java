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
import android.widget.TextView;
import android.widget.Toast;

import com.example.carwashcliente_android.Adapters.ResumenServiciosAdapter;
import com.example.carwashcliente_android.Config.ClientManager;
import com.example.carwashcliente_android.Models.Cotizacion;
import com.example.carwashcliente_android.R;
import com.example.carwashcliente_android.Retrofit.ApiService;
import com.example.carwashcliente_android.Retrofit.RetrofitClient;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumenServiciosFragment extends Fragment {


    public ResumenServiciosFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerResumen;
    TextView total;
    Cotizacion cotizacion;
    ClientManager clientManager;
    private final ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resumen_servicios, container, false);
        clientManager = new ClientManager(getContext());
        if (getArguments() != null) {
            cotizacion = (Cotizacion) getArguments().getSerializable("cotizacion");
            if (cotizacion != null) {

                Log.d("Cotizacion modalidad",cotizacion.getModalidad()+"");
            }
        }
        total = view.findViewById(R.id.totalAproximado);
        total.setText("total Aproximado: "+cotizacion.getTotal());
        recyclerResumen = view.findViewById(R.id.recyclerResumen);
        recyclerResumen.setLayoutManager(new LinearLayoutManager(getContext()));

        if (cotizacion != null && cotizacion.getDetalles() != null) {
            ResumenServiciosAdapter adapter = new ResumenServiciosAdapter(cotizacion.getDetalles());
            recyclerResumen.setAdapter(adapter);
        }

        Button btnEnviar = view.findViewById(R.id.btnEnviar);
        Button btnCancelar = view.findViewById(R.id.btnCancelar);

        btnEnviar.setOnClickListener(v -> {
            enviarCotizacion();
        });

        btnCancelar.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        return view;
    }
    private void enviarCotizacion(){
        String token= "Bearer " + clientManager.getClientToken();
        Call<Void> call = apiService.crearCotizacion(token,cotizacion);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contenedorFragments, new HomeFragment())
                            .addToBackStack(null)
                            .commit();
                }else{
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
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Retrofit", "Fallo en la solicitud: " + t.getMessage());
            }
        });
    }

}