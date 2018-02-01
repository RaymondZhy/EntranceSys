package com.newabel.entrancesys.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.ui.adapter.BottomDialogAdapter;
import com.newabel.entrancesys.ui.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/19 0019.
 */

public class BottomDialog implements BaseQuickAdapter.OnItemClickListener {

    private Context context;
    private RecyclerView rv_list;
    private Dialog mDialog;

    private List<Map<String, Object>> mData;
    private BottomDialogAdapter dialogAdapter;
    private OnClickListener onClickListener;

    public BottomDialog(Context context) {
        this.context = context;
    }

    public BottomDialog(Context context, List<Map<String, Object>> mData) {
        this.context = context;
        this.mData = mData == null ? new ArrayList<Map<String, Object>>() : mData;

        init();
    }

    void init() {
        mDialog = new Dialog(context, R.style.DialogTheme);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_bottom, null);
        rv_list = view.findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(context));
        dialogAdapter = new BottomDialogAdapter(R.layout.item_dialog_bottom_1, mData);
        rv_list.setAdapter(dialogAdapter);
        dialogAdapter.setOnItemClickListener(this);
        mDialog.setContentView(view);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.getWindow().setWindowAnimations(R.style.Dialog_Bottom);
    }

    public void setData(List<Map<String, Object>> mData) {
        this.mData = mData == null ? new ArrayList<Map<String, Object>>() : mData;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void show() {
        mDialog.show();
        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        int itemHeight = UIUtils.dip2Px(51);
        int maxHeight = 8 * itemHeight;
        int actualHeight = mData.size() * itemHeight;
        params.height = actualHeight < maxHeight ? actualHeight : maxHeight;
        mDialog.getWindow().setAttributes(params);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (onClickListener != null) {
            onClickListener.onClick(mDialog, position);
        }
        mDialog.dismiss();
    }

    public interface OnClickListener {
        void onClick(Dialog dialog, int which);
    }
}
