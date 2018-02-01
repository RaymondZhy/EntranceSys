package com.newabel.entrancesys.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.Book;
import com.newabel.entrancesys.service.presenter.BasePresenter;
import com.newabel.entrancesys.service.presenter.BookPresenter;
import com.newabel.entrancesys.ui.base.BaseFragment;
import com.newabel.entrancesys.ui.iview.BookView;
import com.newabel.entrancesys.ui.utils.LogUtil;

/**
 * Date: 2017/11/30 15:30
 * Description:
 */

public class InformationOldFragment extends BaseFragment implements BookView {

//    @BindView(R.id.tv_videocontent)
//    TextView videoContent;

    private BookPresenter bookPresenter = new BookPresenter(this);


    @Override
    protected void loadData() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_information;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("InformationFragment", "--------------------InformationFragment---------------create");

        bookPresenter.attachView(this);
//        bookPresenter.getSearchBooks("西游记", null, 0, 1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        LogUtil.e("HomeFragment", "--------------------InformationFragment---------------onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e("InformationFragment", "--------------------InformationFragment---------------onDestroy");
    }

    @Override
    public void onSuccess(Book book) {
//        videoContent.setText(book.toString());
    }

    @Override
    public void onError(String result) {
//        videoContent.setText("result:" + result);
    }
}
