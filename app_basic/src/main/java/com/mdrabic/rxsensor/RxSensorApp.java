package com.mdrabic.rxsensor;

import android.app.Application;
import android.content.Context;

import dagger.ObjectGraph;

public class RxSensorApp extends Application {

    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        createObjectGraphAndInject();
    }

    private void createObjectGraphAndInject() {
        mObjectGraph = ObjectGraph.create(new RxSensorModule(this));
    }

    public void inject(Object o) {
        mObjectGraph.inject(o);
    }

    public static RxSensorApp get(Context context) {
        return (RxSensorApp) context.getApplicationContext();
    }
}
