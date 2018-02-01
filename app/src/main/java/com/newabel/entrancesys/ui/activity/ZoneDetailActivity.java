package com.newabel.entrancesys.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.MessageEvent;
import com.newabel.entrancesys.service.presenter.ZoneDetailPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.dialog.BottomDialog;
import com.newabel.entrancesys.ui.dialog.CommonDialog;
import com.newabel.entrancesys.ui.dialog.MenuDialog;
import com.newabel.entrancesys.ui.iview.ZoneDetailView;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ZoneDetailActivity extends BaseActivity<ZoneDetailPresenter> implements ZoneDetailView, MenuDialog.OnClickListener, CommonDialog.OnClickListener, BottomDialog.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.iv_add)
    ImageView iv_add;

    @BindView(R.id.ll_edit_invisible)
    LinearLayout ll_edit_invisible;

    @BindView(R.id.et_zone_code)
    EditText et_zone_code;

    @BindView(R.id.et_zone_name)
    EditText et_zone_name;

    @BindView(R.id.et_note)
    EditText et_note;

    @BindView(R.id.btn_submit)
    Button btn_submit;

    @BindView(R.id.tv_zone_type)
    TextView tv_zone_type;

    @BindView(R.id.rl_zone_type)
    RelativeLayout rl_zone_type;

    @BindView(R.id.tv_belong)
    TextView tv_belong;

    private boolean isEditMode;
    private Map<String, Object> mData;
    private String mRoot;
    private int mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_zone_detail);
    }

    @Override
    protected ZoneDetailPresenter createPresenter() {
        return new ZoneDetailPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_zone_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        isEditMode = this.getIntent().getBooleanExtra("isEditMode", true);
        mRoot = this.getIntent().getStringExtra("mRoot");
        String jStr = this.getIntent().getStringExtra("mData");

        if (isEditMode) {
            tv_title.setText(getString(R.string.zone_detail_str_23));
            iv_add.setVisibility(View.INVISIBLE);
            et_zone_code.setText("");
            et_zone_code.setEnabled(true);
            et_zone_name.setText("");
            et_zone_name.setEnabled(true);
            tv_zone_type.setText("");
            et_note.setText("");
            et_note.setEnabled(true);
            ll_edit_invisible.setVisibility(View.GONE);
            btn_submit.setVisibility(View.VISIBLE);
        } else {
            iv_add.setVisibility(View.VISIBLE);
            iv_add.setImageResource(R.mipmap.ic_more);
            tv_title.setText(getString(R.string.title_activity_zone_detail));
            et_zone_code.setEnabled(false);
            et_zone_name.setEnabled(false);
            et_note.setEnabled(false);
            et_note.setHint("");
            ll_edit_invisible.setVisibility(View.VISIBLE);
            btn_submit.setVisibility(View.GONE);
        }

        if (jStr != null) {
            mData = new Gson().fromJson(jStr, Map.class);
        }
        mRoot = mRoot == null ? "" : mRoot;
    }

    @Override
    protected void initData() {
        super.initData();
        if (mData != null) {
            if (iv_add.getVisibility() == View.VISIBLE) {
                et_zone_code.setText(mData.get("PlaceNo").toString());
                et_zone_name.setText(mData.get("PlaceName").toString());
                tv_belong.setText(mRoot);
                mType = Math.round(Float.valueOf(mData.get("PlaceType").toString()));
                tv_zone_type.setText(mPresenter.getZoneTypeStr(mType));
            } else {
                tv_belong.setText(mData.get("PlaceName").toString());
                mType = Math.round(Float.valueOf(mData.get("PlaceType").toString()));
                tv_zone_type.setText(mPresenter.getZoneTypeStr(mType));
            }
        }

    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @OnClick({R.id.ll_back, R.id.iv_add, R.id.rl_zone_type, R.id.btn_submit})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.iv_add:
                MenuDialog dialog = new MenuDialog(this);
                dialog.setWidth(80);
                String[] items = {getString(R.string.edit), getString(R.string.delete)};
                dialog.setItem(items);
                dialog.setOnClickListener(this);
                dialog.show(iv_add);
                break;
            case R.id.rl_zone_type:
                if (isEditMode) {
                    BottomDialog bottomDialog = new BottomDialog(this, mPresenter.getData(mType));
                    bottomDialog.setOnClickListener(this);
                    bottomDialog.show();
                }
                break;
            case R.id.btn_submit:
                if (mPresenter.checkInput(et_zone_code.getText().toString(), et_zone_name.getText().toString(), tv_zone_type.getText().toString())) {
                    int placeID = Math.round(Float.valueOf(mData.get("PlaceID").toString()));
                    String placeNo = et_zone_code.getText().toString();
                    int masterID = iv_add.getVisibility() != View.VISIBLE ? Math.round(Float.valueOf(mData.get("PlaceID").toString())) : Math.round(Float.valueOf(mData.get("MasterID").toString()));
                    String placeName = et_zone_name.getText().toString();
                    int placeType = mType;
                    if (iv_add.getVisibility() != View.VISIBLE) {
                        mPresenter.add(placeID, placeNo, masterID, placeName, placeType); //此时placeID可随便填
                    } else {
                        mPresenter.modify(placeID, placeNo, masterID, placeName, placeType);
                    }
                    break;
                }
        }
    }

    @Override
    public void onClick(PopupWindow window, int which) {
        switch (which) {
            case 0:
                et_zone_code.setSelection(et_zone_code.getText().length());
                et_zone_code.setEnabled(true);
                et_zone_name.setEnabled(true);
                et_note.setEnabled(true);
                et_note.setHint("");
                ll_edit_invisible.setVisibility(View.GONE);
                btn_submit.setVisibility(View.VISIBLE);
                isEditMode = true;
                break;
            case 1:
                CommonDialog dialog = new CommonDialog(this);
                dialog.setTitle(getString(R.string.dialog_title_delete));
                dialog.setMessage(getString(R.string.zone_detail_str_22));
                dialog.setPositiveButton(getString(R.string.dialog_positive));
                dialog.setNegativeButton(getString(R.string.dialog_negative));
                dialog.setOnClickListener(this);
                dialog.show();
                break;
        }
    }

    @Override
    public void onClick(Dialog dialog, int which) {
        if (dialog instanceof CommonDialog) {
            switch (which) {
                case Dialog.BUTTON_NEGATIVE:

                    break;
                case Dialog.BUTTON_POSITIVE:
                    int placeID = Math.round(Float.valueOf(mData.get("PlaceID").toString()));
                    mPresenter.delete(placeID);
                    break;
            }
        } else if (dialog instanceof Dialog) {
            switch (which) {
                case 0:
                    mType = 1;
                    break;
                case 1:
                    mType = 2;
                    break;
                case 2:
                    mType = 3;
                    break;
            }
            tv_zone_type.setText(mPresenter.getZoneTypeStr(mType));
        }
    }

    @Override
    public void startActivity() {
        MessageEvent event = new MessageEvent(MessageEvent.ACTION_ACTIVITY_UPDATE);
        EventBus.getDefault().post(event);
        this.finish();
    }

    @Override
    public Context getContext() {
        return this;
    }


}
