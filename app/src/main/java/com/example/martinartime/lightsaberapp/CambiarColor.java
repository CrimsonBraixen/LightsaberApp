package com.example.martinartime.lightsaberapp;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.madrapps.pikolo.HSLColorPicker;
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by MartinArtime on 07/09/2017.
 */

public class CambiarColor extends AppCompatActivity {

    @BindView (R.id.circuloCentral) ImageView circuloCentral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_color);

        ButterKnife.bind(this);

        HSLColorPicker colorPicker = (HSLColorPicker) findViewById(R.id.colorPicker);
        colorPicker.setColorSelectionListener(new SimpleColorSelectionListener() {
            @Override
            public void onColorSelected(int color) {
                // Do whatever you want with the color
                circuloCentral.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
            }
        });
    }
}
