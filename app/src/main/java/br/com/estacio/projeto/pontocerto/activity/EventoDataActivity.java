package br.com.estacio.projeto.pontocerto.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.estacio.projeto.pontocerto.R;
import br.com.estacio.projeto.pontocerto.activity.adapter.EventoLinhaAdapter;
import br.com.estacio.projeto.pontocerto.model.Evento;
import br.com.estacio.projeto.pontocerto.repository.EventoRepository;

public class EventoDataActivity extends AppCompatActivity {

    private ListView listViewEventoData;
    private String paramDataEventoSelecionado;

    private final SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_data);

        this.listViewEventoData = this.findViewById(R.id.listViewEventoData);

        carregarEventosDaData(new Date());

        this.paramDataEventoSelecionado = getIntent().getStringExtra("data_evento_selecionado");

        try {
            this.carregarEventosDaData(formatData.parse(paramDataEventoSelecionado));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void carregarEventosDaData(Date dataEvento) {

        EventoRepository eventoRepository = new EventoRepository(this);

        List<Evento> eventos = eventoRepository.eventosPorData(dataEvento);

        this.listViewEventoData.setAdapter(new EventoLinhaAdapter(this, eventos));
    }
}
