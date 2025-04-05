package com.example.carwashcliente_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ResumenServiciosFragment extends Fragment {


    public ResumenServiciosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resumen_servicios, container, false);

        Button btnEnviar = view.findViewById(R.id.btnEnviar);
        Button btnCancelar = view.findViewById(R.id.btnCancelar);

        btnEnviar.setOnClickListener(v -> {
            // Aquí puedes lanzar un Toast, enviar por API, etc.
        });

        btnCancelar.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        return view;
    }

}