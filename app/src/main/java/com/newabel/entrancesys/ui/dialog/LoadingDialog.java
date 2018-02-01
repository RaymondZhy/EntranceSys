package com.newabel.entrancesys.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.newabel.entrancesys.R;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/13 0013.
 */

public class LoadingDialog {

    private static Dialog dialog;

    private LoadingDialog() {

    }

    private static void init(Context context) {
        dialog = new Dialog(context, R.style.DialogTheme);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
    }

    public static void show(Context context) {
        if (dialog != null) {
            dialog.dismiss();
        }
        init(context);
        dialog.show();
    }

    public static void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
