package com.flobberworm.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.flobberworm.demo.digital.DigitalActivity;
import com.flobberworm.demo.parallax.ParallaxActivity;
import com.flobberworm.demo.suspension.SimpleListActivity;
import com.flobberworm.demo.suspension.recycle.SimpleRecycleActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvPull;
    private TextView tvRecycle;
    private TextView tvParallax;
    private TextView tvDigital;
    private TextView tvParabola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPull = (TextView) findViewById(R.id.tv_pull);
        tvRecycle = (TextView) findViewById(R.id.tv_recycle);
        tvParallax = (TextView) findViewById(R.id.tv_parallax);
        tvDigital = (TextView) findViewById(R.id.tv_digital);
        tvParabola = (TextView) findViewById(R.id.tv_parabola);

        tvPull.setOnClickListener(this);
        tvRecycle.setOnClickListener(this);
        tvParallax.setOnClickListener(this);
        tvDigital.setOnClickListener(this);
        tvParabola.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pull:
                startActivity(new Intent(this, SimpleListActivity.class));
                break;
            case R.id.tv_recycle:
                startActivity(new Intent(this, SimpleRecycleActivity.class));
                break;
            case R.id.tv_parallax:
                startActivity(new Intent(this, ParallaxActivity.class));
                break;
            case R.id.tv_digital:
                startActivity(new Intent(this, DigitalActivity.class));
                break;
            case R.id.tv_parabola:
                startActivity(new Intent(this, ParallaxActivity.class));
                break;
        }
    }
}
