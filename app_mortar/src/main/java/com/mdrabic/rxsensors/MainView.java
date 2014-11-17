package com.mdrabic.rxsensors;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import javax.inject.Inject;

import mortar.Mortar;

public class MainView extends FrameLayout {

    @Inject Main.Presenter presenter;
    private TextView view;

    public MainView(Context context) {
        super(context);
        Mortar.inject(context, this);
        LayoutInflater.from(context).inflate(R.layout.view_sensor_value, this);
        presenter.takeView(this);
        view = (TextView) findViewById(R.id.light_sensor_value);
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Mortar.inject(context, this);
        LayoutInflater.from(context).inflate(R.layout.view_sensor_value, this);
        presenter.takeView(this);
        view = (TextView) findViewById(R.id.light_sensor_value);
    }

    public void showSensorValue(String value) {
        view.setText(value);
    }
}
