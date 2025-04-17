package com.example.carwashcliente_android.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carwashcliente_android.Adapters.SeleccionarServicioAdapter;
import com.example.carwashcliente_android.Adapters.VehiculoCotizacionAdapter;
import com.example.carwashcliente_android.Config.ClientManager;
import com.example.carwashcliente_android.Models.Cotizacion;
import com.example.carwashcliente_android.Models.ServiciosModel;
import com.example.carwashcliente_android.R;
import com.example.carwashcliente_android.Retrofit.ApiService;
import com.example.carwashcliente_android.Retrofit.RetrofitClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SeleccionServiciosFragment extends Fragment {

    public SeleccionServiciosFragment() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    private Button btnContinuar;
    ClientManager clientManager;
    private final ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    Cotizacion cotizacion;
    private SeleccionarServicioAdapter adapter;
    private List<ServiciosModel> servicios = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seleccion_servicios, container, false);
        clientManager = new ClientManager(getContext());
        if (getArguments() != null) {
            cotizacion = (Cotizacion) getArguments().getSerializable("cotizacion");
            if (cotizacion != null) {

                Log.d("Cotizacion modalidad",cotizacion.getModalidad()+"");
            }
        }
        btnContinuar = view.findViewById(R.id.btnIrADetalleServicio);
        recyclerView = view.findViewById(R.id.recyclerVerServicio);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        llenarLista();
        btnContinuar.setOnClickListener(v -> {
            cotizacion.calcularTotal();
            Bundle bundle = new Bundle();
            bundle.putSerializable("cotizacion", cotizacion);

            Fragment fragment = new ResumenServiciosFragment();
            fragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedorFragments, fragment)
                    .addToBackStack(null)
                    .commit();

        });

        Log.d("ID: ", "Vehiculo id: "+cotizacion.getIdCarro()+"");
        return view;
    }

    private void llenarLista(){
        String token= "Bearer " + clientManager.getClientToken();
        Call<List<ServiciosModel>> call = apiService.listarServicios(token);
        call.enqueue(new Callback<List<ServiciosModel>>() {
            @Override
            public void onResponse(Call<List<ServiciosModel>> call, Response<List<ServiciosModel>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    servicios.clear();
                    servicios.addAll(response.body());
                    adapter = new SeleccionarServicioAdapter(servicios, servicio ->{
                        mostrarDialogoAgregarNota(servicio);
                    });
                    recyclerView.setAdapter(adapter);
                    Log.d("Retrofit", "Servicios mostrados");
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
            public void onFailure(Call<List<ServiciosModel>> call, Throwable t) {
                Log.e("Retrofit", "Fallo en la solicitud: " + t.getMessage());
            }
        });
    }

    private void mostrarDialogoAgregarNota(ServiciosModel servicio) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_agregar_nota, null);

        EditText etNotaServicio = dialogView.findViewById(R.id.etNotaServicio);
        Button btnAgregarNota = dialogView.findViewById(R.id.btnAgregarNota);
        Button btnCancelarNota = dialogView.findViewById(R.id.btnCancelarNota);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        btnAgregarNota.setOnClickListener(v -> {
            String nota = etNotaServicio.getText().toString().trim();

            Cotizacion.detalles detalles = new Cotizacion.detalles(
                    servicio.getID(),
                    nota,
                    servicio.getPrecio()
            );
            cotizacion.addDetalles(detalles);

            Toast.makeText(getContext(), "Servicio añadido con nota", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        btnCancelarNota.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}