package com.newabel.entrancesys.ui.BlueTooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.ParcelUuid;
import android.support.annotation.RequiresApi;

import com.newabel.entrancesys.ui.utils.LogUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2018/2/2 0002.
 */

public class BlueToothReceiver extends BroadcastReceiver {
    private static BluetoothSocket mBluetoothSocket;
    private String TAG = this.getClass().getSimpleName();
    private BluetoothAdapter mBluetoothAdapter;
    private Context mContext;
    private List<BluetoothDevice> mList;
    private BluetoothWatcher mBluetoothWatcher;
    private final double pRssi = 59;  //发射端和接收端相隔1米时的信号强 //50
    private final double dFactor = 2.0; //环境衰减因子 //2.5
    private static BluetoothGatt mBluetoothGatt;

    public BlueToothReceiver(Context context) {
        this.mContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            switch (action) {
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    short rssi = intent.getExtras().getShort(BluetoothDevice.EXTRA_RSSI);
                    int BOND_STATE = intent.getExtras().getInt(BluetoothDevice.EXTRA_BOND_STATE);
                    LogUtil.e(TAG, device.getName() + ";" + device.getType() + ";" + device.getUuids() + ";" + device.getAddress() + ";" + device.getBondState() + "--->" + rssi);
                    if (mBluetoothWatcher != null) {
                        mBluetoothWatcher.actionFound(device, rssi);
                    }
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    if (mBluetoothWatcher != null) {
                        mBluetoothWatcher.actionFinished();
                    }
                    break;
                case BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED:

                    break;
                case BluetoothDevice.ACTION_BOND_STATE_CHANGED:
//                    if(BOND_STATE == BluetoothDevice.BOND_BONDED){
//
//                    }
                    break;
            }
        }
    }

    /**
     * 开始扫描设备
     */
    public void startDiscovery() {
        LogUtil.e(TAG, isBluetoothEnable() + " " + hasBluetooth() + " " + "开始扫描");
        if (isBluetoothEnable() && hasBluetooth()) {
            mBluetoothAdapter.startDiscovery();
        }
    }

    /**
     * 取消扫描
     */
    public void cancelDiscovery() {
        if (hasBluetooth()) {
            mBluetoothAdapter.cancelDiscovery();
        }
    }


    /**
     * 蓝牙是否打开
     *
     * @return
     */
    public boolean isBluetoothEnable() {
        return mBluetoothAdapter.enable();
    }

    /**
     * 是否有蓝牙设备
     *
     * @return
     */
    public boolean hasBluetooth() {
        return mBluetoothAdapter != null;
    }

    /**
     * 获取已配对的设备
     *
     * @return
     */
    List<BluetoothDevice> getBondedDevices() {
        Set<BluetoothDevice> set = mBluetoothAdapter.getBondedDevices();
        return set == null ? new ArrayList<BluetoothDevice>() : new ArrayList<>(set);
    }

    /**
     * 蓝牙测距
     *
     * @param rssi 信号强度
     * @return
     */
    public double getDistance(int rssi) {
        rssi = Math.abs(rssi);
        double power = (rssi - pRssi) / (10 * dFactor);
        return Math.pow(10, power);
    }

    /**
     * 注册广播
     */
    public void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        mContext.registerReceiver(this, filter);
    }

    /**
     * 反注册广播
     */
    public void unRegisterReceiver() {
        mContext.unregisterReceiver(this);
    }

    /**
     * 蓝牙观察者
     *
     * @param watcher
     */
    public void setBluetoothWatcher(BluetoothWatcher watcher) {
        this.mBluetoothWatcher = watcher;
    }

    public interface BluetoothWatcher {
        void actionFound(BluetoothDevice device, int rssi);

        void actionFinished();
    }


    /**
     * 蓝牙配对
     *
     * @param device
     * @return
     */
    public boolean createBound(BluetoothDevice device) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                return device.createBond();
            } else {
                Method createBond = BluetoothDevice.class.getMethod("createBond");
                createBond.setAccessible(true);
                return (boolean) createBond.invoke(device);
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return false;
    }

    /**
     * 取消蓝牙配对
     *
     * @param device
     * @return
     */
    public boolean removeBound(BluetoothDevice device) {
        try {
            Method removeBond = BluetoothDevice.class.getMethod("removeBond");
            removeBond.setAccessible(true);
            return (boolean) removeBond.invoke(device);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return false;
    }


    /**
     * 重新链接蓝牙
     *
     * @param device
     * @return
     */
    public boolean reConnect(BluetoothDevice device) {
        if (hasBluetooth()) {
            if (isConnect(device)) {
                mBluetoothGatt.disconnect();
                mBluetoothGatt.close();
            }
            mBluetoothGatt = device.connectGatt(mContext, false, new MyBluetoothGattCallback());
        }
        return isConnect(device);
    }

    /**
     * 建立蓝牙链接;
     *
     * @param device
     * @return
     */
    public boolean connect(BluetoothDevice device) {
        if (hasBluetooth()) {
            if (!isConnect(device)) {
                mBluetoothGatt = device.connectGatt(mContext, false, new MyBluetoothGattCallback());
            }
        }
        for (ParcelUuid uuid : mBluetoothGatt.getDevice().getUuids()) {
            LogUtil.e(TAG, "UUID" + uuid.toString());
        }
        return isConnect(device);
    }

    /**
     * 蓝牙是否链接
     *
     * @param device
     * @return
     */
    public boolean isConnect(BluetoothDevice device) {
        if (mBluetoothGatt != null) {
            return true;
        }
        return false;
    }

    public void send(String msg) {
        try {

            String[] uuids1 = new String[]{//honor
                    "D00001103-0000-1000-8000-00805f9b34fb",
                    "0000110a-0000-1000-8000-00805f9b34fb",
                    "00001105-0000-1000-8000-00805f9b34fb",
                    "00001115-0000-1000-8000-00805f9b34fb",
                    "00001116-0000-1000-8000-00805f9b34fb",
                    "0000112d-0000-1000-8000-00805f9b34fb",
                    "0000110e-0000-1000-8000-00805f9b34fb",
                    "0000112f-0000-1000-8000-00805f9b34fb",
                    "00001112-0000-1000-8000-00805f9b34fb",
                    "0000111f-0000-1000-8000-00805f9b34fb",
                    "00001132-0000-1000-8000-00805f9b34fb",
                    "00000000-0000-1000-8000-00805f9b34fb",
                    "00000000-0000-1000-8000-00805f9b34fb"};
            String[] uuids2 = new String[]{//mi
                    "0000110a-0000-1000-8000-00805f9b34fb",
                    "00001105-0000-1000-8000-00805f9b34fb",
                    "00001115-0000-1000-8000-00805f9b34fb",
                    "00001116-0000-1000-8000-00805f9b34fb",
                    "0000112f-0000-1000-8000-00805f9b34fb",
                    "00001112-0000-1000-8000-00805f9b34fb",
                    "0000111f-0000-1000-8000-00805f9b34fb",
                    "00001132-0000-1000-8000-00805f9b34fb",
                    "00001132-0000-1000-8000-00805f9b34fb"};

//            for (ParcelUuid uuid : mBluetoothGatt.getDevice().getUuids()) {

            String[] uuids = {"0000111f-0000-1000-8000-00805f9b39fb"};
            LogUtil.e(TAG, "UUID--->开始");
            for (String uuid : uuids) {
                BluetoothGattService service = mBluetoothGatt.getService(UUID.fromString(uuid));
                LogUtil.e(TAG, String.valueOf(service));
                if (service == null) {
                    continue;
                }
                BluetoothGattCharacteristic c = service.getCharacteristic(UUID.fromString(uuid));
                LogUtil.e(TAG, "UUID--->" + uuid);
                c.setValue(msg);
                mBluetoothGatt.writeCharacteristic(c);
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
    }

    /**
     * 创建蓝牙连接
     *
     * @param device
     * @return
     * @throws IOException
     */
    public void sendData(BluetoothDevice device,String msg) throws IOException {
        try {
            if (mBluetoothSocket == null || !mBluetoothSocket.isConnected()) {
                mBluetoothSocket = device.createRfcommSocketToServiceRecord(UUID.fromString("0000111f-0000-1000-8000-00805f9b39fb"));
                mBluetoothSocket.connect();
            }
            OutputStream mOutputStream = mBluetoothSocket.getOutputStream();
            mOutputStream.write(msg.getBytes("UTF-8"));
            mOutputStream.flush();
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
            if(mBluetoothSocket != null) {
                mBluetoothSocket.close();
                mBluetoothSocket = null;
            }
        }
    }

    class MyBluetoothGattCallback extends BluetoothGattCallback {

//        MyBluetoothGattCallback(BluetoothDevice device){
//
//        }

        //远程设备改变了物理层
        @Override
        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyUpdate(gatt, txPhy, rxPhy, status);
        }

        @Override
        public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyRead(gatt, txPhy, rxPhy, status);
        }

        //连接状态改变
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            switch (newState) {
                case BluetoothGatt.STATE_CONNECTED:
                    LogUtil.e(TAG, "蓝牙连接成功");
                    gatt.discoverServices(); //触发onServicesDiscovered()
                    break;
                case BluetoothGatt.STATE_DISCONNECTED:
                    LogUtil.e(TAG, "蓝牙已断开连接");
                    break;
                case BluetoothGatt.STATE_CONNECTING:
                    LogUtil.e(TAG, "蓝牙正在连接");
                    break;
                case BluetoothGatt.STATE_DISCONNECTING:
                    LogUtil.e(TAG, "蓝牙正断开连接");
                    break;
            }
            super.onConnectionStateChange(gatt, status, newState);
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            LogUtil.e(TAG, gatt.getDevice().getAddress() + "  " + status);
            super.onServicesDiscovered(gatt, status);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            LogUtil.e(TAG, "onCharacteristicWrite " + status);
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            LogUtil.e(TAG, "onCharacteristicChanged");
            super.onCharacteristicChanged(gatt, characteristic);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
        }
    }

    private ScanCallback mScanCallback = new ScanCallback() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            mBluetoothWatcher.actionFound(result.getDevice(), result.getRssi());
            LogUtil.e(TAG, "SCANNER");
            super.onScanResult(callbackType, result);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }
    };
}
