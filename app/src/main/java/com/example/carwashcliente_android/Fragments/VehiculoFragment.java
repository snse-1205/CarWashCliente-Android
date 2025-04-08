package com.example.carwashcliente_android.Fragments;

import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashcliente_android.Adapters.VehiculoAdapter;
import com.example.carwashcliente_android.Models.Vehiculo;
import com.example.carwashcliente_android.R;

import java.util.ArrayList;
import java.util.List;

public class VehiculoFragment extends Fragment {

    private RecyclerView recyclerView;
    private VehiculoAdapter adapter;
    private List<Vehiculo> vehiculosList;

    public VehiculoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehiculo, container, false);

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.recyclerVehiculos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializar lista de vehículos (esto debería venir de tu backend)
        vehiculosList = new ArrayList<>();
        // Agregar datos de ejemplo
        vehiculosList.add(new Vehiculo("Toyota", "Corolla", "ABC123", "2020"));
        vehiculosList.add(new Vehiculo("Honda", "Civic", "XYZ789", "2019"));

        // Configurar adapter
        adapter = new VehiculoAdapter(vehiculosList, getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }
}
