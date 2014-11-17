package com.mdrabic.rxsensors;

import android.app.Application;

import dagger.ObjectGraph;
import mortar.Mortar;
import mortar.MortarScope;

public class RxSensorsApp extends Application {

    private MortarScope rootGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        rootGraph = Mortar.createRootScope(BuildConfig.DEBUG,
                ObjectGraph.create(new AppModule(this)));
    }

    @Override public Object getSystemService(String name) {
        if (Mortar.isScopeSystemService(name)) {
            return rootGraph;
        }
        return super.getSystemService(name);
    }
}
