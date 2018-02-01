package com.newabel.entrancesys.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.service.entity.MessageEvent;
import com.newabel.entrancesys.service.presenter.DoorAddNewPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.dialog.BottomDialog;
import com.newabel.entrancesys.ui.dialog.DeviceDialog;
import com.newabel.entrancesys.ui.iview.DoorAddNewView;
import com.newabel.entrancesys.ui.utils.LogUtil;
import com.newabel.entrancesys.ui.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Date: 2018/1/11 16:05
 * Description:添加门禁资料
 */

public class DoorDataAddActivity extends BaseActivity<DoorAddNewPresenter> implements DoorAddNewView, DeviceDialog.DataSelectedListener {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_area_belong_value)
    TextView tvAreaBelong;

    @BindView(R.id.tv_ctr_type)
    TextView tvDoorType;

    @BindView(R.id.et_code)
    EditText etDoorNo;

    @BindView(R.id.et_name)
    EditText etDoorName;

    @BindView(R.id.et_remark)
    EditText etDoorRemark;

    @BindView(R.id.rl_ctr_device)
    RelativeLayout rl_ctr_device;

    @BindView(R.id.tv_ctr_device)
    TextView tv_ctr_device;

    private Map<String, Object> mData;

    private int doorType;


    private String DoorNo, DoorName;

    private int PlaceID;

    private String Remark;

    @Override
    protected DoorAddNewPresenter createPresenter() {
        return new DoorAddNewPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_doordata_add;
    }


    @Override
    protected void initView() {
        super.initView();
        tv_title.setText(UIUtils.getString(R.string.text_add_door));


    }

    @Override
    protected void initData() {
        super.initData();

        String jStr = getIntent().getStringExtra("mData");
        LogUtil.e("-------------", "------------jStr:" + jStr);

        if (jStr != null) {
            mData = new Gson().fromJson(jStr, Map.class);

            String placeName = mData.get("PlaceName").toString();
            PlaceID = Math.round(Float.valueOf(mData.get("PlaceID").toString()));
            if (!TextUtils.isEmpty(placeName)) {
                tvAreaBelong.setText(placeName);
            }
        }
    }


    @OnClick({R.id.ll_back, R.id.rl_door_type, R.id.btn_submit,R.id.rl_ctr_device})
    void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_back:
                finish();
                break;

            case R.id.rl_door_type:
                BottomDialog bottomSexDialog = new BottomDialog(this, mPresenter.getDoorData(doorType));
                bottomSexDialog.show();
                bottomSexDialog.setOnClickListener(new DoorTypeClickListener());
                break;
            case R.id.rl_ctr_device:
                DeviceDialog deviceDialog = new DeviceDialog(this, Constant.MODULE_DEVICE_DATA);
                deviceDialog.setDataSelectedListener(this);
                deviceDialog.show();
                break;
            case R.id.btn_submit:
                DoorNo = etDoorNo.getText().toString();
                DoorName = etDoorName.getText().toString();
                Remark = etDoorRemark.getText().toString();

                if (TextUtils.isEmpty(DoorNo)) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_door_num_isnull));
                    break;
                }

                if (TextUtils.isEmpty(DoorName)) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_door_name_isnull));
                    break;
                }

                if (doorType == 0) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_choose_door_type));
                    break;
                }

                int deviceID = 0;
                if(tv_ctr_device.getTag() == null){
                    UIUtils.showToast(UIUtils.getString(R.string.entrance_data_str_29));
                    break;
                }
                deviceID = Math.round(Float.valueOf(((Map<String,Object>)tv_ctr_device.getTag()).get("DeviceID").toString()));
                mPresenter.doorAdd(0, DoorNo, DoorName, doorType, PlaceID, deviceID, 0, true, Remark);

                break;
        }
    }

    @Override
    public void doorAddSuc() {
        UIUtils.showToast(UIUtils.getString(R.string.add_success));
        //新增机构成功,发送一个消息出去
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTION_ACTIVITY_UPDATE));

        finish();
    }

    @Override
    public void doorAddError(String msg) {
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

    @Override
    public void setSelectData(Map<String, Object> map) {
        tv_ctr_device.setText(map.get("DevName").toString());
        tv_ctr_device.setTag(map);
    }

    class DoorTypeClickListener implements BottomDialog.OnClickListener {

        @Override
        public void onClick(Dialog dialog, int which) {
            switch (which) {
                case 0:
                    doorType = 1;
                    break;
                case 1:
                    doorType = 2;
                    break;
                case 2:
                    doorType = 3;
                    break;
            }
            tvDoorType.setText(mPresenter.getDoorType(doorType));
        }
    }

}
