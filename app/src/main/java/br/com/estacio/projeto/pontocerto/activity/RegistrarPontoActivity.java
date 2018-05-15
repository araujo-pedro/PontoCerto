package br.com.estacio.projeto.pontocerto.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.estacio.projeto.pontocerto.R;
import br.com.estacio.projeto.pontocerto.gps.GPSTracker;
import br.com.estacio.projeto.pontocerto.model.Evento;
import br.com.estacio.projeto.pontocerto.model.TipoEvento;
import br.com.estacio.projeto.pontocerto.repository.EventoRepository;

public class RegistrarPontoActivity extends AppCompatActivity {

    private SimpleDateFormat formatHorario = new SimpleDateFormat("HH:mm:ss");
    //
    private final Handler atualizador = new Handler();
    private final Handler atualizadorButtonRegistrar = new Handler();
    //
    private TextView txtRelogio;
    private TextView txtHorario;
    private TextView txtHorarioRegistrado;
    private TextView txtLocalizacao;
    private TextView txtStatusLocalizacao;
    private Button buttonRegistar;
    //private Button buttonConsultarMarcacoes;
    private ImageButton buttonConsultarMarcacoes;
    private TextView txtTipoEventoExibido;
    //
    private GPSTracker gpsUtil;


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
        this.buttonConsultarMarcacoes = (ImageButton) this.findViewById(R.id.buttonConsultarMarcacoes);
        this.txtRelogio = findViewById(R.id.txtHorario);
        this.txtStatusLocalizacao = this.findViewById(R.id.txtStatusLocalizacao);
        this.txtTipoEventoExibido = this.findViewById(R.id.textViewTipoEventoExibido);

    }

    private void criaAcoesComponentes() {

        taskAtualizadorHora();

        taskAtualizadorButtonRegistrar();

        this.buttonRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar_onClick();
            }
        });


        this.buttonConsultarMarcacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRedirecionar;
                intentRedirecionar = new Intent(v.getContext(), ConsultaEventoActivity.class);
                startActivity(intentRedirecionar);
            }
        });

    }

    private void registrar_onClick() {
        if (null == GPSTracker.getUltimaLocalizacao()) {
            new GPSTracker().goLocalizacao(RegistrarPontoActivity.this, RegistrarPontoActivity.this);
        }

        Location localizacao = GPSTracker.getUltimaLocalizacao();
        Date dataAtual = new Date();

        Evento evento = new Evento();

        evento.setDataHorarioEvento(dataAtual);
        evento.setDataEvento(dataAtual);
        evento.setIdUsuario(208L);
        evento.setLongitude(localizacao.getLongitude());
        evento.setLatitude(localizacao.getLatitude());

        // ==========================================================================
        // DETERMINA QUAL O TIPO DE EVENTO
        // ==========================================================================

        List<Evento> eventos = new EventoRepository(this).eventosPorData(evento.getDataEvento());

        Integer sizaZero = 0;
        if (null == eventos || sizaZero.equals(eventos.size())) {
            evento.setTipoEvento(1);
        } else {
            if (eventos.get(eventos.size() - 1).getTipoEvento().equals(1))
                evento.setTipoEvento(0);
            else if (eventos.get(eventos.size() - 1).getTipoEvento().equals(0))
                evento.setTipoEvento(1);

        }


//        List<Evento> eventos = new EventoRepository(this).eventosPorData(evento.getDataEvento());
//
//        int qtdEvento = eventos != null ? eventos.size() : 0;
//
//        switch (qtdEvento) {
//            case 0:
//                evento.setTipoEvento(TipoEvento.ENTRADA);
//                break;
//            case 1:
//                evento.setTipoEvento(TipoEvento.INICIO_ALMOCO);
//                break;
//            case 2:
//                evento.setTipoEvento(TipoEvento.FIM_ALMOCO);
//                break;
//            case 3:
//                evento.setTipoEvento(TipoEvento.SAIDA);
//                break;
//            default:
//                evento.setTipoEvento(TipoEvento.OUTRO);
//        }


        // ==========================================================================
        // ==========================================================================
        System.out.println(" >>> Vai SALVAR");
        new EventoRepository(this).savar(evento);
        System.out.println(" >>> PASSOU PELO SALVAR");

        Toast.makeText(this, "Registro SALVO", Toast.LENGTH_SHORT).show();

        // ==========================================================================
        // INFORMAÇÕES NA TELA
        // ==========================================================================
        String lat = new String().valueOf(localizacao.getLatitude());
        String lng = new String().valueOf(localizacao.getLongitude());

        txtHorarioRegistrado.setText(txtHorario.getText().toString());
        txtLocalizacao.setTextColor(Color.BLACK);
        txtLocalizacao.setText("CONFIRMADA");

        if(evento.getTipoEvento().equals(1)){
            txtTipoEventoExibido.setText("ENTRADA");
        }else{
            txtTipoEventoExibido.setText("SAIDA");
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txtHorarioRegistrado.setText("");
                txtLocalizacao.setText("");
                txtTipoEventoExibido.setText("");
            }
        }, 1500L);
        // ==========================================================================
        // ==========================================================================
    }

//    private void registrar_onClick() {
//        Log.d(">>PASSOU_PELO", "registrar_onClick()");
//        if (null == GPSTracker.getUltimaLocalizacao()) {
//            new GPSTracker().goLocalizacao(RegistrarPontoActivity.this, RegistrarPontoActivity.this);
//        }
//
//        Location localizacao = GPSTracker.getUltimaLocalizacao();
//
//        Evento evento = new Evento();
//
//        evento.setDataHorarioEvento(new Date());
//        evento.setIdUsuario(208L);
//        evento.setLongitude(localizacao.getLongitude());
//        evento.setLatitude(localizacao.getLatitude());
//
//        System.out.println(" >>> Vai SALVAR");
//        new EventoRepository(this).savar(evento);
//        System.out.println(" >>> PASSOU PELO SALVAR");
//
//        Toast.makeText(this, "Registro SALVO", Toast.LENGTH_SHORT).show();
//
//        // ==========================================================================
//        // INFORMAÇÕES NA TELA
//        // ==========================================================================
//        String lat = new String().valueOf(localizacao.getLatitude());
//        String lng = new String().valueOf(localizacao.getLongitude());
//
//        txtHorarioRegistrado.setText(txtHorario.getText().toString());
//        txtLocalizacao.setTextColor(Color.BLACK);
//        txtLocalizacao.setText("CONFIRMADA");
//
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                txtHorarioRegistrado.setText("");
//                txtLocalizacao.setText("");
//            }
//        }, 1500L);
//        // ==========================================================================
//        // ==========================================================================
//    }

    private void taskAtualizadorHora() {
        atualizador.post(new Runnable() {
            @Override
            public void run() {
                atualizaHora(txtRelogio);
                atualizador.postDelayed(this, 50);
            }
        });
    }

    private void taskAtualizadorButtonRegistrar() {
        atualizadorButtonRegistrar.post(new Runnable() {
            @Override
            public void run() {
                if (null != GPSTracker.getUltimaLocalizacao()) {
                    buttonRegistar.setEnabled(true);
                    txtStatusLocalizacao.setTextColor(Color.BLUE);
                    txtStatusLocalizacao.setText("ON");

                } else
                    atualizadorButtonRegistrar.postDelayed(this, 3000);
            }
        });
    }


    private void atualizaHora(TextView relogio) {
        Date hora = Calendar.getInstance().getTime();
        relogio.setText(formatHorario.format(hora));
    }


}
