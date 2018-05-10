package br.com.estacio.projeto.pontocerto.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import br.com.estacio.projeto.pontocerto.MainActivity;
import br.com.estacio.projeto.pontocerto.R;
import br.com.estacio.projeto.pontocerto.gps.GpsUtil;

public class RegistrarPontoActivity extends AppCompatActivity {

    private TextView txtHorario;
    private TextView txtHorarioRegistrado;
    private TextView txtLocalizacao;
    private Button buttonRegistar;
    private Button buttonConsultarMarcacoes;

    private String resultado;

    private GpsUtil gpsUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_ponto);

        this.initComponentes();

        this.criaAcoesComponentes();
    }

    private void initComponentes() {
        this.txtHorario = this.findViewById(R.id.txtHorario);
        this.txtHorarioRegistrado = this.findViewById(R.id.txtHorarioRegistrado);
        this.txtLocalizacao = this.findViewById(R.id.txtLocalizacao);
        this.buttonRegistar = this.findViewById(R.id.buttonRegistrar);
        this.buttonConsultarMarcacoes = this.findViewById(R.id.buttonRegistrar);

    }

    private void criaAcoesComponentes() {

        final TextView relogio = findViewById(R.id.txtHorario);

        final Handler atualizador = new Handler();
        atualizador.post(new Runnable() {
            @Override
            public void run() {
                atualizaHora(relogio);
                atualizador.postDelayed(this, 50);
            }
        });

        this.buttonRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pedirPermissoes();

                gpsUtil = new GpsUtil();
                gpsUtil.goLocalizacao(RegistrarPontoActivity.this, RegistrarPontoActivity.this);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        System.out.println(">>>> ESTA EXECUTANDO");
                        resultado = gpsUtil.getResultado();

                        txtHorarioRegistrado.setText(relogio.getText().toString());
                        txtLocalizacao.setText(resultado);
                    }
                }, 10000);


            }
        });
    }

    private void atualizaHora(TextView relogio) {
        GregorianCalendar calendario = new GregorianCalendar();

        int h = calendario.get(GregorianCalendar.HOUR_OF_DAY);
        int m = calendario.get(GregorianCalendar.MINUTE);
        int s = calendario.get(GregorianCalendar.SECOND);

        
        relogio.setText(h + ":" + m + ":" + s);
    }
/*
    private void pedirPermissoes() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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
                    Toast.makeText(this, "Não vai funcionar!!!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    public void configurarServico() {
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    atualizar(location);
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
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void atualizar(Location location) {
        Double latPoint = location.getLatitude();
        Double lngPoint = location.getLongitude();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(latPoint.toString());
        stringBuilder.append(" || ");
        stringBuilder.append(latPoint.toString());

        this.txtLocalizacao.setText(stringBuilder.toString());


    }
*/

}
