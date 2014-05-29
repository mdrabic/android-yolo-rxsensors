package com.mdrabic.rxsensor;

import android.app.Activity;
import android.os.Bundle;

import javax.inject.Inject;

public class MainActivity extends Activity {

    @Inject
    LightSensorService mProximityService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RxSensorApp.get(this).inject(this);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, AmbientLightFragment.newInstance()).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        mProximityService.register();
    }

    @Override
    public void onPause() {
        super.onPause();
        mProximityService.unregister();
    }
}
