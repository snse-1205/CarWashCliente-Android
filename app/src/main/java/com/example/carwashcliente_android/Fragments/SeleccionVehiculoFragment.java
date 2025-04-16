package com.example.carwashcliente_android.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.carwashcliente_android.R;


public class SeleccionVehiculoFragment extends Fragment {


    public SeleccionVehiculoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seleccion_vehiculo, container, false);

        ListView lista = view.findViewById(R.id.listaVehiculos);
        Button agregar = view.findViewById(R.id.btnAgregarVehiculo);

        agregar.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedorFragments, new SeleccionServiciosFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }


}