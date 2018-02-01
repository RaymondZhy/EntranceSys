package com.newabel.entrancesys.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;


/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/11/16 0016.
 */

public class CommonDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private TextView tv_title;
    private Button btn_negative;
    private Button btn_positive;
    private LinearLayout ll_buttons;
    private EditText et_message;
    private OnClickListener mOnClickListener;
    private View v_line;


    public CommonDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;

        initViews();
        initListeners();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCancelable(true);
        setCanceledOnTouchOutside(true);

    }

    private void initViews() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_common,null);
        setContentView(view);
        tv_title = findViewById(R.id.tv_title);
        et_message = findViewById(R.id.et_message);
        btn_negative = findViewById(R.id.btn_negative);
        btn_positive = findViewById(R.id.btn_positive);
        ll_buttons = findViewById(R.id.ll_buttons);
        v_line = findViewById(R.id.v_line);
        tv_title.setVisibility(View.GONE);
        et_message.setEnabled(false);

    }


    public void setTitle(int titleId) {
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(mContext.getResources().getText(titleId));
    }

    public void setTitle(String title){
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(title);
    }

    public void setMessage(int messageId){
        et_message.setText(mContext.getResources().getString(messageId));
    }

    public void setMessage(String message){
        et_message.setText(message);
    }

    public void setMessageEditable(boolean isEditable){
        et_message.setEnabled(isEditable);
    }

    public void setPositiveButton(int textId){
        btn_positive.setText(mContext.getResources().getText(textId));
        btn_positive.setTag(true);
    }

    public void setNegativeButton(int textId){
        btn_negative.setText(mContext.getResources().getText(textId));
        btn_negative.setTag(true);
    }

    public void setPositiveButton(String text){
        btn_positive.setText(text);
        btn_positive.setTag(true);
    }

    public void setNegativeButton(String text){
        btn_negative.setText(text);
        btn_negative.setTag(true);
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.mOnClickListener = onClickListener;
    }

    private void initListeners() {
        btn_positive.setOnClickListener(this);
        btn_negative.setOnClickListener(this);
    }
    public void show(){
        if(btn_negative.getTag() == null){
            btn_negative.setVisibility(View.GONE);
            btn_positive.setBackgroundResource(R.drawable.selector_dialog_common_single_button);
            v_line.setVisibility(View.GONE);
        }

        if(btn_positive.getTag() == null){
            btn_positive.setVisibility(View.GONE);
            btn_negative.setBackgroundResource(R.drawable.selector_dialog_common_single_button);
            v_line.setVisibility(View.GONE);
        }
        super.show();
//        WindowManager.LayoutParams param = getWindow().getAttributes();
//        param.width = DpUtils.getScreenWidth(mContext) * 4/5;
//        getWindow().setAttributes(param);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_positive:
                if(mOnClickListener != null) {
                    mOnClickListener.onClick(this, Dialog.BUTTON_POSITIVE);
                }
                break;
            case R.id.btn_negative:
                if(mOnClickListener != null) {
                    mOnClickListener.onClick(this, Dialog.BUTTON_NEGATIVE);
                }
                break;
        }
        dismiss();
    }

    public interface OnClickListener{
        void onClick(Dialog dialog, int which);
    }
}
