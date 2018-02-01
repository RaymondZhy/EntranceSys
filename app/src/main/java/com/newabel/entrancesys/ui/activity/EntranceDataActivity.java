package com.newabel.entrancesys.ui.activity;

import android.app.Dialog;
import android.content.Context;
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
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.service.entity.MessageEvent;
import com.newabel.entrancesys.service.presenter.EntranceDataPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.dialog.BottomDialog;
import com.newabel.entrancesys.ui.dialog.CommonDialog;
import com.newabel.entrancesys.ui.dialog.DeviceDialog;
import com.newabel.entrancesys.ui.dialog.MenuDialog;
import com.newabel.entrancesys.ui.iview.EntranceDataView;
import com.newabel.entrancesys.ui.utils.LogUtil;
import com.newabel.entrancesys.ui.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class EntranceDataActivity extends BaseActivity<EntranceDataPresenter>
        implements EntranceDataView, MenuDialog.OnClickListener, CommonDialog.OnClickListener, DeviceDialog.DataSelectedListener {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.iv_add)
    ImageView iv_add;

    @BindView(R.id.et_code)
    EditText et_code;

    @BindView(R.id.et_name)
    EditText et_name;

    @BindView(R.id.tv_ctr_type)
    TextView tv_ctr_type;

    @BindView(R.id.tv_ctr_device)
    TextView tv_ctr_device;

    @BindView(R.id.iv_state)
    ImageView iv_state;

    @BindView(R.id.et_remark)
    EditText et_remark;

    @BindView(R.id.btn_submit)
    Button btn_submit;

    @BindView(R.id.rl_ctr_type)
    RelativeLayout rl_ctr_type;

    @BindView(R.id.rl_ctr_device)
    RelativeLayout rl_ctr_device;

    @BindView(R.id.rl_state)
    RelativeLayout rl_state;

    @BindView(R.id.rl_child_device)
    RelativeLayout rl_child_device;

    @BindView(R.id.tv_child_device)
    TextView tv_child_device;

    @BindView(R.id.rl_remark)
    RelativeLayout rl_remark;

    @BindView(R.id.tv_area_belong_value)
    TextView tvBelongArea;

    private boolean isEditMode;
    private Map<String, Object> mData;
//    private int mType = 1;

    private int DoorID;//门禁ID
    private String DoorNo;//门禁编号
    private String DoorName;//门禁名称
    private int DoorType;//门禁类型
    private int PlaceID;//区域ID
    private int DeviceID;//设备ID
    private int ChildID;//子设备ID
    private boolean StopState;//是否停用
    private String Remark;//备注

    private String areaBelong;//所属区域

    @Override
    protected EntranceDataPresenter createPresenter() {
        return new EntranceDataPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_entrance_data;
    }

    @Override
    protected void initView() {
        super.initView();
        isEditMode = this.getIntent().getBooleanExtra("isEditMode", true);
        areaBelong = getIntent().getStringExtra("belongDept");
        if (areaBelong != null) {
            tvBelongArea.setText(areaBelong);
        }

        String jStr = this.getIntent().getStringExtra("mData");
        if (jStr != null) {
            LogUtil.e("----------------", "---------jStr:" + jStr);
            mData = new Gson().fromJson(jStr, Map.class);


        }
        tv_title.setText(getString(R.string.title_activity_entrance_data));
        if (!isEditMode) {
            iv_add.setVisibility(View.VISIBLE);
            iv_add.setImageResource(R.mipmap.ic_more);
//            rl_state.setVisibility(View.VISIBLE);
            btn_submit.setVisibility(View.GONE);

            et_code.setEnabled(false);
            et_name.setEnabled(false);
            et_remark.setEnabled(false);
        } else {
            et_code.setText("");
            et_name.setText("");
            tv_ctr_type.setText("");
            tv_ctr_device.setText("");
            rl_state.setVisibility(View.GONE);
            rl_child_device.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        if (mData != null) {


            et_code.setText(mData.get("DoorNo").toString());
            et_name.setText(mData.get("DoorName").toString());

            DoorType = Math.round(Float.valueOf(mData.get("DoorType").toString()));
            PlaceID = Math.round(Float.valueOf(mData.get("PlaceID").toString()));
            DoorID = Math.round(Float.valueOf(mData.get("DoorID").toString()));
            tv_ctr_type.setText(mPresenter.getDoorType(DoorType));
            tv_child_device.setText("" + Math.round(Float.valueOf(mData.get("ChildID").toString())));

//            mPresenter.getDeviceDat("2", String.valueOf(Math.round(Float.valueOf(mData.get("DeviceID").toString()))));//2:按设备编号查询
            mPresenter.getDeviceData( Math.round(Float.valueOf(mData.get("DeviceID").toString())));//2:按设备编号查询
//            mPresenter.getDeviceData("1", mData.get("DeviceID").toString());//2:按设备编号查询

            rl_state.setSelected(Boolean.parseBoolean(mData.get("StopState").toString()));

            String remark = mData.get("Remark").toString();
            if (TextUtils.isEmpty(remark)) {
                et_remark.setHint("");
            } else {
                et_remark.setText(remark);
            }
        }
    }

    @Override
    protected void initListener() {
        super.initListener();

    }

    @OnClick({R.id.ll_back, R.id.iv_add, R.id.rl_ctr_type, R.id.rl_ctr_device, R.id.btn_submit, R.id.rl_state})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.iv_add:
                MenuDialog dialog = new MenuDialog(this);
                String[] items = {getString(R.string.edit), getString(R.string.delete)};
                dialog.setItem(items);
                dialog.setWidth(80);
                dialog.setOnClickListener(this);
                dialog.show(iv_add);
                break;
            case R.id.rl_ctr_type:
                if (isEditMode) {
                    BottomDialog bottomDialog = new BottomDialog(this, mPresenter.getData(DoorType));
                    bottomDialog.setOnClickListener(new BottomDialog.OnClickListener() {
                        @Override
                        public void onClick(Dialog dialog, int which) {
                            DoorType = which + 1;
                            tv_ctr_type.setText(mPresenter.getDoorType(DoorType));
                        }
                    });
                    bottomDialog.show();
                }
                break;
            case R.id.rl_ctr_device:
                if (isEditMode) {
                    DeviceDialog deviceDialog = new DeviceDialog(this, Constant.MODULE_DEVICE_DATA);
                    deviceDialog.setDataSelectedListener(this);
                    deviceDialog.show();
                }
                break;
            case R.id.rl_state:
                if (isEditMode) {
                    rl_state.setSelected(!rl_state.isSelected());
                }
                break;
            case R.id.btn_submit:

                DoorNo = et_code.getText().toString();
                DoorName = et_name.getText().toString();
                Remark = et_remark.getText().toString();

                if (TextUtils.isEmpty(DoorNo)) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_door_num_isnull));
                    break;
                }
                if (TextUtils.isEmpty(DoorName)) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_door_name_isnull));
                    break;
                }
                if (DoorType == 0) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_choose_door_type));
                    break;
                }
                int deviceId;
                if (tv_ctr_device.getTag() != null) {
                    deviceId = Math.round(Float.valueOf(((Map<String, Object>) tv_ctr_device.getTag()).get("DeviceID").toString()));
                } else {
                    deviceId = Math.round(Float.valueOf(mData.get("DeviceID").toString()));
                }
                mPresenter.DoorModify(DoorID, DoorNo, DoorName, DoorType, PlaceID, deviceId, 0, true, Remark);
                break;
        }
    }

    @Override
    public void onClick(PopupWindow window, int which) {
        switch (which) {
            case 0: //编辑
                isEditMode = true;
                et_code.setEnabled(true);
                et_code.setSelection(et_code.getText().length());
                et_name.setEnabled(true);
                rl_state.setVisibility(View.GONE);
                btn_submit.setVisibility(View.VISIBLE);
                tv_ctr_device.setHint(getString(R.string.entrance_data_str_8));
                et_remark.setEnabled(true);
                if (TextUtils.isEmpty(et_remark.getText().toString())) {
                    et_remark.setHint(getString(R.string.entrance_data_str_12));
                }
                rl_state.setVisibility(View.GONE);
                rl_child_device.setVisibility(View.GONE);
                break;
            case 1: //删除
                CommonDialog dialog = new CommonDialog(this);
                dialog.setTitle(getString(R.string.dialog_title_delete));
                dialog.setMessage(getString(R.string.entrance_data_str_18));
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
                mPresenter.deleteDoor(DoorID);
                break;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showDeepData(Map<String, Object> map) {
        if (map != null) {
            tv_ctr_device.setText(map.get("DevName").toString());
        } else {
            tv_ctr_device.setHint("");
        }
    }

    @Override
    public void doorModifySuc() {
        UIUtils.showToast(UIUtils.getString(R.string.modify_success));
        //新增机构成功,发送一个消息出去
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTION_ACTIVITY_UPDATE));

        finish();

    }

    @Override
    public void doorModifyError(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            UIUtils.showToast(msg);
        } else {
            UIUtils.showToast(UIUtils.getString(R.string.add_fail));
        }
    }

    @Override
    public void doorDeleteSuc() {
        UIUtils.showToast(UIUtils.getString(R.string.delete_success));
        //新增机构成功,发送一个消息出去
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTION_ACTIVITY_UPDATE));

        finish();
    }

    @Override
    public void doorDeleteError(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            UIUtils.showToast(msg);
        } else {
            UIUtils.showToast(UIUtils.getString(R.string.delete_fail));
        }
    }

    @Override
    public void setSelectData(Map<String, Object> map) {
        tv_ctr_device.setText(map.get("DevName").toString());
        tv_ctr_device.setTag(map);
    }
}
