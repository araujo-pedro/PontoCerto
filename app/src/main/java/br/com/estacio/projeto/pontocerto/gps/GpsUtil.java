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

public class GpsUtil extends Activity {

    private Context contextThis;
    private AppCompatActivity activityThis;
    private String resultado;

    public GpsUtil() {
        /*this.contextThis = context;
        this.activityThis = activity;

        this.pedirPermissoes();*/
    }

    public void goLocalizacao(Context context, AppCompatActivity activity) {
        contextThis = context;
        activityThis = activity;

        pedirPermissoes();
    }

    private void pedirPermissoes() {

        if (ActivityCompat.checkSelfPermission(contextThis, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(contextThis, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activityThis, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else
            configurarServico();
    }


    @Override
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


    private void configurarServico() {
        try {
            LocationManager locationManager = (LocationManager) activityThis.getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new LocationListener() {
                //Log.d("LocationListener", "ENTROU");
                public void onLocationChanged(Location location) {
                    Log.d("LocationListener", "ENTROU");
                    Double latPoint = location.getLatitude();
                    Double lngPoint = location.getLongitude();


                    resultado = latPoint.toString() + " " + lngPoint.toString();
                    Log.d("LocationListener", resultado);
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            };
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            Boolean networkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Log.d("NETWORK", networkEnable.toString());
            Boolean gpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.d("GPS", gpsEnable.toString());

            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);
        } catch (SecurityException ex) {
            Toast.makeText(activityThis, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void  atualizar(Location location){
        Double latPoint = location.getLatitude();
        Double lngPoint = location.getLongitude();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(latPoint.toString());
        stringBuilder.append(" || ");
        stringBuilder.append(latPoint.toString());

        this.resultado = stringBuilder.toString();
    }

    public String getResultado() {
        return resultado;
    }
}
