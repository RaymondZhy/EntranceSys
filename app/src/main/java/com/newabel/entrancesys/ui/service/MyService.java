package com.newabel.entrancesys.ui.service;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.IBinder;
import android.os.Vibrator;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.ui.BlueTooth.BlueToothReceiver;
import com.newabel.entrancesys.ui.utils.LogUtil;

public class MyService extends Service implements SensorEventListener {
    private String TAG = this.getClass().getSimpleName();
    private SensorManager mSensorManager;
    private Vibrator mVibrator;
    private SoundPool mSoundPool;
    private int mSoundID;
    private int mStreamID;
    private long lastTimeMillis;
    private ScreenBroadcastReceiver mScreenBroadcastReceiver;
    private BlueToothReceiver mBlueToothReceiver;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerBroadcastReceiver();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        LogUtil.e(TAG, String.valueOf(mSensorManager == null));
        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        mSoundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        mBlueToothReceiver = new BlueToothReceiver(this);


        initShake();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        stopShake();
        unRegisterBroadcastReceiver();
        super.onDestroy();
    }


    private void registerBroadcastReceiver() {
        mScreenBroadcastReceiver = new ScreenBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        this.registerReceiver(mScreenBroadcastReceiver, filter);
    }

    private void unRegisterBroadcastReceiver() {
        if (mScreenBroadcastReceiver != null) {
            this.unregisterReceiver(mScreenBroadcastReceiver);
        }
    }


    class ScreenBroadcastReceiver extends BroadcastReceiver {

//        public ScreenBroadcastReceiver(Context context) {
//            super(context);
//        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                boolean keyState = isInKeyguardRestrictedInputMode(MyService.this);
                LogUtil.e(TAG, action + "<--->" + keyState);
                switch (action) {
                    case Intent.ACTION_SCREEN_ON:
                        if (!keyState) {
                            initShake();
                        }
                        break;
                    case Intent.ACTION_SCREEN_OFF:
                        stopShake();
                        break;
                    case Intent.ACTION_USER_PRESENT:
                        initShake();
                        break;
                }
            }
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        try {
            float[] values = event.values;
            LogUtil.e(TAG, values[0] + " " + values[1] + " " + values[2]);
            if (Math.abs(values[0]) > 19 || Math.abs(values[1]) > 19 || Math.abs(values[2]) > 19) { //华为手机25为最佳，小米19
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - lastTimeMillis > 2000) {
                    shaking();
                    lastTimeMillis = currentTimeMillis;
                    mBlueToothReceiver.sendData(mBlueToothReceiver.getBondedDevices().get(0), "恭喜你，摇到一个美女！");
                }
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private boolean isInKeyguardRestrictedInputMode(Context context) {
        KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        boolean flag = mKeyguardManager.inKeyguardRestrictedInputMode();
        return flag;
    }

    private void initShake() {
        if (mSensorManager != null) {
            mSensorManager.registerListener(MyService.this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSoundPool != null) {
            mSoundID = mSoundPool.load(MyService.this, R.raw.sound_shake, 1);
        }
    }

    private void shaking() {
        mStreamID = mSoundPool.play(mSoundID, 1, 1, 0, 0, 1);
        mVibrator.vibrate(1000);
    }

    private void stopShake() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(MyService.this);
        }
        if (mSoundPool != null) {
            mSoundPool.pause(mStreamID);
            mSoundPool.unload(mSoundID);
        }
    }
}
