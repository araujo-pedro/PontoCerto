package br.com.estacio.projeto.pontocerto.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;

import br.com.estacio.projeto.pontocerto.R;
import br.com.estacio.projeto.pontocerto.activity.ConsultaEventosActivity;
import br.com.estacio.projeto.pontocerto.model.Evento;
import br.com.estacio.projeto.pontocerto.repository.EventoRepository;

/**
 * @author Pedro Araujo
 */

public class EventoLinhaAdapter extends BaseAdapter {

    SimpleDateFormat formatData = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat formatHora = new SimpleDateFormat("hh:mm:ss");

    private View viewLinhaLista = null;
    private TextView textViewData;
    private TextView textViewHorarioMarcado;
    private TextView textViewLatitude;
    private TextView textViewLongitude;

    private static LayoutInflater layoutInflater = null;
    private List<Evento> eventos = new ArrayList<Evento>();

    private EventoRepository eventoRepository;
    private ConsultaEventosActivity consultarActivity;

    public EventoLinhaAdapter(ConsultaEventosActivity consultarActivity, List<Evento> eventos) {
        this.eventos = eventos;
        this.consultarActivity = consultarActivity;
        this.layoutInflater = (LayoutInflater) this.consultarActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.eventoRepository = new EventoRepository(consultarActivity);
    }

    @Override
    public int getCount() {
        return eventos.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        initComponentes();

        textViewData.setText(formatData.format(eventos.get(position).getDataHorarioEvento()));
        textViewHorarioMarcado.setText(formatHora.format(eventos.get(position).getDataHorarioEvento().getTime()));
        textViewLatitude.setText(eventos.get(position).getLatitude().toString());
        textViewLongitude.setText(eventos.get(position).getLongitude().toString());

        return viewLinhaLista;
    }

    private void initComponentes(){
        viewLinhaLista = layoutInflater.inflate(R.layout.linha_consultar, null);
        textViewData = (TextView) viewLinhaLista.findViewById(R.id.textViewData);
        textViewHorarioMarcado = (TextView) viewLinhaLista.findViewById(R.id.textViewHorarioMarcado);
        textViewLatitude = (TextView) viewLinhaLista.findViewById(R.id.textViewLatitude);
        textViewLongitude = (TextView) viewLinhaLista.findViewById(R.id.textViewLongitude);
    }


}
