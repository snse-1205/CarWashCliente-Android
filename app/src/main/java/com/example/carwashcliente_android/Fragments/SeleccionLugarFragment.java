package com.example.carwashcliente_android.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.carwashcliente_android.Models.Cotizacion;
import com.example.carwashcliente_android.R;


public class SeleccionLugarFragment extends Fragment {

    Cotizacion cotizacion = new Cotizacion();
    public SeleccionLugarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seleccion_lugar, container, false);

        Button btnLocal = view.findViewById(R.id.btnLocal);
        Button btnDomicilio = view.findViewById(R.id.btnDomicilio);

        btnLocal.setOnClickListener(v -> {
            cotizacion.setModalidad(1);
            Bundle bundle = new Bundle();
            bundle.putSerializable("cotizacion", cotizacion);
            Fragment fragment = new LocalFragment();
            fragment.setArguments(bundle);
            cambiarFragment(fragment);
        });

        btnDomicilio.setOnClickListener(v -> {
            cotizacion.setModalidad(2);
            Bundle bundle = new Bundle();
            bundle.putSerializable("cotizacion", cotizacion);

            Fragment fragment = new DomicilioFragment();
            fragment.setArguments(bundle);
            cambiarFragment(fragment);
        });

        return view;
    }

    private void cambiarFragment(Fragment fragment){
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedorFragments, fragment)
                .addToBackStack(null)
                .commit();
    }

}