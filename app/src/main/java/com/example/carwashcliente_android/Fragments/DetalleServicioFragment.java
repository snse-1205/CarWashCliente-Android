package com.example.carwashcliente_android.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.carwashcliente_android.R;

public class DetalleServicioFragment extends Fragment {


    public DetalleServicioFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_servicio, container, false);

        Button btnAnadir = view.findViewById(R.id.btnAnadirNota);
        Button btnCancelar = view.findViewById(R.id.btnCancelar);

        btnAnadir.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedorFragments, new ResumenServiciosFragment())
                    .addToBackStack(null)
                    .commit();
        });


        btnCancelar.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }

}