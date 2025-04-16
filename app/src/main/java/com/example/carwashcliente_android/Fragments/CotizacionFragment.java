package com.example.carwashcliente_android.Fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.carwashcliente_android.R;

public class CotizacionFragment extends Fragment {

    public CotizacionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cotizacion, container, false);

        Button btnSolicitar = (Button) view.findViewById(R.id.btn_solicitar_cotizacion);

        btnSolicitar.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedorFragments, new SeleccionLugarFragment())
                    .addToBackStack(null)
                    .commit();
        });
        return view;
    }
}