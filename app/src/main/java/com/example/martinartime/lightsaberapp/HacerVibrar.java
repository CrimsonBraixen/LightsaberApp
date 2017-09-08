package com.example.martinartime.lightsaberapp;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MartinArtime on 07/09/2017.
 */

public class HacerVibrar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hacer_vibrar);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.botonVibrar)
    public void hacerVibrar(){
        Vibrator vibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrate.vibrate(500);
    }
}
