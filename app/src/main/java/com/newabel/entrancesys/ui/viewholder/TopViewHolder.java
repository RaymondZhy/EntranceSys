package com.newabel.entrancesys.ui.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.Content;
import com.newabel.entrancesys.service.entity.MessageEvent;
import com.newabel.entrancesys.ui.widget.RatioImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 2017/12/5 16:20
 * Description:
 */

public class TopViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_View)
    RelativeLayout itemView;

    @BindView(R.id.convenientBanner)
    ConvenientBanner banner;

    @BindView(R.id.bottom_content)
    LinearLayout bottomContent;

    public TopViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        EventBus.getDefault().register(this);
    }


    public void setUp(List<Content> contents) {
        if (contents != null && contents.size() > 0) {
            itemView.setVisibility(View.VISIBLE);
            if (contents.size() > 1) {
                banner.setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
                banner.setCanLoop(true);
                banner.startTurning(3000);
            } else {
                banner.setCanLoop(false);
            }
            banner.setPages(cbViewHolderCreator, contents);
            banner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

        } else {
            //没有数据时隐藏布局
            itemView.setVisibility(View.GONE);
        }
    }

    private CBViewHolderCreator<TopItemHolder> cbViewHolderCreator = new CBViewHolderCreator<TopItemHolder>() {
        @Override
        public TopItemHolder createHolder() {
            return new TopItemHolder();
        }
    };

    class TopItemHolder implements Holder<Content> {
        private RatioImageView image;

        @Override
        public View createView(Context context) {
            image = new RatioImageView(context);
            image.setHorizontalWeight(16);
            image.setVerticalWeight(9);
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Content content = (Content) v.getTag();
//                    OpenHandler.openContent(context, content);
                }
            });
            return image;
        }

        @Override
        public void UpdateUI(Context context, int position, Content data) {
//            image.setTag(data);
//            LogUtil.e("TopViewHolder", "-----------" + data.getImgurl());
//            imageLoader.displayImage(data.getImgUrlBig(), image, options);

            Glide.with(context).load(data.getImgurl()).into(image);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void startOrStopTurn(MessageEvent messageEvent) {
        if (messageEvent.getAction() == MessageEvent.ACTION_START_BANNER_TURN) {
//            LogUtil.e("TopViewHolder","----------------------ACTION_START_BANNER_TURN");
            if (banner.isTurning()) return;
            banner.startTurning(3000);
        } else if (messageEvent.getAction() == MessageEvent.ACTION_STOP_BANNER_TURN) {
//            LogUtil.e("TopViewHolder","----------------------ACTION_STOP_BANNER_TURN");
            if (!banner.isTurning()) return;
            banner.stopTurning();
        }

    }

}
