package com.newabel.entrancesys.ui.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.Content;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 2017/12/6 14:43
 * Description:
 */

public class HomeNewsItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.news_item_img)
    ImageView newsItemImg;
    @BindView(R.id.news_item_title)
    TextView newsItemTitle;
    @BindView(R.id.news_item_time)
    TextView newsItemTime;

    public HomeNewsItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setUp(Context context, Content content) {
        Glide.with(context).load(content.getImgurl()).into(newsItemImg);
        newsItemTitle.setText(content.getTitle());
        newsItemTime.setText(content.getTime());
    }

}
