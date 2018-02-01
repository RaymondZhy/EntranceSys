package com.newabel.entrancesys.ui.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;

import com.newabel.entrancesys.R;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/7 0007.
 */

public class VerifyCodeButton extends android.support.v7.widget.AppCompatButton {
    private CountDownTimer countDownTimer;

    public final static int STATUS_COUNTING = 0;
    public final static int STATUS_COUNT_STOP = 1;
    private Context context;

    public VerifyCodeButton(Context context) {
        super(context);
        this.context = context;
    }

    public VerifyCodeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public VerifyCodeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setButtonStatus(int status) {
        switch (status) {
            case STATUS_COUNTING:
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                countDownTimer = new CountDownTimer(60 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        setText(getResources().getString(R.string.verify_code_str_1, millisUntilFinished / 1000));
                    }

                    @Override
                    public void onFinish() {
                        setText(getResources().getString(R.string.verify_code_str_2));
                        setEnabled(true);
                    }
                };

                countDownTimer.start();
                setEnabled(false);
                break;
            case STATUS_COUNT_STOP:
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                setText(getResources().getString(R.string.verify_code_str_2));
                setEnabled(true);
                break;
        }
    }

}
