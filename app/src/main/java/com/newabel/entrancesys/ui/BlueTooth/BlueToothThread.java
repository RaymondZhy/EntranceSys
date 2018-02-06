package com.newabel.entrancesys.ui.BlueTooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;

import com.newabel.entrancesys.ui.utils.LogUtil;

import java.io.InputStream;
import java.util.UUID;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2018/2/5 0005.
 */

public class BlueToothThread extends Thread {

    private final String TAG = this.getClass().getSimpleName();
    private BluetoothServerSocket mBluetoothServerSocket;
    private BluetoothSocket mBluetoothSocket;
    private InputStream mInputStream;
    private byte[] mBuffer;
    private Handler mHandler;
    private boolean isRunning =true;

    public BlueToothThread() {
//        BluetoothServerSocket s = BluetoothAdapter.getDefaultAdapter().listenUsingInsecureRfcommWithServiceRecord();
        try {
            mBluetoothServerSocket = BluetoothAdapter.getDefaultAdapter().listenUsingRfcommWithServiceRecord("纽贝尔测试蓝牙", UUID.fromString("0000111f-0000-1000-8000-00805f9b39fb"));
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            mBluetoothSocket = mBluetoothServerSocket.accept();
            mInputStream = mBluetoothSocket.getInputStream();
            mBuffer = new byte[8192];
            while (isRunning) {
                int count = mInputStream.read(mBuffer);
                String msg = new String(mBuffer, 0, count, "UTF-8");
                LogUtil.e(TAG, new String(mBuffer, 0, count, "UTF-8"));
                sendMessage(msg);
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        } finally {
            try {
                if (mBluetoothServerSocket != null) {
                    mBluetoothSocket.close();
                }
                if (mBluetoothSocket != null) {
                    mBluetoothServerSocket.close();
                }

                if (mInputStream != null) {
                    mInputStream.close();
                }
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
            }
        }

        super.run();
    }

    private void sendMessage(String msg) {
        Message message = Message.obtain(mHandler);
        message.obj = msg;
        message.sendToTarget();
    }

    public void setHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }


    public void stopThread() {
        try {
            isRunning = false;

            if (mBluetoothServerSocket != null) {
                mBluetoothSocket.close();
            }
            if (mBluetoothSocket != null) {
                mBluetoothServerSocket.close();
            }

            if (mInputStream != null) {
                mInputStream.close();
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
    }
}
