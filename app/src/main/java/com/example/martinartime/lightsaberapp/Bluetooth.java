package com.example.martinartime.lightsaberapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * Created by MartinArtime on 12/09/2017.
 */

public class Bluetooth extends AsyncTask<Void, Void, Void> {

    private BluetoothAdapter mBluetoothAdapter;

    private Set equiposApareados;
    private ArrayList<BluetoothDevice> devices;
    private BluetoothDevice conectado;
    private Context context;
    private ProgressDialog progress;

    private static BluetoothSocket msocket = null;
    private boolean misConected = false;
    private static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private boolean ConnectSuccess = true;

    /**
     * Constructor publico
     * @param context
     */
    public Bluetooth(Context context){
        mBluetoothAdapter = null;
        equiposApareados = null;
        this.context = context;
    }

    /**
     * Metodo previo a la conexion
     */
    @Override
    protected void onPreExecute() {
        progress = ProgressDialog.show(context, "Conectando...", "Por favor espere!");
    }

    /**
     * Contectandose...
     * @param devices
     * @return void
     */
    @Override
    protected Void doInBackground(Void... devices) {
        try {
            if (msocket == null || !misConected) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                BluetoothDevice dispositivo = mBluetoothAdapter.getRemoteDevice(conectado.getAddress());
                msocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                msocket.connect();
            }
        }
        catch (IOException e) {
            ConnectSuccess = false;
        }
        return null;
    }

    /**
     * Metodo posterior a la conexion
     * @param result
     */
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        if (!ConnectSuccess){
            Toast.makeText(context, "Conexion fallida, intente nuevamente", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Conectado", Toast.LENGTH_SHORT).show();
            misConected = true;
        }
        progress.dismiss();
    }

    /**
     * Establecer la conexion bluetooth
     * @param activity
     * @return lista de dispositivos conectados
     */
    public ArrayList<BluetoothDevice> establecerConexionBluetooth(Activity activity){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null) {
            Toast.makeText(activity.getApplicationContext(), "Equipo Bluetooth no disponible", Toast.LENGTH_LONG).show();
            //activity.finish();
        }
        else {
            if(mBluetoothAdapter.isEnabled()) {
                devices = conseguirEquiposApareados(activity);
                conectado = devices.get(0);
                return devices;
            }
            else {
                Toast.makeText(activity.getApplicationContext(), "Equipo Bluetooth no activado", Toast.LENGTH_LONG).show();
            }
        }
        return null;
    }

    /**
     * Obtener equipos apareados
     * @param activity
     * @return lista de dispositivos conectados
     */
    private ArrayList<BluetoothDevice> conseguirEquiposApareados(Activity activity) {
        equiposApareados = mBluetoothAdapter.getBondedDevices();
        ArrayList<BluetoothDevice> lista = new ArrayList<>();
        Iterator<BluetoothDevice> i = equiposApareados.iterator();

        if (equiposApareados.size()>0) {
            while(i.hasNext()) {
                lista.add(i.next());
            }
        }
        else {
            Toast.makeText(activity.getApplicationContext(), "No se encontraron equipos apareados", Toast.LENGTH_LONG).show();
        }
        return lista;
    }

    /**
     * get para que el socket sea accesible en toda la app
     * @return socket activo
     */
    public static synchronized BluetoothSocket getMsocket() {
        return msocket;
    }
}
