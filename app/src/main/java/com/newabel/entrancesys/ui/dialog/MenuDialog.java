package com.newabel.entrancesys.ui.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.PopupWindow;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.ui.adapter.MenuDialogAdapter;
import com.newabel.entrancesys.ui.utils.UIUtils;
import com.newabel.entrancesys.ui.widget.FloatingBarItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/11/10 0010.
 */

public class MenuDialog extends PopupWindow implements BaseQuickAdapter.OnItemClickListener {

    private Context mContext;
    private RecyclerView rv_list;
    private List<String> mList;
    private MenuDialogAdapter menuDialogAdapter;
    private OnClickListener mOnClickListener;
    public MenuDialog(@NonNull Context context) {
        super(context);

        this.mContext = context;
        mList = new ArrayList<>();

        View view = View.inflate(mContext,R.layout.dialog_menu,null);
        rv_list = view.findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
        menuDialogAdapter = new MenuDialogAdapter(R.layout.item_menu_dialog,mList);
        rv_list.addItemDecoration(getItemDecoration());
        rv_list.setAdapter(menuDialogAdapter);
        menuDialogAdapter.setOnItemClickListener(this);

        setContentView(view);

//        setCanceledOnTouchOutside(true);
//        setCancelable(true);

        setOutsideTouchable(true);
        setFocusable(true);

        setBackgroundDrawable(new ColorDrawable());

    }


    public void setItem(String[] items){
        mList.addAll(Arrays.asList(items));
        menuDialogAdapter.notifyDataSetChanged();
    }

    public void show(View view){ //x y 计算具体看布局title_bar
        int x = - getWidth() + view.getWidth() + UIUtils.dip2Px(6.5f);
        int y = UIUtils.dip2Px(8);
        showAsDropDown(view,x,y);
    }

    public void setOnClickListener(OnClickListener mOnClickListener){
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public void setWidth(int dp) {
        super.setWidth(UIUtils.dip2Px(dp));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter,View view, int position) {
        if(mOnClickListener != null) {
            mOnClickListener.onClick(this, position);
        }
        dismiss();
    }

    public RecyclerView.ItemDecoration getItemDecoration(){
        FloatingBarItemDecoration.Builder builder = new FloatingBarItemDecoration.Builder(mContext);
        RecyclerView.ItemDecoration decoration = builder.setMode(FloatingBarItemDecoration.MODE_HORIZONTAL_LINE)
                .setTitleHeight(UIUtils.dip2Px(1))
                .setBackground(ContextCompat.getColor(mContext,R.color.colorPrimary))
                .builder();
        return decoration;
    }

    public interface OnClickListener{
        void onClick(PopupWindow window, int which);
    }

}
