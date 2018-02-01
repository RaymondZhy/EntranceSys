package com.newabel.entrancesys.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.MePresenter;
import com.newabel.entrancesys.ui.activity.AboutUsActivity;
import com.newabel.entrancesys.ui.activity.LanguageActivity;
import com.newabel.entrancesys.ui.activity.LoginActivity;
import com.newabel.entrancesys.ui.activity.NotificationSettingActivity;
import com.newabel.entrancesys.ui.activity.PersonalInfoActivity;
import com.newabel.entrancesys.ui.activity.PwdModifyActivity;
import com.newabel.entrancesys.ui.base.BaseFragment;
import com.newabel.entrancesys.ui.dialog.CommonDialog;
import com.newabel.entrancesys.ui.iview.MeView;


/**

 * Date: 2017/11/30 15:31
 * Description:
 */

public class MeFragment extends BaseFragment<MePresenter> implements View.OnClickListener,MeView, CommonDialog.OnClickListener {

    private final int REQUEST_CODE_LANGUAGE = 101;
    private RelativeLayout rl_info;
    private RelativeLayout rl_message_setting;
    private RelativeLayout rl_modify_pwd;
    private RelativeLayout rl_language;
    private RelativeLayout rl_about_us;
    private RelativeLayout rl_exit;

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_me;
    }

    @Override
    protected MePresenter createPresenter() {
        return new MePresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initView(View rootView) {
        super.initView(rootView);

        mPresenter.attachView(this);

        rl_info = rootView.findViewById(R.id.rl_info);
        rl_message_setting = rootView.findViewById(R.id.rl_message_setting);
        rl_modify_pwd = rootView.findViewById(R.id.rl_modify_pwd);
        rl_modify_pwd = rootView.findViewById(R.id.rl_modify_pwd);
        rl_language = rootView.findViewById(R.id.rl_language);
        rl_about_us = rootView.findViewById(R.id.rl_about_us);
        rl_exit = rootView.findViewById(R.id.rl_exit);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        super.initListener();
        rl_info.setOnClickListener(this);
        rl_message_setting.setOnClickListener(this);
        rl_modify_pwd.setOnClickListener(this);
        rl_language.setOnClickListener(this);
        rl_about_us.setOnClickListener(this);
        rl_exit.setOnClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.rl_info:
                intent.setClass(getContext(), PersonalInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_message_setting:
                intent.setClass(getContext(), NotificationSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_modify_pwd:
                intent.setClass(getContext(), PwdModifyActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_language:
                intent.setClass(getContext(), LanguageActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_about_us:
                intent.setClass(getContext(), AboutUsActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_exit:
                CommonDialog dialog = new CommonDialog(getContext());
                dialog.setMessage(getString(R.string.me_str_6));
                dialog.setNegativeButton(getString(R.string.dialog_negative));
                dialog.setPositiveButton(getString(R.string.dialog_positive));
                dialog.setOnClickListener(this);
                dialog.show();
                break;
        }
    }

    @Override
    public void onClick(Dialog dialog, int which) {
        switch (which){
            case Dialog.BUTTON_NEGATIVE:

                break;
            case Dialog.BUTTON_POSITIVE:
                Intent intent = new Intent();
                intent.setClass(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }
}
