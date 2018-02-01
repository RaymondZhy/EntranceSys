package com.newabel.entrancesys.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.service.entity.MessageEvent;
import com.newabel.entrancesys.service.presenter.ModulePresenter;
import com.newabel.entrancesys.ui.adapter.ModuleAdapter;
import com.newabel.entrancesys.ui.adapter.NavBarAdapter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.dialog.CommonDialog;
import com.newabel.entrancesys.ui.dialog.ListDialog;
import com.newabel.entrancesys.ui.iview.ModuleView;
import com.newabel.entrancesys.ui.utils.LogUtil;
import com.newabel.entrancesys.ui.utils.UIUtils;
import com.newabel.entrancesys.ui.widget.NavBarView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class ModuleActivity extends BaseActivity<ModulePresenter> implements ModuleView, NavBarAdapter.OnNavItemWatcher<Map<String, Object>>, BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, BaseQuickAdapter.OnItemLongClickListener {

    private final String TAG = ModuleActivity.class.getSimpleName();
    private final int REQUEST_CODE_SEARCH = 101;

    @BindView(R.id.iv_add)
    ImageView iv_add;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.ll_search)
    LinearLayout ll_search;

    @BindView(R.id.tv_keyword)
    TextView tv_keyword;

    @BindView(R.id.rv_nav)
    RecyclerView rv_nav;

    @BindView(R.id.srf_layout)
    SwipeRefreshLayout srf_layout;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    private int MODULE_CODE;
    private String mHint;
    private List<Map<String, Object>> mBarList;
    private NavBarAdapter<Map<String, Object>> navBarAdapter;
    private List<Map<String, Object>> mContentList;
    private ModuleAdapter moduleAdapter;
    private View mEmptyView;
    private View mNoNetWorkView;
    private View mErrorView;
    private View mBlankView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_func);
        registEventBus(this);
    }

    @Override
    protected ModulePresenter createPresenter() {
        return new ModulePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_module;
    }

    @Override
    protected void initView() {
        super.initView();
        LinearLayoutManager mLineLayout = new LinearLayoutManager(this);
        mLineLayout.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_nav.setLayoutManager(mLineLayout);

        rv_list.setLayoutManager(new LinearLayoutManager(this));

        mBlankView = getLayoutInflater().inflate(R.layout.view_blank, (ViewGroup) rv_list.getParent(), false);
        mEmptyView = getLayoutInflater().inflate(R.layout.view_load_empty, (ViewGroup) rv_list.getParent(), false);
        mErrorView = getLayoutInflater().inflate(R.layout.view_load_error, (ViewGroup) rv_list.getParent(), false);
        mNoNetWorkView = getLayoutInflater().inflate(R.layout.view_load_no_network, (ViewGroup) rv_list.getParent(), false);

    }

    @Override
    protected void initData() {
        super.initData();
        MODULE_CODE = this.getIntent().getIntExtra("MODULE_CODE", 100);

        mBarList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        mBarList.add(map);

        srf_layout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary));

        mContentList = new ArrayList<>();
        moduleAdapter = new ModuleAdapter(mContentList, MODULE_CODE);
        rv_list.setAdapter(moduleAdapter);

        switch (MODULE_CODE) {
            case Constant.MODULE_ENTRANCE_DATA:
                tv_title.setText(getString(R.string.module_title_entrance_data));
                mHint = getString(R.string.module_hint_entrance_data);
                map.put("PlaceName", getString(R.string.module_str_3));
                map.put("PlaceID", "");
                break;
            case Constant.MODULE_ENTRANCE_EVENT:
                tv_title.setText(getString(R.string.module_title_entrance_event));
                mHint = getString(R.string.module_hint_entrance_event);
                map.put("PlaceName", getString(R.string.module_str_3));
                map.put("PlaceID", "");
                break;
            case Constant.MODULE_ENTRANCE_CONTROL:
                tv_title.setText(getString(R.string.module_title_entrance_control));
                mHint = getString(R.string.module_hint_entrance_control);
                map.put("PlaceName", getString(R.string.module_str_3));
                map.put("PlaceID", "");
                break;
            case Constant.MODULE_ZONE_MANAGE:
                tv_title.setText(getString(R.string.module_title_zone_manage));
                mHint = getString(R.string.module_hint_zone_manage);
                map.put("PlaceName", getString(R.string.module_str_4));
                map.put("PlaceID", "");
                break;
            case Constant.MODULE_DEVICE_DATA:
                tv_title.setText(getString(R.string.module_title_device_data));
                mHint = getString(R.string.module_hint_device_data);
                map.put("PlaceName", getString(R.string.module_str_4));
                map.put("PlaceID", "");
                break;
            case Constant.MODULE_DEPARTMENT_MANAGE:
                tv_title.setText(getString(R.string.module_title_department_manage));
                iv_add.setVisibility(View.VISIBLE);
                mHint = getString(R.string.module_hint_department_manage);
                map.put("DeptName", getString(R.string.module_str_9));
                map.put("DeptID", "");
                break;
            case Constant.MODULE_EMPLOYEE_MANAGE:
                tv_title.setText(getString(R.string.module_title_employee_manage));
                mHint = getString(R.string.module_hint_employee_manage);
                map.put("DeptName", getString(R.string.module_str_10));
                map.put("DeptID", "");
                break;
        }
        tv_keyword.setHint(mHint);
        navBarAdapter = new NavBarAdapter<>(this, mBarList, this);
        rv_nav.setAdapter(navBarAdapter);

        setButtonVisible(MODULE_CODE);

        mPresenter.getData("0", "", MODULE_CODE);
    }

    @Override
    protected void initListener() {
        super.initListener();
        moduleAdapter.setOnItemClickListener(this);
        moduleAdapter.setOnItemLongClickListener(this);
        srf_layout.setOnRefreshListener(this);
        mEmptyView.setOnClickListener(this);
        mErrorView.setOnClickListener(this);
        mNoNetWorkView.setOnClickListener(this);
        ll_back.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        ll_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.iv_add:
                intent2Activity(MODULE_CODE);
                break;
            case R.id.ll_search:
                Intent intent = new Intent(this, SearchRecordActivity.class);
                intent.putExtra("mark", String.valueOf(MODULE_CODE));
                intent.putExtra("hint", mHint);
                startActivityForResult(intent, REQUEST_CODE_SEARCH);
                break;
            default:
                clearList();
                switch (MODULE_CODE) {
                    case Constant.MODULE_ENTRANCE_DATA:
                    case Constant.MODULE_ENTRANCE_EVENT:
                    case Constant.MODULE_ENTRANCE_CONTROL:
                    case Constant.MODULE_ZONE_MANAGE:
                    case Constant.MODULE_DEVICE_DATA: {
                        String placeID = mBarList.get(mBarList.size() - 1).get("PlaceID").toString();
                        if (mBarList.size() == 1) {
                            mPresenter.getData("0", "", MODULE_CODE);
                        } else {
                            mPresenter.getData("1", placeID, MODULE_CODE);
                        }
                        break;
                    }
                    case Constant.MODULE_DEPARTMENT_MANAGE:
                        String deptId = mBarList.get(mBarList.size() - 1).get("DeptID").toString();
                        if (mBarList.size() == 1) {
                            mPresenter.getData("0", "", MODULE_CODE);
                        } else {
                            mPresenter.getData("1", deptId, MODULE_CODE);
                        }
                        break;
                    case Constant.MODULE_EMPLOYEE_MANAGE:

                        break;
                }
                break;
        }
    }

    private void intent2Activity(int code) {
        Intent intent = new Intent();
        switch (MODULE_CODE) {
            case Constant.MODULE_ENTRANCE_DATA:
                intent.putExtra("isEditMode", true);
                intent.putExtra("mData", new Gson().toJson(mBarList.get(mBarList.size() - 1)));
                intent.setClass(this, DoorDataAddActivity.class);
                break;
            case Constant.MODULE_ENTRANCE_EVENT:

                break;
            case Constant.MODULE_ENTRANCE_CONTROL:
                intent.putExtra("isEditMode", true);
                intent.setClass(this, EntranceDataActivity.class);
                break;
            case Constant.MODULE_ZONE_MANAGE:
                intent.putExtra("isEditMode", true);
                intent.putExtra("mData", new Gson().toJson(mBarList.get(mBarList.size() - 1)));
                intent.setClass(this, ZoneDetailActivity.class);
                break;
            case Constant.MODULE_DEVICE_DATA:
                intent.putExtra("isEditMode", true);
                intent.putExtra("mData", new Gson().toJson(mBarList.get(mBarList.size() - 1)));
                intent.setClass(this, AddNewDeviceActivity.class);
                break;
            case Constant.MODULE_DEPARTMENT_MANAGE:
                intent.putExtra("mData", new Gson().toJson(mBarList.get(mBarList.size() - 1)));
                intent.setClass(this, AddNewDepartctivity.class);
                break;
            case Constant.MODULE_EMPLOYEE_MANAGE:
                intent.putExtra("isEditMode", false);
                intent.putExtra("mData", new Gson().toJson(mBarList.get(mBarList.size() - 1)));
                intent.setClass(this, AddNewEmployeeActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SEARCH:
                    if (data != null) {
                        String keyword = data.getStringExtra("keyword");
                        if (keyword != null) {
                            ll_search.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                            tv_keyword.setText(keyword);
                            switch (MODULE_CODE) {
                                case Constant.MODULE_ENTRANCE_DATA:
                                case Constant.MODULE_ENTRANCE_EVENT:
                                case Constant.MODULE_ENTRANCE_CONTROL:
                                case Constant.MODULE_ZONE_MANAGE:
                                case Constant.MODULE_DEVICE_DATA:
                                case Constant.MODULE_DEPARTMENT_MANAGE:
                                case Constant.MODULE_EMPLOYEE_MANAGE:

                                    clearList();

                                    mPresenter.getData("3", keyword, MODULE_CODE);
                                    break;
                            }
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void setItemText(NavBarView view, Map<String, Object> item) {
        switch (MODULE_CODE) {
            case Constant.MODULE_ENTRANCE_DATA: {
                String name = item.get("PlaceName") == null ? item.get("DoorName").toString() : item.get("PlaceName").toString();
                view.setText(name);
                break;
            }
            case Constant.MODULE_ENTRANCE_EVENT: {
                String name = item.get("PlaceName") == null ? item.get("DoorName").toString() : item.get("PlaceName").toString();
                view.setText(name);
                break;
            }
            case Constant.MODULE_ENTRANCE_CONTROL: {
                String name = item.get("PlaceName") == null ? item.get("DoorName").toString() : item.get("PlaceName").toString();
                view.setText(name);
                break;
            }
            case Constant.MODULE_ZONE_MANAGE: {
                String name = item.get("PlaceName") == null ? item.get("DoorName").toString() : item.get("PlaceName").toString();
                view.setText(name);
                break;
            }
            case Constant.MODULE_DEVICE_DATA: {
                String name = item.get("PlaceName") == null ? item.get("DevName").toString() : item.get("PlaceName").toString();
                view.setText(name);
                break;
            }
            case Constant.MODULE_DEPARTMENT_MANAGE:
                String deptName = item.get("DeptName") == null ? "" : item.get("DeptName").toString();
                view.setText(deptName);
                break;
            case Constant.MODULE_EMPLOYEE_MANAGE:
                String deptName2 = item.get("DeptName") == null ? "" : item.get("DeptName").toString();
                view.setText(deptName2);
                break;
        }
    }

    @Override
    public void onItemClick(NavBarAdapter adapter, NavBarView view, int position) {
        if (position != mBarList.size() - 1) {
            for (int i = position + 1; i < mBarList.size(); ) {
                mBarList.remove(i);
            }
            navBarAdapter.notifyDataSetChanged();

            setButtonVisible(MODULE_CODE);

            clearList();

            switch (MODULE_CODE) {
                case Constant.MODULE_ENTRANCE_DATA:
                case Constant.MODULE_ENTRANCE_EVENT:
                case Constant.MODULE_ENTRANCE_CONTROL:
                case Constant.MODULE_ZONE_MANAGE:
                case Constant.MODULE_DEVICE_DATA:
                    if (mBarList.size() == 1) {
                        mPresenter.getData("0", "", MODULE_CODE);
                    } else {
                        mPresenter.getData("1", mBarList.get(mBarList.size() - 1).get("PlaceID").toString(), MODULE_CODE);
                    }
                    break;
                case Constant.MODULE_DEPARTMENT_MANAGE:
                    if (mBarList.size() == 1) {
                        mPresenter.getData("0", "", MODULE_CODE);
                    } else {
                        mPresenter.getData("1", mBarList.get(mBarList.size() - 1).get("DeptID").toString(), MODULE_CODE);
                    }
                    break;
                case Constant.MODULE_EMPLOYEE_MANAGE:
                    if (mBarList.size() == 1) {
                        mPresenter.getData("0", "", MODULE_CODE);
                    } else {
                        mPresenter.getData("1", mBarList.get(mBarList.size() - 1).get("DeptID").toString(), MODULE_CODE);
                    }
                    break;
            }
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        switch (MODULE_CODE) {
            case Constant.MODULE_ENTRANCE_DATA: {
                String zoneName = mContentList.get(position).get("PlaceName") == null ? "" : mContentList.get(position).get("PlaceName").toString();
//                String doorName = mContentList.get(position).get("DoorName") == null ? "" : mContentList.get(position).get("DoorName").toString();

                if (!TextUtils.isEmpty(zoneName)) {
                    mBarList.add(mContentList.get(position));
                    navBarAdapter.notifyDataSetChanged();
                    rv_nav.scrollToPosition(mBarList.size() - 1);
                    setButtonVisible(MODULE_CODE);

                    clearList();

                    mPresenter.getData("1", mBarList.get(mBarList.size() - 1).get("PlaceID").toString(), MODULE_CODE);
                } else {
                    //跳转到子页面
                    Intent intent = new Intent();
                    intent.putExtra("isEditMode", false);
                    intent.putExtra("belongDept", mBarList.get(mBarList.size() - 1).get("PlaceName").toString());
                    intent.putExtra("mData", new Gson().toJson(mContentList.get(position)));
                    intent.setClass(this, EntranceDataActivity.class);
                    startActivity(intent);
                }
                break;
            }
            case Constant.MODULE_ENTRANCE_EVENT: {
                String zoneName = mContentList.get(position).get("PlaceName") == null ? "" : mContentList.get(position).get("PlaceName").toString();
                if (!TextUtils.isEmpty(zoneName)) {
                    mBarList.add(mContentList.get(position));
                    navBarAdapter.notifyDataSetChanged();
                    rv_nav.scrollToPosition(mBarList.size() - 1);
                    setButtonVisible(MODULE_CODE);

                    clearList();

                    mPresenter.getData("1", mBarList.get(mBarList.size() - 1).get("PlaceID").toString(), MODULE_CODE);
                } else {
                    //跳转到子页面
                    Intent intent = new Intent(this, EntranceEventActivity.class);
                    intent.putExtra("title", mContentList.get(position).get("DoorName").toString());
                    startActivity(intent);
                }
                break;
            }
            case Constant.MODULE_ENTRANCE_CONTROL: {
                String zoneName = mContentList.get(position).get("PlaceName") == null ? "" : mContentList.get(position).get("PlaceName").toString();
                if (!TextUtils.isEmpty(zoneName)) {
                    mBarList.add(mContentList.get(position));
                    navBarAdapter.notifyDataSetChanged();
                    rv_nav.scrollToPosition(mBarList.size() - 1);

                    setButtonVisible(MODULE_CODE);

                    clearList();

                    mPresenter.getData("1", mBarList.get(mBarList.size() - 1).get("PlaceID").toString(), MODULE_CODE);
                } else {
                    //跳转到子页面
                    Intent intent = new Intent(this, EntranceControlActivity.class);
                    startActivity(intent);
                }
                break;
            }
            case Constant.MODULE_ZONE_MANAGE: {
                String zoneName = mContentList.get(position).get("PlaceName") == null ? "" : mContentList.get(position).get("PlaceName").toString();
                if (!TextUtils.isEmpty(zoneName)) {
                    mBarList.add(mContentList.get(position));
                    navBarAdapter.notifyDataSetChanged();
                    rv_nav.scrollToPosition(mBarList.size() - 1);
                    setButtonVisible(MODULE_CODE);

                    clearList();

                    mPresenter.getData("1", mBarList.get(mBarList.size() - 1).get("PlaceID").toString(), MODULE_CODE);
                } else {
                    //跳转到子页面
                    Intent intent = new Intent(this, ZoneDetailActivity.class);
                    intent.putExtra("isEditMode", false);
                    startActivity(intent);
                }
                break;
            }
            case Constant.MODULE_DEVICE_DATA: {
                String zoneName = mContentList.get(position).get("PlaceName") == null ? "" : mContentList.get(position).get("PlaceName").toString();
                if (!TextUtils.isEmpty(zoneName)) {
                    mBarList.add(mContentList.get(position));
                    navBarAdapter.notifyDataSetChanged();
                    rv_nav.scrollToPosition(mBarList.size() - 1);

                    setButtonVisible(MODULE_CODE);

                    clearList();

                    mPresenter.getData("1", mBarList.get(mBarList.size() - 1).get("PlaceID").toString(), MODULE_CODE);
                } else {
                    //跳转到子页面
                    Intent intent = new Intent(this, DeviceDataActivity.class);
                    intent.putExtra("belongDept", mBarList.get(mBarList.size() - 1).get("PlaceName").toString());
                    intent.putExtra("deviceInfo", new Gson().toJson(mContentList.get(position)));
                    startActivity(intent);
                }
                break;
            }
            case Constant.MODULE_DEPARTMENT_MANAGE:

                String deptName = mContentList.get(position).get("DeptName") == null ? "" : mContentList.get(position).get("DeptName").toString();
//                String doorName = mContentList.get(position).get("DoorName") == null ? "" : mContentList.get(position).get("DoorName").toString();

                if (!TextUtils.isEmpty(deptName)) {
                    mBarList.add(mContentList.get(position));
                    navBarAdapter.notifyDataSetChanged();
                    rv_nav.scrollToPosition(mBarList.size() - 1);

                    setButtonVisible(MODULE_CODE);

                    clearList();

                    mPresenter.getData("1", mBarList.get(mBarList.size() - 1).get("DeptID").toString(), MODULE_CODE);
                } else {
                    //跳转到子页面
                    Intent intent = new Intent();
                    intent.putExtra("isEditMode", false);
                    intent.setClass(this, DepartInfoDetailActivity.class);
                    startActivity(intent);
                }
                break;
            case Constant.MODULE_EMPLOYEE_MANAGE:
                //人员管理有三个层级,机构>团部>一营>李达康(个人详细资料)

                String deptName2 = mContentList.get(position).get("DeptName") == null ? "" : mContentList.get(position).get("DeptName").toString();
//                String doorName = mContentList.get(position).get("DoorName") == null ? "" : mContentList.get(position).get("DoorName").toString();

                if (!TextUtils.isEmpty(deptName2)) {
                    LogUtil.e(TAG, "----------mContentList.get(position):" + mContentList.get(position));
                    mBarList.add(mContentList.get(position));
                    navBarAdapter.notifyDataSetChanged();
                    rv_nav.scrollToPosition(mBarList.size() - 1);

                    setButtonVisible(MODULE_CODE);

                    clearList();

                    mPresenter.getData("1", mBarList.get(mBarList.size() - 1).get("DeptID").toString(), MODULE_CODE);
                } else {
                    //跳转到子页面
                    Intent intent = new Intent();
                    intent.putExtra("empInfo", new Gson().toJson(mContentList.get(position)));
                    intent.putExtra("isEditMode", false);
                    intent.putExtra("belongDept", mBarList.get(mBarList.size() - 1).get("DeptName").toString());
                    intent.putExtra("position", position);
                    intent.setClass(this, EmployeeInfoDetailActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    public void setButtonVisible() {
        if (mBarList.size() > 1) {
            iv_add.setVisibility(View.VISIBLE);
        } else {
            iv_add.setVisibility(View.INVISIBLE);
        }
    }

    public void setButtonVisible(int code) {
        switch (code) {
            case Constant.MODULE_ENTRANCE_DATA:
                setButtonVisible();
                break;
            case Constant.MODULE_ENTRANCE_EVENT:
                iv_add.setVisibility(View.INVISIBLE);
                break;
            case Constant.MODULE_ENTRANCE_CONTROL:
                iv_add.setVisibility(View.INVISIBLE);
                break;
            case Constant.MODULE_ZONE_MANAGE:
                setButtonVisible();
                break;
            case Constant.MODULE_DEVICE_DATA:
                setButtonVisible();
                break;
            case Constant.MODULE_DEPARTMENT_MANAGE:
                setButtonVisible();
                break;
            case Constant.MODULE_EMPLOYEE_MANAGE:
                setButtonVisible();
                break;
        }
    }

    @Override
    public void onRefresh() {
//        srf_layout.setRefreshing(false);
        clearList();
        switch (MODULE_CODE) {
            case Constant.MODULE_ENTRANCE_DATA:
            case Constant.MODULE_ENTRANCE_EVENT:
            case Constant.MODULE_ENTRANCE_CONTROL:
            case Constant.MODULE_ZONE_MANAGE:
            case Constant.MODULE_DEVICE_DATA:
                if (mBarList.size() == 1) {
                    mPresenter.getData("0", "", MODULE_CODE);
                } else {
                    mPresenter.getData("1", mBarList.get(mBarList.size() - 1).get("PlaceID").toString(), MODULE_CODE);
                }
                break;
            case Constant.MODULE_DEPARTMENT_MANAGE:
            case Constant.MODULE_EMPLOYEE_MANAGE:
                if (mBarList.size() == 1) {
                    mPresenter.getData("0", "", MODULE_CODE);
                } else {
                    mPresenter.getData("1", mBarList.get(mBarList.size() - 1).get("DeptID").toString(), MODULE_CODE);
                }
                break;

        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showData(List<Map<String, Object>> list) {
        if (srf_layout.isRefreshing()) {
            srf_layout.setRefreshing(false);
        }
        mContentList.clear();
        moduleAdapter.notifyDataSetChanged();
        mContentList.addAll(list);
        if (mContentList.size() > 0) {
            moduleAdapter.setNewData(mContentList);
        } else {
            setEmptyView();
        }
    }

    @Override
    public void setEmptyView() {
        moduleAdapter.setEmptyView(mEmptyView);
    }

    @Override
    public void setErrorView() {
        srf_layout.setRefreshing(false);
        moduleAdapter.setEmptyView(mErrorView);
    }

    @Override
    public void setNoNetWorkView() {
        srf_layout.setRefreshing(false);
        moduleAdapter.setEmptyView(mNoNetWorkView);
    }

    @Override
    public void showDeepData(List<Map<String, Object>> list) {
        if (srf_layout.isRefreshing()) {
            srf_layout.setRefreshing(false);
        }
        mContentList.addAll(list);
        if (mContentList.size() > 0) {
            moduleAdapter.setNewData(mContentList);
        } else {
            setEmptyView();
        }
    }

    @Override
    public void beginRefresh(boolean bl) {
        srf_layout.setRefreshing(bl);
    }

    public void setBlankView(){
        moduleAdapter.setEmptyView(mBlankView);
    }

    @Override
    public void refresh() {
        onRefresh();
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
        switch (MODULE_CODE) {
            case Constant.MODULE_ENTRANCE_DATA:

            case Constant.MODULE_ENTRANCE_EVENT:

            case Constant.MODULE_ENTRANCE_CONTROL:

            case Constant.MODULE_DEVICE_DATA: {
                final String zoneName = mContentList.get(position).get("PlaceName") == null ? "" : mContentList.get(position).get("PlaceName").toString();
                if (!TextUtils.isEmpty(zoneName)) {
                    ListDialog listDialog = new ListDialog(this);
                    String[] items = {getString(R.string.module_str_5), getString(R.string.module_str_6)};
                    listDialog.setItem(items);
                    listDialog.setItemEnable(1);
                    listDialog.setOnClickListener(new ListDialog.OnClickListener() {
                        @Override
                        public void onClick(Dialog dialog, int which) {
                            switch (which) {
                                case 0:
                                    //跳转到子页面
                                    Intent intent = new Intent(ModuleActivity.this, ZoneDetailActivity.class);
                                    intent.putExtra("mData", new Gson().toJson(mContentList.get(position)));
                                    String mRoot = mBarList.size() == 1 ? getString(R.string.module_str_7) : mBarList.get(mBarList.size() - 1).get("PlaceName").toString();
                                    intent.putExtra("mRoot", mRoot);
                                    intent.putExtra("isEditMode", false);
                                    startActivity(intent);
                                    break;
                                case 1:
                                    CommonDialog commonDialog = new CommonDialog(ModuleActivity.this);
                                    commonDialog.setTitle(getString(R.string.dialog_title_delete));
                                    commonDialog.setMessage(getString(R.string.module_str_8, zoneName));
                                    commonDialog.setPositiveButton(getString(R.string.dialog_positive));
                                    commonDialog.setNegativeButton(getString(R.string.dialog_negative));
                                    commonDialog.setOnClickListener(new CommonDialog.OnClickListener() {
                                        @Override
                                        public void onClick(Dialog dialog, int which) {
                                            if (which == Dialog.BUTTON_POSITIVE) {
                                                int placeID = Math.round(Float.valueOf(mContentList.get(position).get("PlaceID").toString()));
                                                mPresenter.delete(placeID);
                                            }
                                        }
                                    });
                                    commonDialog.show();
                                    break;
                            }
                        }
                    });
                    listDialog.show();
                }
                break;
            }
            case Constant.MODULE_ZONE_MANAGE: {
                final String zoneName = mContentList.get(position).get("PlaceName") == null ? "" : mContentList.get(position).get("PlaceName").toString();
                if (!TextUtils.isEmpty(zoneName)) {
                    ListDialog listDialog = new ListDialog(this);
                    String[] items = {getString(R.string.module_str_5), getString(R.string.module_str_6)};
                    listDialog.setItem(items);
                    listDialog.setOnClickListener(new ListDialog.OnClickListener() {
                        @Override
                        public void onClick(Dialog dialog, int which) {
                            switch (which) {
                                case 0:
                                    //跳转到子页面
                                    Intent intent = new Intent(ModuleActivity.this, ZoneDetailActivity.class);
                                    intent.putExtra("mData", new Gson().toJson(mContentList.get(position)));
                                    String mRoot = mBarList.size() == 1 ? getString(R.string.module_str_7) : mBarList.get(mBarList.size() - 1).get("PlaceName").toString();
                                    intent.putExtra("mRoot", mRoot);
                                    intent.putExtra("isEditMode", false);
                                    startActivity(intent);
                                    break;
                                case 1:
                                    CommonDialog commonDialog = new CommonDialog(ModuleActivity.this);
                                    commonDialog.setTitle(getString(R.string.dialog_title_delete));
                                    commonDialog.setMessage(getString(R.string.module_str_8, zoneName));
                                    commonDialog.setPositiveButton(getString(R.string.dialog_positive));
                                    commonDialog.setNegativeButton(getString(R.string.dialog_negative));
                                    commonDialog.setOnClickListener(new CommonDialog.OnClickListener() {
                                        @Override
                                        public void onClick(Dialog dialog, int which) {
                                            if (which == Dialog.BUTTON_POSITIVE) {
                                                int placeID = Math.round(Float.valueOf(mContentList.get(position).get("PlaceID").toString()));
                                                mPresenter.delete(placeID);
                                            }
                                        }
                                    });
                                    commonDialog.show();
                                    break;
                            }
                        }
                    });
                    listDialog.show();
                }
                break;
            }
            case Constant.MODULE_DEPARTMENT_MANAGE:

                String deptName = mContentList.get(position).get("DeptName") == null ? "" : mContentList.get(position).get("DeptName").toString();
                if (!TextUtils.isEmpty(deptName)) {
                    ListDialog listDialog = new ListDialog(this);
                    String[] items = {getString(R.string.module_str_watch_dept), getString(R.string.module_str_delete_dept)};
                    listDialog.setItem(items);
                    listDialog.setOnClickListener(new ListDialog.OnClickListener() {
                        @Override
                        public void onClick(Dialog dialog, int which) {
                            switch (which) {
                                case 0:
                                    //跳转到子页面
                                    Intent intent = new Intent(ModuleActivity.this, DepartInfoDetailActivity.class);
                                    intent.putExtra("mData", new Gson().toJson(mContentList.get(position)));
                                    LogUtil.e("------------", "-------mData:" + mContentList.get(position).toString());
                                    String mRoot = mBarList.size() == 1 ? getString(R.string.module_str_7) : mBarList.get(mBarList.size() - 1).get("DeptName").toString();
                                    intent.putExtra("mRoot", mRoot);
                                    intent.putExtra("isEditMode", false);
                                    startActivity(intent);
                                    break;
                                case 1:
                                    UIUtils.showToast("这里的删除没有做,可在详情里面删除 T_T");
                                    break;
                            }
                        }
                    });
                    listDialog.show();

                }

                break;
            case Constant.MODULE_EMPLOYEE_MANAGE:

                break;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregistEventBus(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshListData(MessageEvent messageEvent) {
        if (messageEvent.getAction() == MessageEvent.ACTION_ACTIVITY_UPDATE) {
            LogUtil.e("ModuleActivity", "----------------------ACTION_DELETE_EMP_SUC");
            //收到删除成功消息后刷新当前界面
            srf_layout.setRefreshing(true);
            onRefresh();
        }
    }


    public void clearList(){
        mContentList.clear();
        moduleAdapter.notifyDataSetChanged();
        setBlankView();
    }
}
