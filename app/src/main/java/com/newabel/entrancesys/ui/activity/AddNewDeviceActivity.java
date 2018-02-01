package com.newabel.entrancesys.ui.activity;


import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.MessageEvent;
import com.newabel.entrancesys.service.presenter.DeviceAddNewPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.dialog.BottomDialog;
import com.newabel.entrancesys.ui.iview.DeviceAddNewView;
import com.newabel.entrancesys.ui.utils.LogUtil;
import com.newabel.entrancesys.ui.utils.MatchUtils;
import com.newabel.entrancesys.ui.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 新增设备
 */
public class AddNewDeviceActivity extends BaseActivity<DeviceAddNewPresenter> implements DeviceAddNewView {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.et_code)
    EditText etDeviceCode;
    @BindView(R.id.et_name)
    EditText etDeviceName;

    @BindView(R.id.et_belong_zone)
    TextView tvDeviceBelongArea;

    @BindView(R.id.et_device_type)
    EditText etDeviceType;

    @BindView(R.id.et_net_id)
    EditText et_net_id;

    @BindView(R.id.et_communicate_timeout)
    EditText et_communicate_timeout;

    @BindView(R.id.et_ip_address)
    EditText etIpAddress;

    @BindView(R.id.et_ip_port)
    EditText etPort;

    @BindView(R.id.tv_communicate_method)
    TextView tvPortType;

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

    @BindView(R.id.rl_choose_commu_method)
    RelativeLayout rl_choose_commu_method;


    private Map<String, Object> mData;
    private int PlaceID;//所属区域的id
    private String placeNameBelong;//所属区域的名称

    private String deviceNum, deviceName, deviceClass;
    private int PortType;//端口类型
    private String Timeouts;//通讯超时(单位是s)

    private String IpAddr;//ip地址
    private String IpPort;//ip端口


    @Override
    protected DeviceAddNewPresenter createPresenter() {
        return new DeviceAddNewPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_add_new_device;
    }

    @Override
    protected void initView() {
        tvTitle.setText(getString(R.string.title_activity_add_new_device));
    }

    @Override
    protected void initData() {
        super.initData();
        String jStr = this.getIntent().getStringExtra("mData");
        LogUtil.e("----------------", "------jStr:" + jStr);

        if (!TextUtils.isEmpty(jStr)) {
            mData = new Gson().fromJson(jStr, Map.class);
            PlaceID = Math.round(Float.valueOf(mData.get("PlaceID").toString()));
            placeNameBelong = mData.get("PlaceName").toString();
            tvDeviceBelongArea.setText(placeNameBelong);
        }

    }

    @OnClick({R.id.ll_back, R.id.btn_submit, R.id.rl_baudrate, R.id.rl_serial_port, R.id.rl_choose_commu_method, R.id.iv_state})
    void onIconClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.rl_choose_commu_method: {
                BottomDialog bottomDialog = new BottomDialog(this, mPresenter.getPortTypeData(PortType));
                bottomDialog.show();
                bottomDialog.setOnClickListener(new PortTypeClickListener());
                break;
            }
            case R.id.rl_baudrate: {
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
                break;
            }
            case R.id.rl_serial_port: {
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
                break;
            }
            case R.id.iv_state:
                iv_state.setSelected(!iv_state.isSelected());
                break;
            case R.id.btn_submit:
                deviceNum = etDeviceCode.getText().toString();
                deviceName = etDeviceName.getText().toString();
                deviceClass = etDeviceType.getText().toString();

                Timeouts = et_communicate_timeout.getText().toString();
                IpAddr = etIpAddress.getText().toString();
                IpPort = etPort.getText().toString();

                if (TextUtils.isEmpty(deviceNum)) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_device_num_isnull));
                    break;
                }

                if (TextUtils.isEmpty(deviceName)) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_device_name_isnull));
                    break;
                }

                if (TextUtils.isEmpty(deviceClass)) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_choose_device_type));
                    break;
                }

                if (0 == PortType) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_choose_device_port_type));
                    break;
                }

                String devPwd = "";

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

                if (TextUtils.isEmpty(etPort.getText())) {
                    UIUtils.showToast(UIUtils.getString(R.string.device_data_str_25));
                    break;
                }

                mPresenter.deviceAdd(0, deviceNum, deviceName, deviceClass,
                        PlaceID, devPwd, Integer.valueOf(et_net_id.getText().toString().trim()),
                        Timeouts, PortType, tv_serial_port.getText().toString(),
                        Integer.valueOf(tv_baudrate.getText().toString()), IpAddr,
                        IpPort, iv_state.isSelected(), et_remark.getText().toString().trim());

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
            tvPortType.setText(mPresenter.getPortType(PortType));
        }
    }

    @Override
    public void deviceAddSuc() {
        UIUtils.showToast(UIUtils.getString(R.string.add_success));
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTION_ACTIVITY_UPDATE));

        finish();
    }

    @Override
    public void deviceAddFail(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            UIUtils.showToast(msg);
        } else {
            UIUtils.showToast(UIUtils.getString(R.string.add_fail));
        }


    }

    @Override
    public Context getContext() {
        return this;
    }
}
