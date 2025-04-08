package com.example.carwashcliente_android.Fragments;

import androidx.fragment.app.Fragment;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.carwashcliente_android.Adapters.HistorialAdapter;
import com.example.carwashcliente_android.Models.Cotizacion;
import com.example.carwashcliente_android.R;

import java.util.ArrayList;
import java.util.List;

public class HistorialFragment extends Fragment {

    private RecyclerView recyclerView;
    private HistorialAdapter adapter;
    private List<Cotizacion> cotizacionesList;

    public HistorialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historial, container, false);

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.recyclerHistorial);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializar lista de cotizaciones (esto debería venir de tu backend)
        cotizacionesList = new ArrayList<>();
        // Agregar datos de ejemplo
        cotizacionesList.add(new Cotizacion("Lavado Premium", "$150.000", "15/06/2023", "Aceptada"));
        cotizacionesList.add(new Cotizacion("Lavado Básico", "$80.000", "10/06/2023", "Rechazada"));
        cotizacionesList.add(new Cotizacion("Lavado Completo", "$120.000", "05/06/2023", "Aceptada"));


        adapter = new HistorialAdapter(cotizacionesList, getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }
}