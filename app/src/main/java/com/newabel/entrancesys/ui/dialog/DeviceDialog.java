package com.newabel.entrancesys.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.service.presenter.DeviceDialogPresenter;
import com.newabel.entrancesys.ui.adapter.DialogDeviceAdapter;
import com.newabel.entrancesys.ui.adapter.NavBarAdapter;
import com.newabel.entrancesys.ui.iview.DeviceDialogView;
import com.newabel.entrancesys.ui.utils.UIUtils;
import com.newabel.entrancesys.ui.widget.NavBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2018/1/15 0015.
 */

public class DeviceDialog extends Dialog implements NavBarAdapter.OnNavItemWatcher<Map<String, Object>>, DeviceDialogView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener, View.OnFocusChangeListener, TextView.OnEditorActionListener, View.OnClickListener {


    private int MODULE_CODE;
    private RecyclerView rv_nav;
    private RecyclerView rv_list;
    private NavBarAdapter<Map<String, Object>> navBarAdapter;
    private List<Map<String, Object>> mBarList;
    private DeviceDialogPresenter mPresenter;
    private SwipeRefreshLayout srf_layout;
    private List<Map<String, Object>> mContentList;
    private DialogDeviceAdapter moduleAdapter;
    private View mEmptyView;
    private View mErrorView;
    private View mNoNetWorkView;
    private String mHint;
    private EditText et_keyword;
    private DataSelectedListener mDataSelectedListener;
    private View mLoadingView;
    private View mBlankView;

    public DeviceDialog(@NonNull Context context, int code) {
        super(context, R.style.DialogTheme);
        this.MODULE_CODE = code;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        srf_layout.setOnRefreshListener(this);
        moduleAdapter.setOnItemClickListener(this);
        et_keyword.setOnFocusChangeListener(this);
        et_keyword.setOnEditorActionListener(this);
        mEmptyView.setOnClickListener(this);
        mErrorView.setOnClickListener(this);
        mNoNetWorkView.setOnClickListener(this);
    }

    private void initData() {
        mBarList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        mBarList.add(map);

        srf_layout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));

        mContentList = new ArrayList<>();
        moduleAdapter = new DialogDeviceAdapter(mContentList, MODULE_CODE);
        rv_list.setAdapter(moduleAdapter);

        switch (MODULE_CODE) {
            case Constant.MODULE_ENTRANCE_DATA:
                mHint = getContext().getString(R.string.module_hint_entrance_data);
                map.put("PlaceName", getContext().getString(R.string.module_str_3));
                map.put("PlaceID", "");
                break;
            case Constant.MODULE_ENTRANCE_EVENT:
                mHint = getContext().getString(R.string.module_hint_entrance_event);
                map.put("PlaceName", getContext().getString(R.string.module_str_3));
                map.put("PlaceID", "");
                break;
            case Constant.MODULE_ENTRANCE_CONTROL:
                mHint = getContext().getString(R.string.module_hint_entrance_control);
                map.put("PlaceName", getContext().getString(R.string.module_str_3));
                map.put("PlaceID", "");
                break;
            case Constant.MODULE_ZONE_MANAGE:
                mHint = getContext().getString(R.string.module_hint_zone_manage);
                map.put("PlaceName", getContext().getString(R.string.module_str_4));
                map.put("PlaceID", "");
                break;
            case Constant.MODULE_DEVICE_DATA:
                mHint = getContext().getString(R.string.module_hint_device_data);
                map.put("PlaceName", getContext().getString(R.string.module_str_4));
                map.put("PlaceID", "");
                break;
            case Constant.MODULE_DEPARTMENT_MANAGE:
                mHint = getContext().getString(R.string.module_hint_department_manage);
                map.put("DeptName", getContext().getString(R.string.module_str_9));
                map.put("DeptID", "");
                break;
            case Constant.MODULE_EMPLOYEE_MANAGE:
                mHint = getContext().getString(R.string.module_hint_employee_manage);
                map.put("DeptName", getContext().getString(R.string.module_str_10));
                map.put("DeptID", "");
                break;
        }
        et_keyword.setHint(mHint);
        navBarAdapter = new NavBarAdapter<>(getContext(), mBarList, this);
        rv_nav.setAdapter(navBarAdapter);
        mPresenter.getData("0", "", MODULE_CODE);
    }

    private void initView() {
        mPresenter = new DeviceDialogPresenter(this);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_device, null);
        rv_nav = view.findViewById(R.id.rv_nav);
        LinearLayoutManager ll = new LinearLayoutManager(getContext());
        ll.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_nav.setLayoutManager(ll);
        rv_list = view.findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
        srf_layout = view.findViewById(R.id.srf_layout);
        et_keyword = view.findViewById(R.id.et_keyword);
        et_keyword.setSingleLine();

        mBlankView = getLayoutInflater().inflate(R.layout.view_blank, (ViewGroup) rv_list.getParent(), false);
        mLoadingView = getLayoutInflater().inflate(R.layout.view_loading,(ViewGroup) rv_list.getParent(), false);
        mEmptyView = getLayoutInflater().inflate(R.layout.view_load_empty, (ViewGroup) rv_list.getParent(), false);
        mErrorView = getLayoutInflater().inflate(R.layout.view_load_error, (ViewGroup) rv_list.getParent(), false);
        mNoNetWorkView = getLayoutInflater().inflate(R.layout.view_load_no_network, (ViewGroup) rv_list.getParent(), false);

        setContentView(view);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        getWindow().setWindowAnimations(R.style.Dialog_Bottom);
    }

    @Override
    public void setItemText(NavBarView view, Map<String, Object> item) {
        String name = item.get("PlaceName") == null ? item.get("DevName").toString() : item.get("PlaceName").toString();
        view.setText(name);
    }

    @Override
    public void onItemClick(NavBarAdapter adapter, NavBarView view, int position) {
        if (position != mBarList.size() - 1) {
            for (int i = position + 1; i < mBarList.size(); ) {
                mBarList.remove(i);
            }
            navBarAdapter.notifyDataSetChanged();

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
        srf_layout.setRefreshing(false);
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
    public void onRefresh() {

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

    public void show() {
        super.show();
        WindowManager.LayoutParams param = getWindow().getAttributes();
        param.width = WindowManager.LayoutParams.MATCH_PARENT;
        param.height = UIUtils.getScreenHeight() * 3 / 5;
        param.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(param);
    }

    @Override
    public void dismiss() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.dismiss();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (MODULE_CODE) {
            case Constant.MODULE_ENTRANCE_DATA:
            case Constant.MODULE_ENTRANCE_EVENT:
            case Constant.MODULE_ENTRANCE_CONTROL:
            case Constant.MODULE_ZONE_MANAGE:
            case Constant.MODULE_DEVICE_DATA: {
                String zoneName = mContentList.get(position).get("PlaceName") == null ? "" : mContentList.get(position).get("PlaceName").toString();
                if (!TextUtils.isEmpty(zoneName)) {
                    mBarList.add(mContentList.get(position));
                    navBarAdapter.notifyDataSetChanged();
                    rv_nav.scrollToPosition(mBarList.size() - 1);

                    clearList();

                    mPresenter.getData("1", mBarList.get(mBarList.size() - 1).get("PlaceID").toString(), MODULE_CODE);
                } else {
                    //跳转到子页面
                    setData(mContentList.get(position));
                    dismiss();
                }
                break;
            }
            case Constant.MODULE_DEPARTMENT_MANAGE:
            case Constant.MODULE_EMPLOYEE_MANAGE: {
                //人员管理有三个层级,机构>团部>一营>李达康(个人详细资料)
                String deptName2 = mContentList.get(position).get("DeptName") == null ? "" : mContentList.get(position).get("DeptName").toString();
                if (!TextUtils.isEmpty(deptName2)) {
                    mBarList.add(mContentList.get(position));
                    navBarAdapter.notifyDataSetChanged();
                    rv_nav.scrollToPosition(mBarList.size() - 1);

                    clearList();

                    mPresenter.getData("1", mBarList.get(mBarList.size() - 1).get("DeptID").toString(), MODULE_CODE);
                } else {
                    //跳转到子页面
                    setData(mContentList.get(position));
                    dismiss();
                }
                break;
            }
        }
    }

    private void setData(Map<String, Object> map) {
        if (mDataSelectedListener != null) {
            mDataSelectedListener.setSelectData(map);
        }
    }

    public void setDataSelectedListener(DataSelectedListener listener) {
        this.mDataSelectedListener = listener;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        ViewGroup.LayoutParams param = v.getLayoutParams();
        if (hasFocus) {
            param.width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            if (TextUtils.isEmpty(et_keyword.getText())) {
                param.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
        }
        v.setLayoutParams(param);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String keyword = et_keyword.getText().toString().trim();
            if (TextUtils.isEmpty(keyword)) {
                UIUtils.showToast(mHint);
                return true;
            }
            UIUtils.hideSoftInput(et_keyword);

            clearList();

            switch (MODULE_CODE) {
                case Constant.MODULE_ENTRANCE_DATA:
                case Constant.MODULE_ENTRANCE_EVENT:
                case Constant.MODULE_ENTRANCE_CONTROL:
                case Constant.MODULE_ZONE_MANAGE:
                case Constant.MODULE_DEVICE_DATA:
                case Constant.MODULE_DEPARTMENT_MANAGE:
                case Constant.MODULE_EMPLOYEE_MANAGE:
                    mPresenter.getData("3", keyword, MODULE_CODE);
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

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
    }

    public interface DataSelectedListener {
        void setSelectData(Map<String, Object> map);
    }

    public void clearList(){
        mContentList.clear();
        moduleAdapter.notifyDataSetChanged();
        setBlankView();
    }
}
