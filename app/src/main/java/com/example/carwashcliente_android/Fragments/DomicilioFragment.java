package com.example.carwashcliente_android.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.carwashcliente_android.R;


public class DomicilioFragment extends Fragment {


    public DomicilioFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_domicilio, container, false);

        Button btnAgregar = view.findViewById(R.id.btnAgregarDomicilio);
        Button btnCancelar = view.findViewById(R.id.btnCancelarDomicilio);

        btnAgregar.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedorFragments, new SeleccionVehiculoFragment())
                    .addToBackStack(null)
                    .commit();
        });

        btnCancelar.setOnClickListener(v ->
                requireActivity().getOnBackPressedDispatcher().onBackPressed()
        );

        return view;
    }

}