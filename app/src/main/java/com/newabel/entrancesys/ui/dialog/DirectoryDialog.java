package com.newabel.entrancesys.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.PhotoDirectory;
import com.newabel.entrancesys.ui.adapter.DirectoryDialogAdapter;
import com.newabel.entrancesys.ui.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/11/21 0021.
 */

public class DirectoryDialog extends Dialog implements BaseQuickAdapter.OnItemClickListener {

    private Context context;
    private RecyclerView rv_list;
    private List<PhotoDirectory> list;
    private DirectoryDialogAdapter adapter;
    private OnClickListener onClickListener;
    public DirectoryDialog(@NonNull Context context) {
        super(context,R.style.DialogTheme);
        this.context = context;

        setWindowAnimation();
        initViews();
        initListeners();
    }

    private void setWindowAnimation() {
        getWindow().setWindowAnimations(R.style.Dialog_Directory);
    }

    private void initViews() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_directory,null);
        setContentView(view);
        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(context));
        if(list == null){
            list = new ArrayList<>();
        }
        adapter = new DirectoryDialogAdapter(R.layout.item_dialog_directory,list);
        rv_list.setAdapter(adapter);

        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    private void initListeners() {
        adapter.setOnItemClickListener(this);
    }

    public void setAdapterIndex(int index){
        adapter.index = index;
        adapter.notifyDataSetChanged();
    }

    public void setList(List<PhotoDirectory> list){
        if(list == null){
            list = new ArrayList<>();
        }
        this.list.clear();
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void show(View view){
        super.show();

        WindowManager.LayoutParams param = getWindow().getAttributes();
        param.width = WindowManager.LayoutParams.MATCH_PARENT;
        param.y = view.getHeight();
        int maxHeight = UIUtils.dip2Px(5*81);
        int height = UIUtils.dip2Px(list.size()*81);
        param.height =  height > maxHeight ? maxHeight : height;
        param.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(param);
    }


    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter,View view, int position) {
        if(onClickListener != null) {
            onClickListener.onClick(this, position);
        }
        dismiss();
    }

    public interface OnClickListener{
        void onClick(Dialog dialog, int which);
    }
}
