package br.com.estacio.projeto.pontocerto;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import br.com.estacio.projeto.pontocerto.activity.ConsultaEventosActivity;
import br.com.estacio.projeto.pontocerto.activity.RegistrarPontoActivity;
import br.com.estacio.projeto.pontocerto.gps.GPSTracker;
import br.com.estacio.projeto.pontocerto.util.ValidaGpsAndIntenet;


public class MainActivity extends AppCompatActivity {

    private Button buttonRegistrar;
    private Button buttonConsultarMarcacoes;
    private Intent intentRedirecionar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initComponentes();

        initAcoesComponentes();

        new GPSTracker().goLocalizacao(MainActivity.this, MainActivity.this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(this, "Tentou Acessar Configurações", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initComponentes() {
        buttonRegistrar = findViewById(R.id.buttonRegistrarPonto);

        buttonConsultarMarcacoes = findViewById(R.id.buttonConsultarMarcacoesMain);

    }

    private void initAcoesComponentes() {

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentRedirecionar = new Intent(v.getContext(), RegistrarPontoActivity.class);
                if (verificarRequisitosMinimos())
                    startActivity(intentRedirecionar);
            }
        });

        buttonConsultarMarcacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentRedirecionar = new Intent(v.getContext(), ConsultaEventosActivity.class);
                startActivity(intentRedirecionar);
            }
        });
    }

    boolean verificarRequisitosMinimos() {

        if (!ValidaGpsAndIntenet.netWorkdisponibilidade(this)) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Ativar a Internet");
            alert.setMessage(R.string.vld_internet);
            alert.setPositiveButton("WIFI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivityForResult(intent, 1);
                }
            });

            alert.setNegativeButton("NAO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                }
            });

            alert.create();
            alert.show();

            return false;
        }
        if (!ValidaGpsAndIntenet.gpsDisponibilidade(this)) {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Ativar o GPS");
            alert.setMessage(R.string.vld_gps);
            alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 1);
                }
            });

            alert.setNegativeButton("NAO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                }
            });

            alert.create();
            alert.show();

            return false;
        }

        return true;
    }

    public boolean netWorkdisponibilidade(Context context) {
        boolean ativado = false;
        ConnectivityManager conmag;
        conmag = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        conmag.getActiveNetworkInfo();
        //Verifica o WIFI
        if (conmag.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
            ativado = true;
        }
        //Verifica o 3G/4G
        else if (conmag.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
            ativado = true;
        } else {
            ativado = false;
        }
        return ativado;
    }

    public boolean gpsDisponibilidade(Context context) {
        String provider = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (null == provider || provider.equals(0) || "".equals(provider))
            return false;
        else
            return true;
    }


}
