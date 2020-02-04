package com.example.capitalroute;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class Login extends AppCompatActivity {
    //Declaracion de variables
    EditText userLogin,passwordLogin;
    Button btnLogin;
    FirebaseAuth firebaseAutenticacion;
    //Fin declaracion de variables

    //Declaracion de variables para el inicio de sesión en FB
        private LoginButton loginButtonFB;
        private CallbackManager callbackManagerFB;
    //Fin de declaración de varibales para FB

    //Declaracion de variables para hacer el login con Google
        SignInButton botonLoginGoogle;
        private final static int RC_SIGN_IN=13;
        GoogleApiClient mGoogleSignInClient;
    //Fin de declaracion de variables para hacer el login con Google

    //Metodo que checa si ya estas logeado en firebase
        FirebaseAuth.AuthStateListener firebaseListener;

        @Override
        protected void onStart() {
            super.onStart();
            firebaseAutenticacion.addAuthStateListener(firebaseListener);
        }
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Aqui se edita el actionbar, pero al final lo escondo
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.drawable.logo);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().hide();
        //Fin de interaccion con el actionbar

        //Se crea el listenner para enviar al usuario a la actividad de registrar usuario
        final TextView linkRegistrarUsuario = (TextView) findViewById(R.id.registrarUsuarioLogin);
        linkRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Login.this, registrarUsuario.class);
                startActivity(myIntent);
            }
        });
        //Finaliza listenner de registrar usuario

        //Se crea el listenner para enviar al usuario a la actividad de registrar usuario
        final TextView linkOlvidarContra = (TextView) findViewById(R.id.linkOlvidarContrasegna);
        linkOlvidarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Login.this, olvidarContrasegna.class);
                startActivity(myIntent);
            }
        });
        //Finaliza listenner de registrar usuario

        //SE checa si ya se inicio sesion o no
          firebaseListener= new FirebaseAuth.AuthStateListener() {
              @Override
              public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser()!=null){
                        startActivity(new Intent(Login.this,Home.class));//Despues se cambia a home
                    }
              }
          };
        //Fin

        //Empieza el proceso de inciar sesión
            userLogin= findViewById(R.id.usuarioLogin);
            passwordLogin= findViewById(R.id.contrasegnaLogin);
            btnLogin= findViewById(R.id.botonLogin);
            firebaseAutenticacion= FirebaseAuth.getInstance();

            //Proceso cuando le das click al boton login
                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String emailL = userLogin.getText().toString().trim();
                        String contrasegnaL= passwordLogin.getText().toString().trim();

                        if(TextUtils.isEmpty(emailL)){
                            userLogin.setError("Ingese su Email, por favor");
                            return;
                        }

                        if(TextUtils.isEmpty(contrasegnaL)){
                            passwordLogin.setError("Ingrese su contraseña, por favor");
                            return;
                        }

                        //Se autentica el usuario y la contraseña
                            firebaseAutenticacion.signInWithEmailAndPassword(emailL,contrasegnaL).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()){
                                        Toast.makeText(Login.this, "Inicio de sesión correcto", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(),Home.class));
                                    }else{
                                        Toast.makeText(Login.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                        //Fin de la autenticacion
                    }
                });
            //Fin del proceso cuando le das click al boton login

        //Finaliza proceso de iniciar sesón

        //Proceso para iniciar sesión con Fb

            callbackManagerFB= CallbackManager.Factory.create();
            loginButtonFB = (LoginButton)findViewById(R.id.login_button_fb);
            loginButtonFB.setReadPermissions(Arrays.asList("email", "public_profile", "user_friends"));
            loginButtonFB.registerCallback(callbackManagerFB, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    irPantallaPrincipal();
                }

                @Override
                public void onCancel() {
                    Toast.makeText(getApplicationContext(), "Se canceló el inicio de sesión", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });

        //Fin del proceso para iniciar sesión con FB


        //Proceso para iniciar sesion con Google
            botonLoginGoogle= (SignInButton)findViewById(R.id.btnGoogle);
            // Build a GoogleSignInClient with the options specified by gso.
            //mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

            mGoogleSignInClient= new GoogleApiClient.Builder(this).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                    Toast.makeText(getApplicationContext(), "Upps, algo salió mal", Toast.LENGTH_LONG).show();
                }
            })
                    .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                    .build();

            botonLoginGoogle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signIn();
                }
            });
        //Fin del proceso para iniciar sesion con Google

    }



    //Esto tambien es para loggear en FB

        private void irPantallaPrincipal() {
            Intent intent = new Intent(this,Home.class);//Esto debo cambiar que redireccione a home
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);//Para que no vuelva a login sino que cierre sesion
            startActivity(intent);
        }

        @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                callbackManagerFB.onActivityResult(requestCode,resultCode,data);

                //Codigo para iniciar sesion en google, seria lo de onactivity result

                // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
                if (requestCode == RC_SIGN_IN) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    try {
                        // Google Sign In was successful, authenticate with Firebase
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            firebaseAuthWithGoogle(account);
                        } catch (ApiException e) {
                        // Google Sign In failed, update UI appropriately
                            Toast.makeText(getApplicationContext(), "Upps, algo salió mal", Toast.LENGTH_LONG).show();
                            // ...
                        }
                }


            //Fin del de iniciar el Gmail
            }



    //Termina lo de loggear en Fb


        //Funciones para loggear en Google
        // Configure Google Sign In
        //GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
           //     .requestIdToken(getString(R.string.default_web_client_id))
             //   .requestEmail()
               // .build();


            private void signIn() {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }

            private void firebaseAuthWithGoogle(GoogleSignInAccount account) {


                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                firebaseAutenticacion.signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "signInWithCredential:success");
                                    FirebaseUser user = firebaseAutenticacion.getCurrentUser();
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "signInWithCredential:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Upps, algo salió mal:"+task.getException().toString(), Toast.LENGTH_LONG).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });




            }
        //Terminan funciones para loggear en Google
}
