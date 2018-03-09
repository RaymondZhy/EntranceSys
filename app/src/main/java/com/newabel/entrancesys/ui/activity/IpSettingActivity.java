package com.newabel.entrancesys.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.service.helper.Retrofit.RetrofitHelper;
import com.newabel.entrancesys.service.entity.MessageEvent;
import com.newabel.entrancesys.service.presenter.IpSettingPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.IpSettingView;
import com.newabel.entrancesys.ui.utils.MatchUtils;
import com.newabel.entrancesys.ui.utils.PreUtils;
import com.newabel.entrancesys.ui.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class IpSettingActivity extends BaseActivity<IpSettingPresenter> implements IpSettingView, View.OnClickListener {

    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.et_ip)
    EditText et_ip;

    @BindView(R.id.et_port)
    EditText et_port;

    @BindView(R.id.btn_save)
    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ip_setting);
    }

    @Override
    protected IpSettingPresenter createPresenter() {
        return new IpSettingPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_ip_setting;
    }

    @Override
    protected void initData() {
        super.initData();
        tv_title.setText(getString(R.string.title_activity_ip_setting));
        et_ip.setText(Constant.APP_SERVER_URL);
        et_ip.setSelection(et_ip.getText().length());
        et_port.setText(Constant.APP_SERVER_PORT);
    }

    @Override
    protected void initListener() {
        super.initListener();
        ll_back.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.btn_save:
                String ip = et_ip.getText().toString().trim();
                String port = et_port.getText().toString().trim();
                if(!TextUtils.equals(Constant.APP_SERVER_URL,ip) || !TextUtils.equals(Constant.APP_SERVER_PORT,port)) {
                    if (TextUtils.isEmpty(ip)) {
                        UIUtils.showToast(getString(R.string.ip_setting_str_6));
                        break;
                    }

                    if (!MatchUtils.isIp(ip)) {
                        UIUtils.showToast(getString(R.string.ip_setting_str_8));
                        break;
                    }

                    if (TextUtils.isEmpty(port)) {
                        UIUtils.showToast(getString(R.string.ip_setting_str_7));
                        break;
                    }

                    PreUtils.putString(PreUtils.IP_SETTING, ip + ":" + port); //保存设置

                    Constant.APP_SERVER_URL = ip;
                    Constant.APP_SERVER_PORT = port;

                    RetrofitHelper.reSetRetrofit();
                    MessageEvent message = new MessageEvent("UPDATE_URL");
                    EventBus.getDefault().post(message);
                }
                UIUtils.showToast(getString(R.string.ip_setting_str_9));
                this.finish();
                break;
        }
    }
}
