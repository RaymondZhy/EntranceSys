package com.newabel.entrancesys.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.EntranceControlPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.dialog.CommonDialog;
import com.newabel.entrancesys.ui.iview.EntranceControlView;
import com.newabel.entrancesys.ui.utils.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class EntranceControlActivity extends BaseActivity<EntranceControlPresenter> implements EntranceControlView, CommonDialog.OnClickListener {


    @BindView(R.id.tv_title)
    TextView tv_title;
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_entrance_control);
    }

    @Override
    protected EntranceControlPresenter createPresenter() {
        return new EntranceControlPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_entrance_control;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_title.setText("门禁控制");
    }

    @OnClick({R.id.ll_back, R.id.rl_1, R.id.rl_2, R.id.rl_3, R.id.rl_4, R.id.rl_5, R.id.rl_6, R.id.rl_7,R.id.rl_8,R.id.rl_9,R.id.rl_10,R.id.rl_11})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.rl_1:
                currentIndex = 1;
                String title = "远程开门";
                String message = "确定要远程开门吗？";
                showDialog(title,message);
                break;
            case R.id.rl_2:
                currentIndex = 2;
                title = "门开关布防";
                message = "确定要门开关布防吗？";
                showDialog(title,message);
                break;
            case R.id.rl_3:
                currentIndex = 3;
                title = "门开关撤防";
                message = "确定要门开关撤防吗？";
                showDialog(title,message);
                break;
            case R.id.rl_4:
                currentIndex = 4;
                break;
            case R.id.rl_5:
                currentIndex = 5;
                break;
            case R.id.rl_6:
                currentIndex = 6;
                break;
            case R.id.rl_7:
                currentIndex = 7;
                break;
            case R.id.rl_8:
                currentIndex = 8;
                break;
            case R.id.rl_9:
                currentIndex = 9;
                break;
            case R.id.rl_10:
                currentIndex = 10;
                break;
            case R.id.rl_11:
                currentIndex = 11;
                break;
        }
    }

    private void showDialog(String title,String message){
        CommonDialog dialog = new CommonDialog(this);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setNegativeButton(R.string.dialog_negative);
        dialog.setPositiveButton(R.string.dialog_positive);
        dialog.setOnClickListener(this);
        dialog.show();
    }

    @Override
    public void onClick(Dialog dialog, int which) {
        switch (which){
            case Dialog.BUTTON_NEGATIVE:

                break;
            case Dialog.BUTTON_POSITIVE:
                String msg = "";
                switch (currentIndex){
                    case 1:
                        msg = "远程开门指令下发成功!";
                        break;
                    case 2:
                        msg = "门开关布防指令下发成功!";
                        break;
                    case 3:
                        msg = "门开关撤防指令下发成功!";
                        break;
                    case 4:
                        msg = "红外布防指令下发成功!";
                        break;
                    case 5:
                        msg = "红外撤防指令下发成功!";
                        break;
                    case 6:
                        msg = "关闭报警指令下发成功!";
                        break;
                    case 7:
                        msg = "关闭胁迫报警指令指令下发成功!";
                        break;
                    case 8:
                        msg = "关闭常开门指令下发成功!";
                        break;
                    case 9:
                        msg = "开启常开门指令下发成功!";
                        break;
                    case 10:
                        msg = "关闭常闭门指令下发成功!";
                        break;
                    case 11:
                        msg = "开启常闭门指令下发成功!";
                        break;
                }
                UIUtils.showToast(msg);
                break;
        }
    }
}
