package com.newabel.entrancesys.ui.activity;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.AreaAccessInfoEntity;
import com.newabel.entrancesys.service.presenter.BasePresenter;
import com.newabel.entrancesys.ui.adapter.AccessInfoAdapter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.utils.UIUtils;
import com.newabel.entrancesys.ui.widget.ItemDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 门禁资料
 */
public class AccessInfoActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_add)
    ImageView tvAdd;

    @BindView(R.id.et_input)
    EditText etInput;

    @BindView(R.id.input_notice)
    LinearLayout inputNotice;

    @BindView(R.id.srf_access_info_area)
    SwipeRefreshLayout srf;

    @BindView(R.id.recyclerview_access_info_area)
    RecyclerView recyclerView;

    private AccessInfoAdapter accessInfoAdapter;


    private List<AreaAccessInfoEntity> areas = new ArrayList<AreaAccessInfoEntity>();

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_access_info;
    }

    @Override
    protected void initView() {
        tvTitle.setText("门禁资料");
        tvAdd.setVisibility(View.VISIBLE);

        accessInfoAdapter = new AccessInfoAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new ItemDivider(this, R.drawable.item_divider));
        recyclerView.setAdapter(accessInfoAdapter);

    }

    @Override
    protected void initData() {
        loadData();
    }


    @Override
    protected void initListener() {
//        hideInputKeyboard();

        etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    handlerSearch(etInput.getText().toString());
                    UIUtils.showToast("开始搜索");
                    return true;
                }
                return false;
            }
        });

        srf.setOnRefreshListener(this);

        accessInfoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                //模拟发起网络请求
                UIUtils.postTaskDelay(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 8; i++) {
                            areas.add(new AreaAccessInfoEntity("独立团" + i));
                        }
                        accessInfoAdapter.loadMoreComplete();
//                        accessInfoAdapter.loadMoreEnd();
                    }
                }, 3000);

            }
        }, recyclerView);


    }


    private void loadData() {
        for (int i = 0; i < 8; i++) {
            areas.add(new AreaAccessInfoEntity("独立团" + i));
        }
        accessInfoAdapter.setNewData(areas);
    }


    @OnClick({R.id.ll_back, R.id.iv_add, R.id.input_notice})
    void onIconClicked(View view) {

        switch (view.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.iv_add:
                UIUtils.showToast("添加门禁资料");
                break;

            case R.id.input_notice:
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
                inputNotice.setVisibility(View.GONE);
                etInput.setCursorVisible(true);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        inputNotice.setVisibility(View.VISIBLE);
        etInput.setCursorVisible(false);
    }

    private void handlerSearch(String keyword) {
        try {
            /** 先隐藏键盘 **/
            hideInputKeyboard();

            if (TextUtils.isEmpty(keyword)) {
                UIUtils.showToast("输入内容不能为空");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hideInputKeyboard() {
        ((InputMethodManager) AccessInfoActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(AccessInfoActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onRefresh() {
        UIUtils.showToast("刷新数据");
        UIUtils.postTaskDelay(new Runnable() {
            @Override
            public void run() {
                srf.setRefreshing(false);
                areas.clear();
                for (int i = 0; i < 8; i++) {
                    areas.add(new AreaAccessInfoEntity("独立团" + i));
                }
                accessInfoAdapter.setNewData(areas);

            }
        }, 2000);


    }
}
