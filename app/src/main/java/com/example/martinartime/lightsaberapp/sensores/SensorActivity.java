package com.example.martinartime.lightsaberapp.sensores;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.martinartime.lightsaberapp.Bluetooth;
import com.example.martinartime.lightsaberapp.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pablo on 28/9/2017.
 * Clase que implementa los metodos necesarios para registrar, desregistar y escuchar los sensores
 *
 * Datos enviados:
 *  - Luz debil: 0
 *  - Luz fuerte: 1
 *  - Choque: 2
 *  - Aceleracion: 3
 */

public class SensorActivity extends Activity implements SensorEventListener {

    //CONSTANTES
    private static final int SENSIBILIDAD_PROXIMIDAD = 4;
    private static int UMBRAL_MOVIMIENTO = 2500;
    private static float UMBRAL_LUZ = 230;
    //SENSOR
    private SensorManager sensorManager;
    private Sensor aceletrometro;
    private Sensor proximidad;
    private Sensor luminico;
    //ACELEROMETRO
    private long ultimaActualizacion = 0;
    private float xAnterior;
    private float yAnterior;
    private float zAnterior;
    //DATOS A ENVIAR
    private static Byte DEBIL = 0;
    private static Byte FUERTE = 1;
    private static Byte CHOQUE = 2;
    private static Byte ACELERACION = 3;

    @BindView(R.id.tv_ejeX_value)
    TextView tv_ejeX;
    @BindView(R.id.tv_ejeY_value)
    TextView tv_ejeY;
    @BindView(R.id.tv_ejeZ_value)
    TextView tv_ejeZ;
    @BindView(R.id.tv_aceleracion_value)
    TextView tv_aceleracion;
    @BindView(R.id.tv_luminosidad_value)
    TextView tv_luminosidad;
    @BindView(R.id.tv_proximidad_value)
    TextView tv_proximidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_activity);

        ButterKnife.bind(this);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        aceletrometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proximidad = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        luminico = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    public SensorActivity() {

    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, aceletrometro, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximidad, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, luminico, SensorManager.SENSOR_DELAY_NORMAL);

    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        synchronized (this) {

            BluetoothSocket socket = Bluetooth.getMsocket();

            Sensor sensorQueCambio = event.sensor;

            if (sensorQueCambio.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                long tiempoActual = System.currentTimeMillis();
                if ((tiempoActual - ultimaActualizacion) > 100) {

                    ultimaActualizacion = tiempoActual;

                    double aceleracion = calcularAceleracion(x, y, z);

                    tv_ejeX.setText(String.valueOf(x));
                    tv_ejeY.setText(String.valueOf(y));
                    tv_ejeZ.setText(String.valueOf(z));
                    tv_aceleracion.setText(String.valueOf(aceleracion));

                    if (aceleracion > UMBRAL_MOVIMIENTO) {
                        if (socket != null) {
                            try {
                                socket.getOutputStream().write(ACELERACION);
                            } catch (IOException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        Toast.makeText(getApplicationContext(), "Hubo aceleración", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Neutro", Toast.LENGTH_SHORT).show();
                    }

                    xAnterior = x;
                    yAnterior = y;
                    zAnterior = z;
                }
            } else if (sensorQueCambio.getType() == Sensor.TYPE_PROXIMITY) {
                float valor = event.values[0];

                tv_proximidad.setText(String.valueOf(valor));

                if (valor >= -SENSIBILIDAD_PROXIMIDAD && valor <= SENSIBILIDAD_PROXIMIDAD) {
                    if (socket != null) {
                        try {
                            socket.getOutputStream().write(CHOQUE);
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(getApplicationContext(), "cerca", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "lejos", Toast.LENGTH_SHORT).show();
                }
            } else if (sensorQueCambio.getType() == Sensor.TYPE_LIGHT) {
                float valor = event.values[0];

                tv_luminosidad.setText(String.valueOf(valor));

                if (valor >= UMBRAL_LUZ) {
                    if (socket != null) {
                        try {
                            socket.getOutputStream().write(FUERTE);
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(getApplicationContext(), "luz fuerte", Toast.LENGTH_SHORT).show();
                } else {
                    if (socket != null) {
                        try {
                            socket.getOutputStream().write(DEBIL);
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(getApplicationContext(), "luz débil", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    /**
     * Calcula la aceleración a partir de los valores x, y, z actuales y los anteriores.
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    private double calcularAceleracion(float x, float y, float z) {
        double normal = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
        double normalAnterior = Math.sqrt(Math.pow(xAnterior, 2) + Math.pow(yAnterior, 2) + Math.pow(zAnterior, 2));
        return Math.abs(normal - normalAnterior);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
