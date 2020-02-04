package com.example.capitalroute;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registrarUsuario extends AppCompatActivity {
    //Se declaran las variables que se van a utilizar para registar al usuario
        EditText emailUsuario,confirmarEmailUsuario,contrasegnaUsuario,confirmarContrasegnaUsuario;
        Button botonRegistrarUsuario;
        FirebaseAuth firebaseAutenticacion; //Se declara la variable para interactuar con firebase
    //Fin de la declaracion de varibles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);


        //Aqui se edita el actionbar, pero al final lo escondo
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.drawable.logo);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().hide();
        //Fin de interaccion con el actionbar

        //Se crea el listenner para regresar al inicio
        final TextView linkVolverAlInicio = (TextView) findViewById(R.id.volverAlInicio);
        linkVolverAlInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(registrarUsuario.this, Login.class);
                startActivity(myIntent);
            }
        });
        //Finaliza listenner de regresar al inicio

        //Empieza el proceso de registrar un nuevo usuario

            //Igualacion de variables
            emailUsuario= findViewById(R.id.emailReg);
            confirmarEmailUsuario= findViewById(R.id.confirmarEmailReg);
            contrasegnaUsuario= findViewById(R.id.contraReg);
            confirmarContrasegnaUsuario= findViewById(R.id.confirmarContraReg);
            botonRegistrarUsuario= findViewById(R.id.botonRegistrarse);
            //Fin de igualacion de varaibles
            firebaseAutenticacion= FirebaseAuth.getInstance();//Se inicializa la instacia de firebase

            //Checamos si el usuario ya está loggeado
                if (firebaseAutenticacion.getCurrentUser()!=null){
                    startActivity(new Intent(getApplicationContext(),Login.class));
                    finish();
                }
            //Fin del "chequeo"

            //Declaracion del evento del boton de registrarse
            botonRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Declaracion de variables
                    String emailR = emailUsuario.getText().toString().trim();
                    String confEmailR= confirmarEmailUsuario.getText().toString().trim();
                    String contraR= contrasegnaUsuario.getText().toString().trim();
                    String confContraR= confirmarContrasegnaUsuario.getText().toString().trim();
                    //Fin declaracion de variables

                    //Se valida que los campos esten lleenos
                    if(TextUtils.isEmpty(emailR)){
                        emailUsuario.setError("Ingrese su email, por favor");
                        return;
                    }

                    if(TextUtils.isEmpty(confEmailR)){
                        confirmarEmailUsuario.setError("Confirme su email, por favor");
                        return;
                    }

                    if(TextUtils.isEmpty(contraR)){
                        contrasegnaUsuario.setError("Ingrese su contraseña, por favor");
                        return;
                    }

                    if(TextUtils.isEmpty(confContraR)){
                        confirmarContrasegnaUsuario.setError("Confirme su contraseña, por favor");
                        return;
                    }

                    //Fin de la validacion de los campos

                    //Se valida que los email y las contraseñas sean iguales
                    if (!emailR.equals(confEmailR)){
                        Toast.makeText(registrarUsuario.this, "Los emails ingresados no coinciden", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (!contraR.equals(confContraR)){
                        Toast.makeText(registrarUsuario.this, "Las contraseñas ingresadas no coinciden", Toast.LENGTH_LONG).show();
                        return;
                    }

                    //Fin de la validación de los emails  contraseñas

                    //Inica el registro de usuario
                        firebaseAutenticacion.createUserWithEmailAndPassword(emailR,contraR).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()){
                                    Toast.makeText(registrarUsuario.this, "Usuario Registrado correctamente", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(),Login.class));
                                }else{
                                    Toast.makeText(registrarUsuario.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    //FInaliza el registro de usuario


                }

            });
            //Fin del evento del boton de registrarse


        //Finaliza el proceso de registrar un nuevo usuario

    }
}
