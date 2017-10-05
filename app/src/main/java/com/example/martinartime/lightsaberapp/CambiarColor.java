package com.example.martinartime.lightsaberapp;

import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapProgressBar;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by MartinArtime on 07/09/2017.
 */

public class CambiarColor extends AppCompatActivity {

    @BindView (R.id.barAzul) SeekBar barAzul;
    @BindView (R.id.barRojo) SeekBar barRojo;
    @BindView (R.id.botonColor) BootstrapButton botonColor;


    /**
     * Obtener el input de color elegido por el usuario y enviarselo al Arduino por bluetooth
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_color);

        ButterKnife.bind(this);

        barAzul.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
        barAzul.getThumb().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);

        barRojo.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        barRojo.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

        botonColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rojo = barRojo.getProgress();
                int azul = barAzul.getProgress();
                String color;

                if(rojo==0 && azul==0){
                    Toast.makeText(getApplicationContext(), "Debe setear por lo menos un color", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(rojo>0 && azul==0){
                    botonColor.setBackgroundColor(Color.RED);
                    color = "Rojo";
                }
                else if(rojo==0 && azul>0){
                    botonColor.setBackgroundColor(Color.BLUE);
                    color = "Azul";
                }
                else{
                    botonColor.setBackgroundColor(Color.rgb(255,0,255));
                    color = "Violeta";
                }

                Toast.makeText(getApplicationContext(), color, Toast.LENGTH_SHORT).show();
                BluetoothSocket socket = Bluetooth.getMsocket();
                if(socket!=null){
                    try {
                        socket.getOutputStream().write(color.getBytes());
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}
