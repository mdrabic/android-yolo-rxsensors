package com.mdrabic.rxsensors;

import android.os.Bundle;

import com.mdrabic.sensorservice.LightSensorService;

import javax.inject.Inject;
import javax.inject.Singleton;

import mortar.Blueprint;
import mortar.ViewPresenter;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import com.mdrabic.sensorservice.LightSensorService.ProximityEventWrapper;

public class Main implements Blueprint{
    @Override
    public String getMortarScopeName() {
        return getClass().getName();
    }

    @Override
    public Object getDaggerModule() {
        return new Module();
    }

    @dagger.Module(
        injects = {
            MainActivity.class,
            MainView.class
        },
        addsTo = AppModule.class
    )
    class Module{}

    @Singleton
    static class Presenter extends ViewPresenter<MainView> {
        private LightSensorService lightSensor;
        private Subscription subscription;

        @Inject Presenter(LightSensorService sensor){
            lightSensor = sensor;
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            lightSensor.register();
            subscription = lightSensor.subscribeToSensor()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new HandleEvent(this));
        }

        @Override
        protected void onSave(Bundle outState) {
            super.onSave(outState);
            lightSensor.unregister();
            if (!subscription.isUnsubscribed()) subscription.unsubscribe();
        }

        protected void updateView(String value) {
            getView().showSensorValue(value);
        }
    }

    static class HandleEvent implements Action1<ProximityEventWrapper> {

        Presenter presenter;

        HandleEvent(Presenter presenter) {
            this.presenter = presenter;
        }

        @Override
        public void call(ProximityEventWrapper wrapper) {
            //could handle other parsing of the event here
            if (wrapper.event != null) {
                float f = wrapper.event.values[0];
                String value = String.valueOf(f);
                presenter.updateView(value);
            }
        }
    }
}
