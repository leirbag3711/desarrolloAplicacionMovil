package com.example.capitalroute;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class GPStracker implements LocationListener {

    Context context;

    @Override
    public void onLocationChanged(Location location) {

    }

    public Location getLocation(){

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            Toast.makeText(context,"PEermiso no autorizado",Toast.LENGTH_SHORT).show();
            return null;
        }

        LocationManager ln= (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled= ln.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isGPSEnabled){
            ln.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,10,this);
            Location l= ln.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return  l;

        }else{
            Toast.makeText(context,"Encienda el GPS",Toast.LENGTH_LONG).show();

        }

        return null;
    }



    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public GPStracker(Context c){
        context= c;
    }

}
