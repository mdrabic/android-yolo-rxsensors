package com.mdrabic.rxsensors;

import android.content.Context;

import com.mdrabic.sensorservice.LightSensorService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        library = true,
        complete = false
)
public class ServiceModule {

    @Provides @Singleton
    LightSensorService provideLightSensorService(Context context) {
        return new LightSensorService(context);
    }
}
