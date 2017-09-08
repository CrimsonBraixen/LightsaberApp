package com.example.martinartime.lightsaberapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.SeekBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MartinArtime on 07/09/2017.
 */

public class AjustarLuz extends AppCompatActivity {

    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustar_luz);
    }

    @Override
    protected void onStart() {
        super.onStart();

        seekBar = (SeekBar) findViewById(R.id.brillo);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.screenBrightness = progress/10;
                getWindow().setAttributes(lp);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}
