package com.example.martinartime.lightsaberapp;

import android.bluetooth.BluetoothSocket;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.madrapps.pikolo.HSLColorPicker;
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by MartinArtime on 07/09/2017.
 */

public class CambiarColor extends AppCompatActivity {

    @BindView (R.id.circuloCentral) ImageView circuloCentral;

    /**
     * Obtener el input de color elegido por el usuario y enviarselo al Arduino por bluetooth
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_color);

        ButterKnife.bind(this);

        HSLColorPicker colorPicker = (HSLColorPicker) findViewById(R.id.colorPicker);
        colorPicker.setColorSelectionListener(new SimpleColorSelectionListener() {
            @Override
            public void onColorSelected(int color) {
                circuloCentral.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);

                BluetoothSocket socket = Bluetooth.getMsocket();
                if(socket!=null){
                    try {
                        socket.getOutputStream().write(color);
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
