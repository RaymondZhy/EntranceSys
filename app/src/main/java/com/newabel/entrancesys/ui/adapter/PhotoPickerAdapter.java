package com.newabel.entrancesys.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.service.helper.Glide.GlideHelper;
import com.newabel.entrancesys.ui.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/11/20 0020.
 */

public class PhotoPickerAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    private List<String> selectPhotos;
    private Button mButton;
    private int photoCount;
    private TextView mTextView;
    private int size = 0;
    public PhotoPickerAdapter(int layoutResId, List<String> data, List<String> selectPhotos, Button mButton, TextView mTextView, int photoCount) {
        super(layoutResId, data);
        if(selectPhotos == null){
            selectPhotos = new ArrayList<>();
        }
        this.selectPhotos = selectPhotos;
        this.mButton = mButton;
        this.mTextView = mTextView;
        this.photoCount = photoCount;
    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        size = size == 0 ? (UIUtils.getScreenWidth()-UIUtils.dip2Px(6))/3 : size;
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(size,size);
        helper.getView(R.id.iv_photo).setLayoutParams(param);

        if(TextUtils.equals(Constant.FLAG_POSITION_OCCUPY,item)){
            ((ImageView) helper.getView(R.id.iv_photo)).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            ((ImageView) helper.getView(R.id.iv_photo)).setImageResource(R.mipmap.ic_take_photo);
            helper.getView(R.id.cb_select).setVisibility(View.INVISIBLE);

        }else {
            ((ImageView) helper.getView(R.id.iv_photo)).setScaleType(ImageView.ScaleType.CENTER_CROP);
            GlideHelper.showImage(mContext, item, (ImageView) helper.getView(R.id.iv_photo));
            helper.getView(R.id.cb_select).setVisibility(View.VISIBLE);
        }
        helper.setChecked(R.id.cb_select,selectPhotos.contains(item));

        helper.getView(R.id.cb_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()){
                    if(selectPhotos.size()>=photoCount) {
                        UIUtils.showToast(mContext.getResources().getString(R.string.photo_picker_activity_str_5,photoCount));
                        notifyDataSetChanged();
                        return;
                    }
                    selectPhotos.add(mData.get(helper.getAdapterPosition()));
                }else{
                    selectPhotos.remove(mData.get(helper.getAdapterPosition()));

                }
                updateUi();
                notifyDataSetChanged();
            }
        });
    }

    public void updateUi(){
        if(selectPhotos.size() == 0){
            mButton.setText(mContext.getResources().getString(R.string.photo_picker_activity_str_2));
            mTextView.setText(mContext.getResources().getString(R.string.photo_picker_activity_str_7));
        }else{
            mButton.setText(mContext.getResources().getString(R.string.photo_picker_activity_str_6,selectPhotos.size(),photoCount));
            mTextView.setText(mContext.getResources().getString(R.string.photo_picker_activity_str_8,selectPhotos.size()));
        }
    }
}
