package com.example.martinartime.lightsaberapp.sensores;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

/**
 * Created by pablo on 28/9/2017.
 * Clase que implementa los metodos necesarios para registrar, desregistar y escuchar los sensores
 */

public class SensorActivity extends Activity implements SensorEventListener {

    //CONSTANTES
    private static final int SENSIBILIDAD_PROXIMIDAD = 4;
    private static int UMBRAL_MOVIMIENTO=2500;
    private static float UMBRAL_LUZ=230;
    //SENSOR
    private final SensorManager sensorManager;
    private final Sensor aceletrometro;
    private final Sensor proximidad;
    private final Sensor luminico;
    //ACELEROMETRO
    private long ultimaActualizacion = 0;
    private float xAnterior;
    private float yAnterior;
    private float zAnterior;




    public SensorActivity() {
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        aceletrometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proximidad= sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        luminico = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
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
        Sensor sensorQueCambio  = event.sensor;

        if (sensorQueCambio.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long tiempoActual = System.currentTimeMillis();
            if ((tiempoActual - ultimaActualizacion) > 100) {

                ultimaActualizacion = tiempoActual;

                double aceleracion = calcularAceleracion(x, y, z);

                if (aceleracion > UMBRAL_MOVIMIENTO) {
                    //enviar por bluTú al arduino que se movio
                    Toast.makeText(getApplicationContext(), "Hubo aceleración", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Neutro", Toast.LENGTH_SHORT).show();
                }

                xAnterior = x;
                yAnterior = y;
                zAnterior = z;
            }
        }else if(sensorQueCambio.getType() == Sensor.TYPE_PROXIMITY){
            float valor= event.values[0];
            if ( valor>= -SENSIBILIDAD_PROXIMIDAD && valor <= SENSIBILIDAD_PROXIMIDAD) {
                //enviar por bluTú al arduino que se prenda el sable
                Toast.makeText(getApplicationContext(), "cerca", Toast.LENGTH_SHORT).show();
            } else {
                //enviar por bluTú al arduino que se apague el sable
                Toast.makeText(getApplicationContext(), "lejos", Toast.LENGTH_SHORT).show();
            }
        }else if(sensorQueCambio.getType()== Sensor.TYPE_LIGHT){
            float valor= event.values[0];
            if ( valor>= UMBRAL_LUZ) {
                //enviar por bluTú al arduino que filtre.
                Toast.makeText(getApplicationContext(), "luz fuerte", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "luz débil", Toast.LENGTH_SHORT).show();
            }
        }


    }

    /**
     * calcula la aceleración a partir de los valores x, y, z actuales y los anteriores.
     * @param x
     * @param y
     * @param z
     * @return
     */
    private double calcularAceleracion(float x, float y, float z) {
        double normal=Math.sqrt(Math.pow(x,2)+Math.pow(y,2)+ Math.pow(z,2));
        double normalAnterior=Math.sqrt(Math.pow(xAnterior,2)+Math.pow(yAnterior,2)+Math.pow(zAnterior,2));
        return Math.abs(normal-normalAnterior);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
