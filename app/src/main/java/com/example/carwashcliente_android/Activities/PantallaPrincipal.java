package com.example.carwashcliente_android.Activities;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;



import com.example.carwashcliente_android.Fragments.FragmenHome;

import com.example.carwashcliente_android.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

public class PantallaPrincipal extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        // Configurar Bottom Navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        // Cargar el fragment inicial (por ejemplo, HomeFragment)
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contenedorFragments, new com.example.carwashcliente_android.Activities.HomeFragment())
                    .commit();
        }
    }

    // Listener para la Bottom Navigation
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    int itemId = item.getItemId();

                    if (itemId == R.id.nav_home) {
                        selectedFragment = new HomeFragment();
                    } else if (itemId == R.id.nav_cotizacion) {
                        selectedFragment = new CotizacionFragment();
                    } else if (itemId == R.id.nav_historial) {
                        selectedFragment = new HistorialFragment();
                    } else if (itemId == R.id.nav_vehiculo) {
                        selectedFragment = new VehiculoFragment();
                    }

                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contenedorFragments, selectedFragment)
                                .commit();
                    }

                    return true;
                }

            };
}; // Punto y coma?
