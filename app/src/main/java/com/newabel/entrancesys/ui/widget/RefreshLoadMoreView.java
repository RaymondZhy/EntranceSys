package com.newabel.entrancesys.ui.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.newabel.entrancesys.R;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/11 0011.
 */

public class RefreshLoadMoreView extends LoadMoreView {
    
    @Override
    public int getLayoutId() {
        return R.layout.view_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.ll_loading;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.ll_load_failed;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.ll_load_complete;
    }
}
