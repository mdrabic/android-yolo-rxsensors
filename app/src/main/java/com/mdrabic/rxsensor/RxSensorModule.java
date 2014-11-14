package com.mdrabic.rxsensor;

import android.app.Application;

import com.mdrabic.sensorservice.LightSensorService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                RxSensorApp.class,
                MainActivity.class,
                AmbientLightFragment.class
        }
)
public class RxSensorModule {

    private final RxSensorApp mApp;

    public RxSensorModule(RxSensorApp app) {
        mApp = app;
    }

    @Provides @Singleton Application provideApplication() {
        return mApp;
    }

    @Provides @Singleton
    LightSensorService provideLightSensorService(Application app) {
        return new LightSensorService(app);
    }
}
