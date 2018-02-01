package com.newabel.entrancesys.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.MessageEvent;
import com.newabel.entrancesys.service.presenter.DeviceDetailPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.dialog.BottomDialog;
import com.newabel.entrancesys.ui.dialog.CommonDialog;
import com.newabel.entrancesys.ui.dialog.MenuDialog;
import com.newabel.entrancesys.ui.iview.DeviceDetailView;
import com.newabel.entrancesys.ui.utils.MatchUtils;
import com.newabel.entrancesys.ui.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设备资料详细
 */
public class DeviceDataActivity extends BaseActivity<DeviceDetailPresenter>
        implements MenuDialog.OnClickListener, CommonDialog.OnClickListener, DeviceDetailView {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_add)
    ImageView iv_add;

    @BindView(R.id.et_code)
    EditText etCode;

    @BindView(R.id.et_name)
    EditText etName;

    @BindView(R.id.et_device_type)
    EditText etDeviceType;

    @BindView(R.id.et_belong_zone)
    EditText etBelongZone;

    @BindView(R.id.et_communicate_timeout)
    EditText et_communicate_timeout;

    @BindView(R.id.et_ip_address)
    EditText etIpAddress;

    @BindView(R.id.et_ip_port)
    EditText etIpPort;

    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @BindView(R.id.et_net_id)
    EditText et_net_id;

    @BindView(R.id.tv_communicate_method)
    TextView tv_communicate_method;

    @BindView(R.id.tv_baudrate)
    TextView tv_baudrate;

    @BindView(R.id.tv_serial_port)
    TextView tv_serial_port;

    @BindView(R.id.iv_state)
    ImageView iv_state;

    @BindView(R.id.et_remark)
    EditText et_remark;

    @BindView(R.id.rl_baudrate)
    RelativeLayout rl_baudrate;

    @BindView(R.id.rl_serial_port)
    RelativeLayout rl_serial_port;

    private Map<String, Object> deviceInfo;

    String deviceNum;
    String deviceName;

    String deviceType;//设备类型

    String deviceBelongArea;

    String deviceCommunicateMethod;//串口类型

    String deviceCommunicateTimeOut;

    String deviceIpAddress;

    String devicePortNum;

    private boolean isEditMode = false;//是否处于编辑状态,默认为否
    private int PortType;

    private int deviceId;//设备id

    private int PlaceId;//区域id

    @Override
    protected DeviceDetailPresenter createPresenter() {
        return new DeviceDetailPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_device_data;
    }

    @Override
    protected void initView() {
        tvTitle.setText(getString(R.string.title_activity_device_data));

        iv_add.setVisibility(View.VISIBLE);
        iv_add.setImageResource(R.mipmap.ic_more);

    }

    @Override
    protected void initData() {
        super.initData();


        Intent intent = getIntent();

        if (intent != null) {
            String jStr = intent.getStringExtra("deviceInfo");
            deviceBelongArea = intent.getStringExtra("belongDept");
            if (jStr != null) {
                deviceInfo = new Gson().fromJson(jStr, Map.class);
                deviceNum = deviceInfo.get("DeviceNo").toString();
                deviceType = deviceInfo.get("DevClass").toString();
                deviceName = deviceInfo.get("DevName").toString();
                deviceId = Math.round(Float.valueOf(deviceInfo.get("DeviceID").toString()));
                PlaceId = Math.round(Float.valueOf(deviceInfo.get("PlaceID").toString()));
//                deviceCommunicateMethod = deviceInfo.get("DeviceNo").toString();
                deviceCommunicateTimeOut = Math.round(Float.valueOf(deviceInfo.get("Timeouts").toString())) + "";
                deviceIpAddress = deviceInfo.get("IpAddr").toString();
                devicePortNum = Math.round(Float.valueOf(deviceInfo.get("IpPort").toString())) + "";

                etCode.setText(deviceNum);
                etName.setText(deviceName);
                etDeviceType.setText(deviceType);
                etBelongZone.setText(deviceBelongArea);

                et_communicate_timeout.setText(deviceCommunicateTimeOut);
                etIpAddress.setText(deviceIpAddress);
                etIpPort.setText(devicePortNum);
                et_net_id.setText(Math.round(Float.valueOf(deviceInfo.get("NetID").toString())) + "");
                PortType = Math.round(Float.valueOf(deviceInfo.get("PortType").toString()));
                tv_communicate_method.setText(mPresenter.getPortType(Math.round(Float.valueOf(deviceInfo.get("PortType").toString()))));

                tv_baudrate.setText(Math.round(Float.valueOf(deviceInfo.get("Baudrate").toString())) + "");
                tv_serial_port.setText(deviceInfo.get("SerialPort").toString());
                iv_state.setSelected(Boolean.parseBoolean(deviceInfo.get("StopState").toString()));
                et_remark.setText(deviceInfo.get("Remark").toString());
            }
        }

    }

    @OnClick({R.id.ll_back, R.id.iv_add, R.id.btn_submit, R.id.rl_choose_commu_method, R.id.rl_baudrate, R.id.rl_serial_port, R.id.iv_state})
    void onIconClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;

            case R.id.rl_choose_commu_method:
                if (isEditMode) {
                    BottomDialog bottomDialog = new BottomDialog(this, mPresenter.getPortTypeData(PortType));
                    bottomDialog.show();
                    bottomDialog.setOnClickListener(new PortTypeClickListener());
                }
                break;
            case R.id.rl_baudrate:
                if (isEditMode) {
                    final List<Map<String, Object>> list = mPresenter.getBaudrateData(tv_baudrate.getText().toString());
                    BottomDialog bottomDialog = new BottomDialog(this, list);
                    bottomDialog.show();
                    bottomDialog.setOnClickListener(new BottomDialog.OnClickListener() {
                        @Override
                        public void onClick(Dialog dialog, int which) {
                            String mBaudrate = list.get(which).get("name").toString();
                            tv_baudrate.setText(mBaudrate);
                        }
                    });
                }
                break;
            case R.id.rl_serial_port:
                if (isEditMode) {
                    final List<Map<String, Object>> list = mPresenter.getSerialPortData(tv_serial_port.getText().toString());
                    BottomDialog bottomDialog = new BottomDialog(this, list);
                    bottomDialog.show();
                    bottomDialog.setOnClickListener(new BottomDialog.OnClickListener() {
                        @Override
                        public void onClick(Dialog dialog, int which) {
                            String mSerialPort = list.get(which).get("name").toString();
                            tv_serial_port.setText(mSerialPort);
                        }
                    });
                }
                break;

            case R.id.iv_state:
                iv_state.setSelected(!iv_state.isSelected());
                break;
            case R.id.iv_add:
                MenuDialog dialog = new MenuDialog(this);
                String[] items = {getString(R.string.edit), getString(R.string.delete)};
                dialog.setItem(items);
                dialog.setWidth(80);
                dialog.setOnClickListener(this);
                dialog.show(iv_add);
                break;
            case R.id.btn_submit:
                deviceNum = etCode.getText().toString();
                deviceName = etName.getText().toString();
                deviceType = etDeviceType.getText().toString();
                deviceCommunicateTimeOut = et_communicate_timeout.getText().toString();
                deviceIpAddress = etIpAddress.getText().toString();
                devicePortNum = etIpPort.getText().toString();

                if (TextUtils.isEmpty(deviceNum)) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_device_num_isnull));
                    break;
                }

                if (TextUtils.isEmpty(deviceName)) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_device_name_isnull));
                    break;
                }

                if (TextUtils.isEmpty(deviceType)) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_choose_device_type));
                    break;
                }
                if (0 == PortType) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_choose_device_port_type));
                    break;
                }

                String devPwd = deviceInfo.get("DevPwd").toString();

                if (TextUtils.isEmpty(et_net_id.getText())) {
                    UIUtils.showToast(UIUtils.getString(R.string.device_data_str_19));
                    break;
                }

                if (TextUtils.isEmpty(tv_baudrate.getText())) {
                    UIUtils.showToast(UIUtils.getString(R.string.device_data_str_21));
                    break;
                }

                if (TextUtils.isEmpty(tv_serial_port.getText())) {
                    UIUtils.showToast(UIUtils.getString(R.string.device_data_str_22));
                    break;
                }

                if (TextUtils.isEmpty(et_communicate_timeout.getText())) {
                    UIUtils.showToast(UIUtils.getString(R.string.device_data_str_23));
                    break;
                }

                if (TextUtils.isEmpty(etIpAddress.getText())) {
                    UIUtils.showToast(UIUtils.getString(R.string.device_data_str_24));
                    break;
                }

                if(!MatchUtils.isIp(etIpAddress.getText().toString())){
                    UIUtils.showToast(UIUtils.getString(R.string.device_data_str_30));
                    break;
                }

                if (TextUtils.isEmpty(etIpPort.getText())) {
                    UIUtils.showToast(UIUtils.getString(R.string.device_data_str_25));
                    break;
                }


                mPresenter.deviceModify(deviceId, deviceNum, deviceName, deviceType, PlaceId,
                        devPwd, Integer.valueOf(et_net_id.getText().toString()),
                        et_communicate_timeout.getText().toString().trim(), PortType,
                        tv_serial_port.getText().toString(), Integer.valueOf(tv_baudrate.getText().toString().trim()),
                        deviceIpAddress, devicePortNum,
                        iv_state.isSelected(), et_remark.getText().toString().trim());

                break;

        }
    }

    @Override
    public void onClick(PopupWindow window, int which) {
        switch (which) {
            case 0://编辑
                isEditMode = true;
                etCode.setEnabled(true);
                etCode.setSelection(etCode.getText().length());
                etName.setEnabled(true);
                etDeviceType.setEnabled(true);
                et_communicate_timeout.setEnabled(true);
                etIpAddress.setEnabled(true);
                etIpPort.setEnabled(true);
                et_net_id.setEnabled(true);
                et_remark.setEnabled(true);

                btnSubmit.setVisibility(View.VISIBLE);

                break;
            case 1://删除
                CommonDialog dialog = new CommonDialog(this);
                dialog.setTitle(getString(R.string.dialog_title_delete));
                dialog.setMessage(getString(R.string.device_data_str_18));
                dialog.setNegativeButton(getString(R.string.dialog_negative));
                dialog.setPositiveButton(getString(R.string.dialog_positive));
                dialog.setOnClickListener(this);
                dialog.show();
                break;


        }
    }

    @Override
    public void onClick(Dialog dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_NEGATIVE:

                break;
            case Dialog.BUTTON_POSITIVE:
                mPresenter.deleteDevice(deviceId);
                break;
        }
    }

    class PortTypeClickListener implements BottomDialog.OnClickListener {

        @Override
        public void onClick(Dialog dialog, int which) {
            switch (which) {
                case 0:
                    PortType = 1;
                    break;
                case 1:
                    PortType = 2;
                    break;
                case 2:
                    PortType = 3;
                    break;
                case 3:
                    PortType = 4;
                    break;
            }
            tv_communicate_method.setText(mPresenter.getPortType(PortType));
        }
    }

    @Override
    public void deviceModifySuc() {
        //删除成功,关闭当前界面,回到上一个界面,同时发个消息过去,刷新界面
        UIUtils.showToast(UIUtils.getString(R.string.modify_success));
        //发一个消息出去
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTION_ACTIVITY_UPDATE));

        finish();
    }

    @Override
    public void deviceModifyError(String msg) {
        if (!TextUtils.isEmpty(msg))
            UIUtils.showToast(msg);
        else
            UIUtils.showToast(UIUtils.getString(R.string.modify_fail));
    }

    @Override
    public void deviceDeleteSuc() {

        //删除成功,关闭当前界面,回到上一个界面,同时发个消息过去,刷新界面
        UIUtils.showToast(UIUtils.getString(R.string.delete_success));
        //发一个消息出去
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTION_ACTIVITY_UPDATE));

        finish();
    }

    @Override
    public void deviceDeleteError(String msg) {
        if (!TextUtils.isEmpty(msg))
            UIUtils.showToast(msg);
        else
            UIUtils.showToast(UIUtils.getString(R.string.delete_fail));
    }

    @Override
    public Context getContext() {
        return this;
    }

}
