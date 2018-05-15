package br.com.estacio.projeto.pontocerto.repository;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.estacio.projeto.pontocerto.model.Evento;
import br.com.estacio.projeto.pontocerto.repository.util.DataBaseUtil;

/**
 * @author Pedro Araujo
 */
public class EventoRepository {

    private DataBaseUtil dataBaseUtil;
    private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    private final SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");

    public EventoRepository(Context context) {
        this.dataBaseUtil = new DataBaseUtil(context);
    }

    /**
     * SALVA UM NOVO EVENTO
     *
     * @param evento
     */
    @SuppressLint("LongLogTag")
    public void savar(Evento evento) {
        ContentValues contentValues = new ContentValues();


        contentValues.put("data_horario", format.format(evento.getDataHorarioEvento()));
        contentValues.put("data", formatData.format(evento.getDataEvento()));
        contentValues.put("tipo_evento", evento.getTipoEvento());
        contentValues.put("latitude", evento.getLatitude());
        contentValues.put("longitude", evento.getLongitude());
        contentValues.put("id_usuario", evento.getIdUsuario());

        Long id_recuperado = this.dataBaseUtil.GetConexaoDataBase().insert("tb_evento", null, contentValues);

        Log.d("EVENTO_REPOSITORY_SALVAR", "SALVOU");
        Log.d("ID_RECUPERADO :: ", id_recuperado.toString());
    }

    public List<Evento> eventos() {
        List<Evento> eventos = new ArrayList<Evento>();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT id_evento,    ");
        sql.append("        data,         ");
        sql.append("        tipo_evento,  ");
        sql.append("        data_horario, ");
        sql.append("        latitude,     ");
        sql.append("        longitude,    ");
        sql.append("        id_usuario    ");
        sql.append("FROM tb_evento        ");
        sql.append(" ORDER BY data_horario");

        Cursor cursor = dataBaseUtil.GetConexaoDataBase().rawQuery(sql.toString(), null);

        cursor.moveToFirst();

        Evento evento;

        while (!cursor.isAfterLast()) {
            evento = new Evento();

            evento.setId(cursor.getLong(cursor.getColumnIndex("id_evento")));

            try {
                evento.setDataHorarioEvento(format.parse(cursor.getString(cursor.getColumnIndex("data_horario"))));
                evento.setDataEvento(formatData.parse(cursor.getString(cursor.getColumnIndex("data"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            evento.setTipoEvento(cursor.getInt(cursor.getColumnIndex("tipo_evento")));
            evento.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
            evento.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
            evento.setIdUsuario(cursor.getLong(cursor.getColumnIndex("id_usuario")));

            eventos.add(evento);

            cursor.moveToNext();
        }

        return eventos;
    }

    public List<Evento> eventosPorData(Date dataEvento) {

        List<Evento> eventos = new ArrayList<Evento>();

        Cursor cursor = dataBaseUtil.GetConexaoDataBase().rawQuery(" SELECT * FROM tb_evento WHERE data = '" + formatData.format(dataEvento) + "' ORDER BY data_horario", null);
        cursor.moveToFirst();

        Evento evento;

        while (!cursor.isAfterLast()) {
            evento = new Evento();

            evento.setId(cursor.getLong(cursor.getColumnIndex("id_evento")));

            try {
                evento.setDataHorarioEvento(format.parse(cursor.getString(cursor.getColumnIndex("data_horario"))));
                evento.setDataEvento(formatData.parse(cursor.getString(cursor.getColumnIndex("data"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            evento.setTipoEvento(cursor.getInt(cursor.getColumnIndex("tipo_evento")));
            evento.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
            evento.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
            evento.setIdUsuario(cursor.getLong(cursor.getColumnIndex("id_usuario")));

            eventos.add(evento);

            cursor.moveToNext();
        }

        return eventos;
    }

    public List<Date> dataEventos() {

        List<Date> dataEventos = new ArrayList<>();

        Cursor cursor = dataBaseUtil.GetConexaoDataBase().rawQuery(" SELECT data FROM tb_evento GROUP BY data ORDER BY data", null);
        cursor.moveToFirst();

        Date dataEvento;

        while (!cursor.isAfterLast()) {
            dataEvento = new Date();

            try {
                dataEvento = formatData.parse(cursor.getString(cursor.getColumnIndex("data")));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            dataEventos.add(dataEvento);

            cursor.moveToNext();
        }

        return dataEventos;
    }


}
