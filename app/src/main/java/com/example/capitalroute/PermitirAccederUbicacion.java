package com.example.capitalroute;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PermitirAccederUbicacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permitir_acceder_ubicacion);

        //Aqui se edita el actionbar, pero al final lo escondo
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();
        //Fin de interaccion con el actionbar



    }
}
