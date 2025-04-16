package com.example.carwashcliente_android.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carwashcliente_android.Models.Cotizacion;
import com.example.carwashcliente_android.R;


public class SeleccionServiciosFragment extends Fragment {

    public SeleccionServiciosFragment() {
        // Required empty public constructor
    }
    Cotizacion cotizacion;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seleccion_servicios, container, false);
        if (getArguments() != null) {
            cotizacion = (Cotizacion) getArguments().getSerializable("cotizacion");
            if (cotizacion != null) {

                Log.d("Cotizacion modalidad",cotizacion.getModalidad()+"");
            }
        }

        Log.d("ID: ", "Vehiculo id: "+cotizacion.getIdCarro()+"");
        return view;
    }

}