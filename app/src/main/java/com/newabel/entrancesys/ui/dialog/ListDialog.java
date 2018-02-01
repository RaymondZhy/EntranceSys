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
import com.newabel.entrancesys.ui.adapter.ListDialogAdapter;
import com.newabel.entrancesys.ui.utils.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/19 0019.
 */

public class ListDialog implements BaseQuickAdapter.OnItemClickListener {

    private Context context;
    private RecyclerView rv_list;
    private Dialog mDialog;

    private int mGravity = Gravity.CENTER;
    private int mLocation;
    private List<String> mData;
    private ListDialogAdapter dialogAdapter;
    private OnClickListener onClickListener;

    public ListDialog(Context context){
        this.context = context;
        init();
    }

    void init(){
        mDialog = new Dialog(context, R.style.DialogTheme);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_list,null);
        mData = new ArrayList<>();
        rv_list = view.findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(context));
        dialogAdapter = new ListDialogAdapter(R.layout.item_list_dialog,mData,mGravity);
        dialogAdapter.setOnItemClickListener(this);
        rv_list.setAdapter(dialogAdapter);
        mDialog.setContentView(view);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
    }

    public void setGravity(int mGravity){
        this.mGravity = mGravity;
    }

    public void setLocation(int location){
        this.mLocation = location;
    }

    public void setItemEnable(int position){
        dialogAdapter.setItemEnable(position);
    }

    public void setItem(String[] items){
        if(items != null) {
            mData.addAll(Arrays.asList(items));
            dialogAdapter.notifyDataSetChanged();
        }
    }

    public void setItem(List<String> list) {
        if (list != null) {
            mData.addAll(list);
            dialogAdapter.notifyDataSetChanged();
        }
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public void show(){
        mDialog.show();
        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        params.width = UIUtils.getScreenWidth()*4/5;
        mDialog.getWindow().setAttributes(params);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if(onClickListener != null) {
            onClickListener.onClick(mDialog, position);
        }
        mDialog.dismiss();
    }

    public interface OnClickListener{
        void onClick(Dialog dialog, int which);
    }


}
