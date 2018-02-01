package com.newabel.entrancesys.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.AreaAccessInfoEntity;

import java.util.List;

/**
 * Date: 2017/12/7 15:24
 * Description:门禁资料区域级别的适配器
 */

public class AccessInfoAdapter extends com.chad.library.adapter.base.BaseQuickAdapter<AreaAccessInfoEntity, BaseViewHolder> {

    public AccessInfoAdapter(@Nullable List<AreaAccessInfoEntity> data) {
        super(R.layout.access_info_area_item_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AreaAccessInfoEntity item) {
        helper.setText(R.id.acccess_info_area_name, item.getAreaName());
        helper.itemView.setOnClickListener(onClickListener);
    }


    //    private Context context;
//
//    private LayoutInflater layoutInflater;
//
//    private List<AreaAccessInfoEntity> areas;
//
//    public void setAreas(List<AreaAccessInfoEntity> areas) {
//        this.areas = areas;
//    }
//
//    public AccessInfoAdapter(Context context) {
//        this.context = context;
//        layoutInflater = LayoutInflater.from(context);
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        RecyclerView.ViewHolder holder = new AccessInfoAreaViewHolder(layoutInflater.inflate(R.layout.access_info_area_item_layout, null));
//        holder.itemView.setOnClickListener(onClickListener);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        AccessInfoAreaViewHolder holder1 = (AccessInfoAreaViewHolder) holder;
//        holder1.setUp(context, areas.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        if (areas != null) {
//            return areas.size();
//        }
//        return 0;
//    }
//
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

}
