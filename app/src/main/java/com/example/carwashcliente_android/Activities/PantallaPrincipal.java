package com.example.carwashcliente_android.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.example.carwashcliente_android.Config.ClientManager;
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

    ClientManager clientManager;
    private BottomNavigationView bottomNavigationView;
    private ImageView logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        // Configurar Bottom Navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        logOut = findViewById(R.id.LogoutPantallaPrincipal);
        clientManager = new ClientManager(this);

        logOut.setOnClickListener(v -> cerrarSesion());

        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_home) {
                abrirFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.nav_cotizacion) {
                abrirFragment(new SeleccionLugarFragment());
            } else if (item.getItemId() == R.id.nav_vehiculo) {
                abrirFragment(new VehiculoFragment());
            }
            return true;
        });
    }

    private void cerrarSesion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cerrar sesión");
        builder.setMessage("¿Estás seguro de que quieres cerrar sesión?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clientManager.clearClientData();
                Intent intent = new Intent(PantallaPrincipal.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
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
