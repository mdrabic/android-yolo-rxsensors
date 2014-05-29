package com.mdrabic.rxsensor;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class AmbientLightFragment extends Fragment {

    @Inject LightSensorService mSensorService;
    @InjectView(R.id.light_sensor_value) TextView mLightValue;
    private Subscription mSubscription;
    private static final String TAG = AmbientLightFragment.class.getSimpleName();

    public static AmbientLightFragment newInstance() {
        return new AmbientLightFragment();
    }

    public AmbientLightFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proximity, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        RxSensorApp.get(getActivity()).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSubscription = mSensorService.subscribeToSensor()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<LightSensorService.ProximityEventWrapper>() {
                    @Override
                    public void call(LightSensorService.ProximityEventWrapper sensorEvent) {
                        if (sensorEvent.event != null)
                            mLightValue.setText(String.valueOf(sensorEvent.event.values[0]));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i(TAG, throwable.getMessage());
                    }
                });

    }

    @Override
    public void onPause() {
        super.onPause();
        mSubscription.unsubscribe();
    }
}
