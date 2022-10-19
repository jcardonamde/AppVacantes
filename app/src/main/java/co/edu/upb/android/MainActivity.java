package co.edu.upb.android;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pasarPantalla();
    }

    private void pasarPantalla() {
        CountDownTimer con = new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                Intent in;
                if(cargarPreferencias()) {
                    in = new Intent(MainActivity.this, HomeActivity.class);
                }else {
                    in = new Intent(MainActivity.this, HomeActivity.class);
                }
                startActivity(in);
                finish();
            }
        };
        con.start();
    }


    private boolean cargarPreferencias() {
        SharedPreferences sharedPref = getSharedPreferences("empresa",Context.MODE_PRIVATE);
        boolean logueado = sharedPref.getBoolean("logueado", false);
        return logueado;
    }

}