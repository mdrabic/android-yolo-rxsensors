package com.mdrabic.rxsensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import rx.subjects.BehaviorSubject;

public class LightSensorService implements SensorEventListener{

    private Sensor mLightSensor;
    private SensorManager mSensorManager;
    private BehaviorSubject<ProximityEventWrapper> mSubject;
    private static final String TAG = LightSensorService.class.getSimpleName();

    public LightSensorService(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSubject = BehaviorSubject.create(new ProximityEventWrapper(null));
    }

    public void register() {
        mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_UI);
    }

    public void unregister() {
        mSensorManager.unregisterListener(this);
    }

    public BehaviorSubject<ProximityEventWrapper> subscribeToSensor() {
        return mSubject;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        mSubject.onNext(new ProximityEventWrapper(sensorEvent));
        Log.i(TAG, "Light : " + sensorEvent.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //idgaf about this.
    }

    public class ProximityEventWrapper {
        public SensorEvent event;

        public ProximityEventWrapper(SensorEvent event) {
            this.event = event;
        }
    }
}
