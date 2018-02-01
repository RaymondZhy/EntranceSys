package com.newabel.entrancesys.ui.fragment;


import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.service.entity.Column;
import com.newabel.entrancesys.service.entity.Content;
import com.newabel.entrancesys.service.entity.MessageEvent;
import com.newabel.entrancesys.service.presenter.BasePresenter;
import com.newabel.entrancesys.ui.adapter.HomeAdapter;
import com.newabel.entrancesys.ui.base.BaseFragment;
import com.newabel.entrancesys.ui.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Date: 2017/11/30 15:29
 * Description:
 */

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.srf)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.home_recyclerview)
    RecyclerView mRecyclerview;

    private HomeAdapter homeAdapter;

    @Override
    protected void initView(View rootView) {

        homeAdapter = new HomeAdapter(getContext());
        mRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(),R.color.colorPrimary));
        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerview.setAdapter(homeAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {


    }

    @Override
    protected void loadData() {
        List<Column> columns = new ArrayList<Column>();

        //首页轮播图
        Column column = new Column();

        List<Content> contents = new ArrayList<Content>();

        Content content = new Content();
        content.setImgurl("http://mpic.tiankong.com/2cd/5dd/2cd5dd03d2c8fb0aa43cb2bb7cedc4ff/640.jpg");
        contents.add(content);

        Content content1 = new Content();
        content1.setImgurl("http://mpic.tiankong.com/c40/8eb/c408eb7edab627145b9a5cf76664b357/640.jpg");
        contents.add(content1);

        Content content2 = new Content();
        content2.setImgurl("http://mpic.tiankong.com/2a4/8fd/2a48fd2159fcbfd99f6243c742046e95/640.jpg");
        contents.add(content2);

        column.setContents(contents);
        column.setStyle(Column.column_style_1);

        columns.add(column);

        //首页的功能区块
        Column column1 = new Column();

        List<Content> contents1 = new ArrayList<Content>();

        Content content10 = new Content();
        content10.setImgResourceId(R.drawable.icon_access_info);
        content10.setTitle("门禁资料");
        content10.setContentType(Constant.MODULE_ENTRANCE_DATA);
        contents1.add(content10);

        Content content11 = new Content();
        content11.setImgResourceId(R.drawable.icon_access_action);
        content11.setTitle("门禁事件");
        content11.setContentType(Constant.MODULE_ENTRANCE_EVENT);
        contents1.add(content11);

        Content content12 = new Content();
        content12.setImgResourceId(R.drawable.icon_access_control);
        content12.setTitle("门禁控制");
        content12.setContentType(Constant.MODULE_ENTRANCE_CONTROL);
        contents1.add(content12);

        Content content13 = new Content();
        content13.setImgResourceId(R.drawable.icon_access_district_control);
        content13.setTitle("区域管理");
        content13.setContentType(Constant.MODULE_ZONE_MANAGE);
        contents1.add(content13);

        Content content14 = new Content();
        content14.setImgResourceId(R.drawable.icon_equipment_info);
        content14.setTitle("设备资料");
        content14.setContentType(Constant.MODULE_DEVICE_DATA);
        contents1.add(content14);

        Content content15 = new Content();
        content15.setImgResourceId(R.drawable.icon_organiza_control);
        content15.setTitle("机构管理");
        content15.setContentType(Constant.MODULE_DEPARTMENT_MANAGE);
        contents1.add(content15);


        Content content16 = new Content();
        content16.setImgResourceId(R.drawable.icon_person_info);
        content16.setTitle("人员资料");
        content16.setContentType(Constant.MODULE_EMPLOYEE_MANAGE);
        contents1.add(content16);

        Content content17 = new Content();
        content17.setImgResourceId(R.drawable.icon_person_info);
        content17.setTitle("注册审核");
        content17.setContentType(Constant.MODULE_REGISTER_REVIEW);
        contents1.add(content17);

        Content content18 = new Content();
        content18.setImgResourceId(R.drawable.icon_work_order);
        content18.setTitle("我的工单");
        content18.setContentType(Constant.MODULE_WORK_ORDER);
        contents1.add(content18);

        column1.setContents(contents1);
        column1.setStyle(Column.column_style_2);

        columns.add(column1);

        //新闻区块
        Column column2 = new Column();

        List<Content> contents2 = new ArrayList<Content>();

        Content content20 = new Content();
        content20.setImgurl("https://gaopin-preview.bj.bcebos.com/133208874610.jpg");
        content20.setTitle("00自然环境得到改善，落霞与孤鹜齐飞，秋水共长天一色，真是人间仙境");
        content20.setTime("2017-12-06 15:23");
        contents2.add(content20);

        Content content21 = new Content();
        content21.setImgurl("https://gaopin-preview.bj.bcebos.com/133208874926.jpg");
        content21.setTitle("11自然环境得到改善，落霞与孤鹜齐飞，秋水共长天一色，真是人间仙境");
        content21.setTime("2017-12-06 15:23");
        contents2.add(content21);

        Content content22 = new Content();
        content22.setImgurl("https://gaopin-preview.bj.bcebos.com/133208875528.jpg");
        content22.setTitle("22自然环境得到改善，落霞与孤鹜齐飞，秋水共长天一色，真是人间仙境");
        content22.setTime("2017-12-06 15:23");
        contents2.add(content22);

        Content content23 = new Content();
        content23.setImgurl("https://gaopin-preview.bj.bcebos.com/134207258129.jpg");
        content23.setTitle("33自然环境得到改善，落霞与孤鹜齐飞，秋水共长天一色，真是人间仙境");
        content23.setTime("2017-12-06 15:23");
        contents2.add(content23);

        Content content24 = new Content();
        content24.setImgurl("https://gaopin-preview.bj.bcebos.com/133208876152.jpg");
        content24.setTitle("44自然环境得到改善，落霞与孤鹜齐飞，秋水共长天一色，真是人间仙境");
        content24.setTime("2017-12-06 15:23");
        contents2.add(content24);

        column2.setContents(contents2);
        column2.setStyle(Column.column_style_3);
        columns.add(column2);

        homeAdapter.setColumns(columns);
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onRefresh() {
        UIUtils.showToast("刷新首页数据...");
        UIUtils.postTaskDelay(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isFragmentVisible) {
        if (isFragmentVisible) {
            //不可见-->可见,发一个消息,轮播图开始轮播
            EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTION_START_BANNER_TURN));
        } else {
            //可见-->不可见,发一个消息,轮播图停止轮播
            EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTION_STOP_BANNER_TURN));
        }
    }
}
