package com.example.carwashcliente_android.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carwashcliente_android.R;


public class SeleccionServiciosFragment extends Fragment {

    public SeleccionServiciosFragment() {
        // Required empty public constructor
    }

    private int idvehiculo;
    int marca;
    public static SeleccionServiciosFragment newInstance(int idvehiculo) {
        SeleccionServiciosFragment fragment = new SeleccionServiciosFragment();
        Bundle args = new Bundle();
        args.putInt("idvehiculo", idvehiculo);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            marca = getArguments().getInt("idvehiculo");
        }

        Log.d("ID: ", "Vehiculo id: "+marca);
        return inflater.inflate(R.layout.fragment_seleccion_servicios, container, false);
    }

}