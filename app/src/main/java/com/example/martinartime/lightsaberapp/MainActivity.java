package com.example.martinartime.lightsaberapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.martinartime.lightsaberapp.bluetooth.ConectarBluetoothActivity;
import com.example.martinartime.lightsaberapp.sensores.SensorActivity;

import butterknife.BindView;
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

        //bluetooth = new Bluetooth(getApplicationContext());

        /*ArrayList<BluetoothDevice> equipos = bluetooth.establecerConexionBluetooth(this);

        if(equipos!=null){
            Dialog dialog = crearDialogo(equipos.get(0).getName());
            dialog.show();
        }
        else{
            Toast.makeText(getApplicationContext(), "No se encontraron Equipos bluetooth", Toast.LENGTH_LONG).show();
        }*/
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
     * Iniciar actividad VerDatos
     *//*
    @OnClick(R.id.cardDatos)
    public void verDatos(){
        Intent intent = new Intent(getApplicationContext(), SensorActivity.class);
        startActivity(intent);
    }
*/
    /**
     * Iniciar actividad ManejarBluetooth
     */
    @OnClick(R.id.cardBluetooth)
    public void manejarBluetooth(){
        Intent intent = new Intent(getApplicationContext(), ConectarBluetoothActivity.class);
        startActivity(intent);
    }
}
