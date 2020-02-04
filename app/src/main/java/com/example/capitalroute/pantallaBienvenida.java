package com.example.capitalroute;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class pantallaBienvenida extends AppCompatActivity {

    private final int duracion_SPLASH=3000;//Se declara la duracion de la pantalla de bienvenida

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_pantalla_bienvenida);

        //Aqui se edita el actionbar, pero al final lo escondo
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.drawable.logo);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().hide();
        //Fin de interaccion con el actionbar

        //PAra la pantalla de inicio
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent= new Intent(pantallaBienvenida.this,Login.class);
                    startActivity(intent);
                    finish();
                }
            },duracion_SPLASH);
        //Fin de la pantalla de inicio




    }
}
