package br.com.estacio.projeto.pontocerto.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.estacio.projeto.pontocerto.MainActivity;
import br.com.estacio.projeto.pontocerto.R;
import br.com.estacio.projeto.pontocerto.gps.GPSTracker;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new GPSTracker().goLocalizacao(this, this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarMainActivity();
            }
        }, 2000);
    }

    private void mostrarMainActivity() {
        Intent intent = new Intent(
                SplashActivity.this,MainActivity.class
        );
        startActivity(intent);
        finish();
    }
}
