package com.example.capitalroute;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class cerrarsesionprueba extends AppCompatActivity {
    AlertDialog alert= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerrarsesionprueba);

        //Obtener la ubicación
            ActivityCompat.requestPermissions( cerrarsesionprueba.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
            GPStracker g = new GPStracker(getApplicationContext());
            EditText lati,longi;
            lati= findViewById(R.id.lalitud);
            longi=findViewById(R.id.longitud);
            Location l= g.getLocation();
            if(l != null) {
                double lat = l.getLatitude();
                double lon = l.getLongitude();

                lati.setText(String.valueOf(lat));
                longi.setText(String.valueOf(lon));

            }else {
                encenderGPS();
            }
        //Fin de obtener la ubicación

        //Con este if se verifica que tenga inicio de sesión con Fb, debes ponerlo en home
            //if (AccessToken.getCurrentAccessToken()== null){
              //  irPantallaLogin();//Este método manda al usuario a
            //}
        //fin del if de verifiación

    }

    //Metodo para mandar a login si el usuario no ha iniciado sesion
        private void irPantallaLogin() {
            Intent intent = new Intent(this,Login.class);//Esto debo cambiar que redireccione a home
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);//Para que no vuelva a login sino que cierre sesion
            startActivity(intent);
        }
    //Fin del método para mandar al inicio de sesion
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//Cierra sesion
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

    //Mandar a MAinPAge
    public void irMP(View view) {
        //startActivity(new Intent(cerrarsesionprueba.this,cerrarsesionprueba.this));//Aqui iria a Homee
        //finish();
    }
    //Fin de mandar a MainPage


    //Metodo para cerrar sesion en FB
    public void logoutFb(View view) {
        LoginManager.getInstance().logOut();
        irPantallaLogin();
    }
    //Fin del metodo para cerrar sesion en Fb

    //Encender GPS
    private void encenderGPS(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Para disfrutar todo lo que el sistema tiene para ofrecerle, encienda el GPS de su móvil y dele acceso a nuestro sistema a su ubicación")
                .setCancelable(false).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert=builder.create();
        alert.show();

    }


    //Encender GPS
}
