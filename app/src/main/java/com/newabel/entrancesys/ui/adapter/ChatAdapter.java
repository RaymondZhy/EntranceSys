package com.newabel.entrancesys.ui.adapter;

import android.util.SparseArray;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newabel.entrancesys.R;

import java.util.List;
import java.util.Map;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/11/29 0029.
 */

public class ChatAdapter extends BaseQuickAdapter<Map<String,Object>,BaseViewHolder> {
    private final int LAYOUT_LEFT = 1;
    private final int LAYOUT_RIGHT = 2;
    private SparseArray<Integer> layouts;

    public ChatAdapter(List<Map<String, Object>> data) {
        super(data);
        layouts = new SparseArray<>();
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if(mData.get(position).get("publisher").toString().equalsIgnoreCase("2")){
            type = LAYOUT_RIGHT;
        }else {
            type = LAYOUT_LEFT;
        }
        addItemType(type);
        return type;
    }

    private void addItemType(int type) {
        switch (type){
            case LAYOUT_LEFT:
                layouts.put(LAYOUT_LEFT, R.layout.item_chat_left);
                break;
            case LAYOUT_RIGHT:
                layouts.put(LAYOUT_RIGHT, R.layout.item_chat_right);
                break;
        }
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent,layouts.get(viewType));
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, Object> item) {
        helper.setText(R.id.tv_content,item.get("body").toString());
    }
}
