package br.com.estacio.projeto.pontocerto.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.provider.Settings;

/**
 * @author Pedro Araujo
 */
public final class ValidaGpsAndIntenet {

    public static  boolean netWorkdisponibilidade(Context context) {
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

    public static boolean gpsDisponibilidade(Context context) {
        String provider = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (null == provider || provider.equals(0) || "".equals(provider))
            return false;
        else
            return true;
    }
}
