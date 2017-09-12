package com.example.martinartime.lightsaberapp;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    static final String TAG_MAIN = "MainActivity";

    @BindView (R.id.colores) ImageView colores;
    @BindView (R.id.luz) ImageView luz;
    @BindView (R.id.vibrar) ImageView vibrar;
    Bluetooth bluetooth;

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

        bluetooth = new Bluetooth(getApplicationContext());

        ArrayList<BluetoothDevice> equipos = bluetooth.establecerConexionBluetooth(this);

        if(equipos!=null){
            Dialog dialog = crearDialogo(equipos.get(0).getName());
            dialog.show();
        }
        else{
            Toast.makeText(getApplicationContext(), "No se encontraron Equipos bluetooth", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Crea el mensaje con el dispositivo apareado que llega por parametro
     * @param nombre
     * @return Dialog
     */
    public Dialog crearDialogo(String nombre) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Dispositivos apareados")
                .setMessage(nombre);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {}
        });
        return builder.create();
    }

    /**
     * Iniciar actividad CambiarColor
     */
    @OnClick(R.id.cardColores)
    public void cambiarColor(){
        Intent intent = new Intent(getApplicationContext(), CambiarColor.class);

        YoYo.with(Techniques.Bounce)
                .duration(2000)
                .repeat(1)
                .playOn(findViewById(R.id.colores));

        startActivity(intent);
    }

    /**
     * Iniciar actividad AjustarLuz
     */
    @OnClick(R.id.cardLuz)
    public void ajustarLuz(){
        Intent intent = new Intent(getApplicationContext(), AjustarLuz.class);
        startActivity(intent);
    }

    /**
     * Iniciar actividad HacerVibrar
     */
    @OnClick(R.id.cardVibrar)
    public void hacerVibrar(){
        Intent intent = new Intent(getApplicationContext(), HacerVibrar.class);
        startActivity(intent);
    }
}
