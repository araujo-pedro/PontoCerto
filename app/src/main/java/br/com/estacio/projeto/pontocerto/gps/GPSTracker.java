package br.com.estacio.projeto.pontocerto.gps;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Pedro Araujo
 */
public class GPSTracker {

    private Context contextThis;
    private AppCompatActivity activityThis;
    private String resultado;
    private static Location ultimaLocalizacao;

    //@Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    configurarServico();
                } else {
                    Toast.makeText(contextThis, "Não vai funcionar!!!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }




    public void goLocalizacao(Context context, AppCompatActivity activity) {

        contextThis = context;
        activityThis = activity;

        pedirPermissoes();
    }

    private void pedirPermissoes() {

        if (!verificaPermissao()) {

            ActivityCompat.requestPermissions(activityThis, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            pedirPermissoes();

        } else
            configurarServico();
    }

    private Boolean verificaPermissao() {

        if (ActivityCompat.checkSelfPermission(contextThis, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(contextThis, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else
            return true;
    }





    private void configurarServico() {
        try {
            LocationManager locationManager = (LocationManager) activityThis.getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    Log.d("LocationListener", "ENTROU");
                    Double latPoint = location.getLatitude();
                    Double lngPoint = location.getLongitude();


                    resultado = latPoint.toString() + " " + lngPoint.toString();
                    Log.d("LocationListener", resultado);

                    ultimaLocalizacao = location;
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            };

            Boolean networkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Log.d("NETWORK_ENABLE", networkEnable.toString());
            Boolean gpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.d("GPS_ENABLE", gpsEnable.toString());

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException ex) {
            Toast.makeText(activityThis, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public static Location getUltimaLocalizacao() {

        return ultimaLocalizacao;
    }


}
