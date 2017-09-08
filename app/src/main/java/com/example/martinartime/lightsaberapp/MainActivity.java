package com.example.martinartime.lightsaberapp;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    static final String TAG_MAIN = "MainActivity";

    @BindView (R.id.colores) ImageView colores;
    @BindView (R.id.luz) ImageView luz;
    @BindView (R.id.vibrar) ImageView vibrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.cardColores)
    public void cambiarColor(){
        Intent intent = new Intent(getApplicationContext(), CambiarColor.class);

        YoYo.with(Techniques.Bounce)
                .duration(2000)
                .repeat(1)
                .playOn(findViewById(R.id.colores));

        startActivity(intent);
    }

    @OnClick(R.id.cardLuz)
    public void ajustarLuz(){
        Intent intent = new Intent(getApplicationContext(), AjustarLuz.class);
        startActivity(intent);
    }

    @OnClick(R.id.cardVibrar)
    public void hacerVibrar(){
        Intent intent = new Intent(getApplicationContext(), HacerVibrar.class);
        startActivity(intent);
    }
}
