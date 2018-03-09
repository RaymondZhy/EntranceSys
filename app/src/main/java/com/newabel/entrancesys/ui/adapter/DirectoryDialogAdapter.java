package com.newabel.entrancesys.ui.adapter;

import android.widget.ImageView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.PhotoDirectory;
import com.newabel.entrancesys.service.helper.Glide.GlideHelper;

import java.util.List;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/11/21 0021.
 */

public class DirectoryDialogAdapter extends BaseQuickAdapter<PhotoDirectory,BaseViewHolder> {

    public int index = 0;

    public DirectoryDialogAdapter(int layoutResId, List<PhotoDirectory> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PhotoDirectory item) {
        GlideHelper.showImage(mContext,item.getCoverPath(),(ImageView)helper.getView(R.id.iv_dir_cover));
        helper.setText(R.id.tv_dir_name,item.getName());
        helper.setText(R.id.tv_dir_count,mContext.getResources().getString(R.string.photo_picker_activity_str_4,
                mData.get(helper.getAdapterPosition()).getPhotos().size()));
        helper.setVisible(R.id.cb_select,helper.getAdapterPosition() == index);
    }
}
