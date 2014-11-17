package com.mdrabic.rxsensors;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    includes = {
        ServiceModule.class
    },
    library = true
)
public class AppModule {

    private final RxSensorsApp context;

    AppModule(RxSensorsApp app) {
        this.context = app;
    }

    @Provides @Singleton
    Context provideApplication() {
        return context;
    }
}
