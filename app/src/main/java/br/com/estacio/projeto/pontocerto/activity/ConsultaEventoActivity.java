package br.com.estacio.projeto.pontocerto.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.estacio.projeto.pontocerto.R;
import br.com.estacio.projeto.pontocerto.repository.EventoRepository;
import br.com.estacio.projeto.pontocerto.util.ValidaGpsAndIntenet;

public class ConsultaEventoActivity extends AppCompatActivity {

    private final SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");

    private ListView listViewDataEventos;
    private Button buttonRegistrarPontoConsulta;
    private Intent intentRedirecionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_evento);

        this.initComponentes();

        this.carregaOpcoesLista();

        this.criarEventos();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void initComponentes() {

        this.listViewDataEventos = this.findViewById(R.id.listViewDataEvento);
        this.buttonRegistrarPontoConsulta = this.findViewById(R.id.buttonRegistrarEventoConsulta);
    }

    private void carregaOpcoesLista() {

        List<Date> dataEventos = new EventoRepository(this).dataEventos();

        String[] itens = new String[dataEventos.size()];

        for (int i = 0; i < dataEventos.size(); i++) {
            itens[i] = formatData.format(dataEventos.get(i));
        }

        ArrayAdapter<String> arrayItens = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itens);

        listViewDataEventos.setAdapter(arrayItens);
    }

    private void criarEventos() {
        listViewDataEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String opcaoSelecionada = ((TextView) view).getText().toString();

                redirecionaTela(opcaoSelecionada);

            }
        });

        buttonRegistrarPontoConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intentRedirecionar = new Intent(v.getContext(), RegistrarPontoActivity.class);
                if (verificarRequisitosMinimos())
                    startActivity(intentRedirecionar);
            }
        });
    }

    private void redirecionaTela(String opcaoMenu) {

        Intent intentRedirecionar = new Intent(this, EventoDataActivity.class);
        intentRedirecionar.putExtra("data_evento_selecionado", opcaoMenu);
        startActivity(intentRedirecionar);
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
}
