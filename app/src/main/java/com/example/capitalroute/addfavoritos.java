package com.example.capitalroute;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;


public class addfavoritos extends AppCompatActivity {

    PlacesClient placesClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfavoritos);

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
                Log.i("PlacesApi","OnPlaceSelected:"+latLng.latitude+"\n"+latLng.longitude);



            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });

    }


}
