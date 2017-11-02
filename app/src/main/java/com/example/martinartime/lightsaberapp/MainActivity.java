package com.example.martinartime.lightsaberapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.martinartime.lightsaberapp.bluetooth.ConectarBluetoothActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    static final String TAG_MAIN = "";

    /**
     * Se hace el binding de las views
     * Se obtienen los equipos bluetooth apareados
     * Muestra la conexion actual
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    /**
     * Iniciar actividad ManejarBluetooth
     */
    @OnClick(R.id.cardBluetooth)
    public void conectarBluetooth(){
        Intent intent = new Intent(getApplicationContext(), ConectarBluetoothActivity.class);
        startActivity(intent);
    }
}
