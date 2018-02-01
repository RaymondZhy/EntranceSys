package com.newabel.entrancesys.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/8 0008.
 */

public class SexDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private RelativeLayout rl_male;
    private CheckBox cb_male;
    private RelativeLayout rl_female;
    private CheckBox cb_female;
    private View view;

    public SexDialog(@NonNull Context context, View view) {
        super(context, R.style.DialogTheme);
        this.context = context;
        this.view = view;
        initView();
        initListener();
    }

    public SexDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        initView();
        initListener();
    }

    private void initView() {
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_sex, null);
        setContentView(v);

        rl_male = findViewById(R.id.rl_male);
        cb_male = findViewById(R.id.cb_male);
        rl_female = findViewById(R.id.rl_female);
        cb_female = findViewById(R.id.cb_female);


        String sex = getText();
        if (sex != null) {
            if (TextUtils.equals(sex, context.getResources().getString(R.string.male))) {
                cb_male.setChecked(true);
            } else if (TextUtils.equals(sex, context.getResources().getString(R.string.female))) {
                cb_female.setChecked(true);
            }
        }
    }

    private void initListener() {
        rl_male.setOnClickListener(this);
        rl_female.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_male:
                cb_male.setChecked(true);
                cb_female.setChecked(false);
                setText(context.getText(R.string.male).toString());
                break;
            case R.id.rl_female:
                cb_male.setChecked(false);
                cb_female.setChecked(true);
                setText(context.getText(R.string.female).toString());
                break;
        }
        dismiss();
    }

    private String getText() {
        if (view != null) {
            if (view instanceof EditText) {
                return ((EditText) view).getText().toString();
            } else if (view instanceof TextView) {
                return ((TextView) view).getText().toString();
            }else if (view instanceof Button) {
                return ((Button) view).getText().toString();
            }
        }
        return "";
    }

    private void setText(String str) {
        if (view != null) {
            if (view instanceof EditText) {
                ((EditText) view).setText(str);
            } else if (view instanceof TextView) {
                 ((TextView) view).setText(str);
            }else if (view instanceof Button) {
                 ((Button) view).setText(str);
            }
        }
    }
}
