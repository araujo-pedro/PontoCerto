package br.com.estacio.projeto.pontocerto.util;

import android.app.AlertDialog;
import android.content.Context;

import br.com.estacio.projeto.pontocerto.R;

public class AlertUtil {

    public static void Alert(Context context, String mensagem){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        //ADICIONANDO UM TITULO A NOSSA MENSAGEM DE ALERTA
        //alertDialog.setTitle(R.string.app_name);
        alertDialog.setTitle("ALERTA!!");

        //MENSAGEM A SER EXIBIDA
        alertDialog.setMessage(mensagem);

        //CRIA UM BOTÃO COM O TEXTO OK SEM AÇÃO
        alertDialog.setPositiveButton("OK", null);

        //MOSTRA A MENSAGEM NA TELA
        alertDialog.show();

    }
}
