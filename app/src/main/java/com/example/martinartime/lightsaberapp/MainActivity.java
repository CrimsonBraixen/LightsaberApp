package com.example.martinartime.lightsaberapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView (R.id.colores) ImageView colores;
    @BindView (R.id.luz) ImageView luz;
    @BindView (R.id.vibrar) ImageView vibrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.cambiar_color)
    public void cambiarColor(){
        Intent intent = new Intent(getApplicationContext(), CambiarColor.class);

        YoYo.with(Techniques.Shake)
                .duration(700)
                .repeat(5)
                .playOn(findViewById(R.id.colores));

        startActivity(intent);
    }

    @OnClick(R.id.ajustar_luz)
    public void ajustarLuz(){
        Intent intent = new Intent(getApplicationContext(), AjustarLuz.class);
        startActivity(intent);
    }

    @OnClick(R.id.hacer_vibrar)
    public void hacerVibrar(){
        Intent intent = new Intent(getApplicationContext(), HacerVibrar.class);
        startActivity(intent);
    }
}
