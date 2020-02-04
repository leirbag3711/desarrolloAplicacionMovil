package com.example.capitalroute;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class olvidarContrasegna extends AppCompatActivity {

    //Declaracion de variables para recuperar contraseña
        EditText emailRecuperarContrasegna;
        Button botonParaEnviarEmailDeRecuperacion;
        FirebaseAuth mAuth;
    //Fin de declaracion de varaibles para recuperar contraseña

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvidar_contrasegna);

        //Aqui se edita el actionbar, pero al final lo escondo
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.drawable.logo);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().hide();
        //Fin de interaccion con el actionbar

        //Se crea el listenner para regresar al inicio
        final TextView linkVolverAlInicio = (TextView) findViewById(R.id.volverAlInicioRecuperarContra);
        linkVolverAlInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(olvidarContrasegna.this, Login.class);
                startActivity(myIntent);
            }
        });
        //Finaliza listenner de volver al inicio

        //Inicia proceso para recuperar contraseña
            mAuth= FirebaseAuth.getInstance();
            emailRecuperarContrasegna= (EditText) findViewById(R.id.emailRecup);
            botonParaEnviarEmailDeRecuperacion= (Button)findViewById(R.id.btnEnviarEmail);

            botonParaEnviarEmailDeRecuperacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String emailRecuperar= emailRecuperarContrasegna.getText().toString().trim();
                    //Toast.makeText(olvidarContrasegna.this,emailRecuperar,Toast.LENGTH_LONG).show();
                    RecuperacionCuenta(emailRecuperar);//Se envia el email al metodo para recuperar la contraseña

                }
            });

        //Fin del proceso de recuperar contraseña



    }

    //Inicia método para recuperar contraseña
    public void RecuperacionCuenta(String emailRecuperarF) {
        Toast.makeText(olvidarContrasegna.this,emailRecuperarF,Toast.LENGTH_LONG).show();

        mAuth.sendPasswordResetEmail(emailRecuperarF).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Toast.makeText(olvidarContrasegna.this,"Email enviado",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(olvidarContrasegna.this,"Email no enviado",Toast.LENGTH_LONG).show();
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                //Mostrar el trxto de porque falló
                Toast.makeText(olvidarContrasegna.this,"Error:"+e.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

        startActivity(new Intent(olvidarContrasegna.this,Login.class));
        finish();
    }

    //Fin del método para recuperar contraseña
}
