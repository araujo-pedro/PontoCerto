package br.com.estacio.projeto.pontocerto.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import br.com.estacio.projeto.pontocerto.R;
import br.com.estacio.projeto.pontocerto.activity.adapter.EventoLinhaAdapter;
import br.com.estacio.projeto.pontocerto.model.Evento;
import br.com.estacio.projeto.pontocerto.repository.EventoRepository;

public class ConsultaEventosActivity extends AppCompatActivity {

    private ListView listViewEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_eventos);

        this.listViewEventos = this.findViewById(R.id.listViewEventos);
        carregarEventosCadastrados();
    }

    private void carregarEventosCadastrados(){
        EventoRepository eventoRepository = new EventoRepository(this);

        List<Evento> eventos = eventoRepository.eventos();

        this.listViewEventos.setAdapter(new EventoLinhaAdapter(this, eventos));
    }
}
