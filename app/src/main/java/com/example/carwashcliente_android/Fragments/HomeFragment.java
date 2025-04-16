package com.example.carwashcliente_android.Fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.carwashcliente_android.Adapters.CotizacionesPendientesAdapter;
import com.example.carwashcliente_android.Config.ClientManager;
import com.example.carwashcliente_android.Models.CotizacionesPendientesModel;
import com.example.carwashcliente_android.R;
import com.example.carwashcliente_android.Retrofit.ApiService;
import com.example.carwashcliente_android.Retrofit.RetrofitClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

        private RecyclerView recyclerView;
        private CotizacionesPendientesAdapter adapter;
        private List<CotizacionesPendientesModel> listaCotizaciones;
        private final ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        private ClientManager clientManager;

        public HomeFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.fragment_home, container, false);
                clientManager = new ClientManager(getContext());
                recyclerView = view.findViewById(R.id.recycler_cotizaciones);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                listaCotizaciones = new ArrayList<>();
                adapter = new CotizacionesPendientesAdapter(listaCotizaciones, cotizacion -> {
                        AceptaroRechazar("3", cotizacion);
                }, cotizacion2 -> {
                        AceptaroRechazar("6", cotizacion2);
                });
                recyclerView.setAdapter(adapter);

                ObtenerCotizacionesDetalles();

                return view;
        }

        private void ObtenerCotizacionesDetalles(){
                listaCotizaciones = new ArrayList<>();
                String token= "Bearer " + clientManager.getClientToken();
                Call<List<CotizacionesPendientesModel>> call = apiService.aceptarORechazarCotizacion(token);
                call.enqueue(new Callback<List<CotizacionesPendientesModel>>() {
                        @Override
                        public void onResponse(Call<List<CotizacionesPendientesModel>> call, Response<List<CotizacionesPendientesModel>> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                        listaCotizaciones.clear();
                                        listaCotizaciones.addAll(response.body());

                                        adapter = new CotizacionesPendientesAdapter(listaCotizaciones, cotizacion->{
                                                AceptaroRechazar("3", cotizacion);
                                        }, cotizacion2->{
                                                AceptaroRechazar("6", cotizacion2);
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
                        public void onFailure(Call<List<CotizacionesPendientesModel>> call, Throwable t) {
                                Log.e("Retrofit", "Fallo en la solicitud: " + t.getMessage());
                        }
                });
        }

        public void AceptaroRechazar(String estado, CotizacionesPendientesModel cotizacion){
                HashMap<String, String> body = new HashMap<>();
                body.put("estado", estado);
                int id = cotizacion.getIdCotizacion();
                listaCotizaciones = new ArrayList<>();
                String token= "Bearer " + clientManager.getClientToken();
                Call<CotizacionesPendientesModel> call = apiService.accionEnCotizacion(token,body,id);
                call.enqueue(new Callback<CotizacionesPendientesModel>() {
                        @Override
                        public void onResponse(Call<CotizacionesPendientesModel> call, Response<CotizacionesPendientesModel> response) {
                                if(response.isSuccessful() && response.body() != null){
                                        Log.e("Retrofit", "Datos obtenidos exitosamente");
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
                        public void onFailure(Call<CotizacionesPendientesModel> call, Throwable t) {
                                Log.e("Retrofit", "Fallo en la solicitud: " + t.getMessage());
                        }
                });
        }

}
