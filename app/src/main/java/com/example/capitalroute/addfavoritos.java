package com.example.capitalroute;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;

//import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.StatusLine;

import static android.widget.Toast.LENGTH_LONG;


public class addfavoritos extends AppCompatActivity {

    PlacesClient placesClient;
    LatLng latLngGuardar = null; //Variable global de las coordenadas
    EditText nombreLugar; //Nombre del lugar que desear guardar
    FirebaseAuth firebaseAutenticacion; //Se declara la variable para interactuar con firebase
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfavoritos);
        //Para añadir la flecha de go back a la actionbar
        //getActionBar().setTitle("lo");


        //Fin de añadir flecha de go back a la actionbar
        if(!Places.isInitialized()){
            Places.initialize(getApplicationContext(), getResources().getString(R.string.googleplacesapikey));
        }

        placesClient= Places.createClient(this);

        final AutocompleteSupportFragment autocompleteSupportFragment=
                (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.LAT_LNG,Place.Field.NAME));
        autocompleteSupportFragment.setCountry("MX");
        autocompleteSupportFragment.setLocationBias(RectangularBounds.newInstance(
                new LatLng(-99.11859756999995, 19.584461719999087),
                //new LatLng(-99.11690613999994, 19.581444599999138),
                new LatLng(-99.1146617199999, 19.57979630999918)));


        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                final LatLng latLng = place.getLatLng();
                latLngGuardar=latLng;
                Log.i("PlacesApi","OnPlaceSelected:"+latLng.latitude+"\n"+latLng.longitude);



            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });

    }


    public void goBackHomeFromFav(View view) {
        Intent myIntent = new Intent(addfavoritos.this, Home.class);
        startActivity(myIntent);
    }

    public void guardarLugarServicio(View view) {

        nombreLugar=findViewById(R.id.nombrelugar);
        firebaseAutenticacion= FirebaseAuth.getInstance();//Se inicializa la instacia de firebase
        //Checamos si el usuario ya está loggeado
        if (firebaseAutenticacion.getCurrentUser()==null){
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }
        //Fin del "chequeo"
        //Se valida que los campos esten lleenos
        String nameSavePlace=nombreLugar.getText().toString().trim();
        if(TextUtils.isEmpty(nameSavePlace)){
            nombreLugar.setError("Ingrese el nombre del lugar que desea guardar, por favor");
            return;
        }

        String ui= firebaseAutenticacion.getCurrentUser().getUid();//Obtiene el UI
        String emailUsuario= firebaseAutenticacion.getCurrentUser().getEmail();
        //Toast.makeText(this, "UiID:"+ui+"\nCorreo:"+emailUsuario+"\nNombre:"+nameSavePlace+"\nLas coordenadas son:\n Latitud:"+latLngGuardar.latitude+"\nLongitud:"+latLngGuardar.longitude, LENGTH_LONG).show();
        solicitudGuardarLugar("agregarLugar",emailUsuario,nameSavePlace);
        //startActivity(new Intent(getApplicationContext(),Login.class));
        //finish();
    }

    private void solicitudGuardarLugar(String opcion,String emailUsuario,String nombreLugar) {

        OkHttpClient client= new OkHttpClient();
        nombreLugar= nombreLugar.replaceAll(" ", "%20");
        String url= "http://lugares.us-east-2.elasticbeanstalk.com/?opcion="+opcion+"&idUsuario="+emailUsuario+"&lat="+latLngGuardar.latitude+"&long="+latLngGuardar.longitude+"&nombre="+nombreLugar;
        System.out.println(url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    final String myResponse = response.body().string();
                    addfavoritos.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(addfavoritos.this,myResponse, LENGTH_LONG).show();
                        }
                    });
                    
                }
            }
        });

    }
}
