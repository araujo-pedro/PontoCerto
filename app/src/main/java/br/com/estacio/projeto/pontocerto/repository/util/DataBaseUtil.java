package br.com.estacio.projeto.pontocerto.repository.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseUtil extends SQLiteOpenHelper {

    // Nome do banco de dados
    private static final String NOME_BASE_DE_DADOS = "SISTEMA.db";

    // Versao do banco de dados
    private static final int VERSAO_BASE_DE_DADOS = 1;


    public DataBaseUtil(Context context) {
        super(context, NOME_BASE_DE_DADOS, null, VERSAO_BASE_DE_DADOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder stringBuilderCreateTable = new StringBuilder();

        stringBuilderCreateTable.append(" CREATE TABLE tb_evento (");
        stringBuilderCreateTable.append("        id_evento      INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuilderCreateTable.append("        data_horario   DATETIME      NOT NULL,            ");
        stringBuilderCreateTable.append("        latitude       TEXT    NOT NULL,                  ");
        stringBuilderCreateTable.append("        longitude      TEXT    NOT NULL,                  ");
        stringBuilderCreateTable.append("        id_usuario     TEXT    NOT NULL)                  ");

        db.execSQL(stringBuilderCreateTable.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS tb_pessoa");
        onCreate(db);
    }

    /* METODO QUE VAI EXECUTAR AS ROTINAS NO BANCO DE DADOS*/
    public SQLiteDatabase GetConexaoDataBase() {
        return this.getWritableDatabase();
    }
}
