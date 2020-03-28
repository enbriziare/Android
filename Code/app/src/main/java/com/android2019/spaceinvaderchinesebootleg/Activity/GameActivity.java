package com.android2019.spaceinvaderchinesebootleg.Activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android2019.spaceinvaderchinesebootleg.View.AnimatedView;
import com.android2019.spaceinvaderchinesebootleg.Manager.GameManager;

public class GameActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private static Object lock = new Object();

    private AnimatedView mAnimatedView;
    private GameManager gm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mAnimatedView = new AnimatedView(this, lock);
        gm = new GameManager(mAnimatedView,this, lock);
        mAnimatedView.setGM(gm);

        setContentView(mAnimatedView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) { }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gm.onSensorEvent(event);
        }
    }

    public void upgradeBulletSpeed(View view) {
        gm.upgradeBulletSpeed();
    }

    public void upgradePlayerSpeed(View view) {
        gm.upgradePlayerSpeed();
    }

    public void upgradeBulletNb(View view) {
        gm.upgradeBulletNb();
    }
}
