package com.example.carwashcliente_android.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.example.carwashcliente_android.Fragments.CotizacionFragment;

import com.example.carwashcliente_android.Fragments.HistorialFragment;
import com.example.carwashcliente_android.Fragments.HomeFragment;
import com.example.carwashcliente_android.Fragments.SeleccionLugarFragment;
import com.example.carwashcliente_android.Fragments.VehiculoFragment;
import com.example.carwashcliente_android.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class PantallaPrincipal extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        // Configurar Bottom Navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_home) {
                abrirFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.nav_cotizacion) {
                abrirFragment(new SeleccionLugarFragment());
            } else if (item.getItemId() == R.id.nav_historial) {
                abrirFragment(new HistorialFragment());
            } else if (item.getItemId() == R.id.nav_vehiculo) {
                abrirFragment(new VehiculoFragment());
            }
            return true;
        });
    }

    private void abrirFragment(Fragment fragment, int action) {
        Bundle args = new Bundle();
        args.putInt("ACTION", action);
        fragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedorFragments, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void abrirFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedorFragments, fragment)
                .addToBackStack(null)
                .commit();
    }

}
