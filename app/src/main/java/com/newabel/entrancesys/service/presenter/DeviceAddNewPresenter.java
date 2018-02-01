package com.newabel.entrancesys.service.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.ui.dialog.LoadingDialog;
import com.newabel.entrancesys.ui.iview.DeviceAddNewView;
import com.newabel.entrancesys.ui.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Date: 2018/1/10 16:14
 * Description:
 */

public class DeviceAddNewPresenter extends BasePresenter<DeviceAddNewView> {

    public DeviceAddNewPresenter(DeviceAddNewView view) {
        super(view);
    }

    /**
     * @param DeviceID   设备ID
     * @param DeviceNo   设备编号
     * @param DevName    设备名称
     * @param DevClass   设备类型
     * @param PlaceID    区域ID
     * @param DevPwd     设备密码
     * @param NetID      网络ID
     * @param Timeouts   通讯超时
     * @param PortType   端口类型
     * @param SerialPort 串口号
     * @param Baudrate   波特率
     * @param IpAddr     IP地址
     * @param IpPort     IP端口
     * @param StopState  是否停用
     * @param Remark     备注
     */
    public void deviceAdd(int DeviceID, String DeviceNo, String DevName, String DevClass, int PlaceID, String DevPwd,
                          int NetID, String Timeouts, int PortType, String SerialPort, int Baudrate, String IpAddr,
                          String IpPort, boolean StopState, String Remark) {

        LoadingDialog.show(mView.getContext());

        Map<String, Object> map = new HashMap<>();
        map.put("DeviceID", DeviceID);
        map.put("DeviceNo", DeviceNo);
        map.put("DevName", DevName);
        map.put("DevClass", DevClass);
        map.put("PlaceID", PlaceID);
        map.put("DevPwd", DevPwd);
        map.put("NetID", NetID);
        map.put("Timeouts", Timeouts);
        map.put("PortType", PortType);
        map.put("SerialPort", SerialPort);
        map.put("Baudrate", Baudrate);
        map.put("IpAddr", IpAddr);
        map.put("IpPort", IpPort);
        map.put("StopState", StopState);
        map.put("Remark", Remark);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(map));
        addSubscription(retrofitService.DeviceAdd(body), new DisposableObserver<Map<String, Object>>() {
            @Override
            public void onNext(Map<String, Object> data) {
                int retCode = Math.round(Float.valueOf(data.get("retCode").toString()));
                switch (retCode) {
                    case Constant.RESULT_CODE_OK:
                        //新增机构成功
                        mView.deviceAddSuc();
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                String msg = parserThrowable(e);
                mView.deviceAddFail(msg);
                LoadingDialog.dismiss();
            }

            @Override
            public void onComplete() {
                LoadingDialog.dismiss();
            }
        });
    }


    public List<Map<String, Object>> getPortTypeData(int portType) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", getPortType(1));
        map1.put("isSelect", portType == 1);
        list.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", getPortType(2));
        map2.put("isSelect", portType == 2);
        list.add(map2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("name", getPortType(3));
        map3.put("isSelect", portType == 3);
        list.add(map3);

        Map<String, Object> map4 = new HashMap<>();
        map4.put("name", getPortType(4));
        map4.put("isSelect", portType == 4);
        list.add(map4);
        return list;
    }

    public String getPortType(int type) {
        String str = "";
        switch (type) {
            case 1:
                str = UIUtils.getString(R.string.device_data_str_14);
                break;
            case 2:
                str = UIUtils.getString(R.string.device_data_str_15);
                break;
            case 3:
                str = UIUtils.getString(R.string.device_data_str_16);
                break;
            case 4:
                str = UIUtils.getString(R.string.device_data_str_17);
                break;
        }
        return str;
    }

    public List<Map<String, Object>> getBaudrateData(String Baudrate){
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "2400");
        map1.put("isSelect", TextUtils.equals(Baudrate,"2400"));
        list.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "4800");
        map2.put("isSelect", TextUtils.equals(Baudrate,"4800"));
        list.add(map2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("name", "9600");
        map3.put("isSelect", TextUtils.equals(Baudrate,"9600"));
        list.add(map3);

        Map<String, Object> map4 = new HashMap<>();
        map4.put("name", "19200");
        map4.put("isSelect", TextUtils.equals(Baudrate,"19200"));
        list.add(map4);

        Map<String, Object> map5 = new HashMap<>();
        map5.put("name", "38400");
        map5.put("isSelect", TextUtils.equals(Baudrate,"38400"));
        list.add(map5);

        Map<String, Object> map6 = new HashMap<>();
        map6.put("name", "57600");
        map6.put("isSelect", TextUtils.equals(Baudrate,"57600"));
        list.add(map6);

        Map<String, Object> map7 = new HashMap<>();
        map7.put("name", "115200");
        map7.put("isSelect", TextUtils.equals(Baudrate,"115200"));
        list.add(map6);
        return list;
    }

    public List<Map<String, Object>> getSerialPortData(String port){
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "COM1");
        map1.put("isSelect", TextUtils.equals(port,"COM1"));
        list.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "COM2");
        map2.put("isSelect", TextUtils.equals(port,"COM2"));
        list.add(map2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("name", "COM3");
        map3.put("isSelect", TextUtils.equals(port,"COM3"));
        list.add(map3);

        Map<String, Object> map4 = new HashMap<>();
        map4.put("name", "COM4");
        map4.put("isSelect", TextUtils.equals(port,"COM4"));
        list.add(map4);

        Map<String, Object> map5 = new HashMap<>();
        map5.put("name", "COM5");
        map5.put("isSelect", TextUtils.equals(port,"COM5"));
        list.add(map5);

        Map<String, Object> map6 = new HashMap<>();
        map6.put("name", "COM6");
        map6.put("isSelect", TextUtils.equals(port,"COM6"));
        list.add(map6);

        Map<String, Object> map7 = new HashMap<>();
        map7.put("name", "COM7");
        map7.put("isSelect", TextUtils.equals(port,"COM1"));
        list.add(map6);
        return list;
    }
}
