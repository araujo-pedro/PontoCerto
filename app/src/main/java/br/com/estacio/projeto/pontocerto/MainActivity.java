package br.com.estacio.projeto.pontocerto;

import android.content.Intent;
import android.os.Bundle;

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


}
