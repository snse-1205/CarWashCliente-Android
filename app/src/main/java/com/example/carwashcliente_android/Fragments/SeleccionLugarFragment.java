package com.example.carwashcliente_android.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.carwashcliente_android.R;


public class SeleccionLugarFragment extends Fragment {

    public SeleccionLugarFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seleccion_lugar, container, false);

        Button btnLocal = view.findViewById(R.id.btnLocal);
        Button btnDomicilio = view.findViewById(R.id.btnDomicilio);

        btnLocal.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedorFragments, new LocalFragment())
                    .addToBackStack(null)
                    .commit();
        });

        btnDomicilio.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedorFragments, new DomicilioFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

}