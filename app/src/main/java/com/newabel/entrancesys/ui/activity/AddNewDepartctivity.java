package com.newabel.entrancesys.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.MessageEvent;
import com.newabel.entrancesys.service.presenter.DeptAddNewPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.dialog.BottomDialog;
import com.newabel.entrancesys.ui.iview.DeptAddNewView;
import com.newabel.entrancesys.ui.utils.LogUtil;
import com.newabel.entrancesys.ui.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 新增机构
 */
public class AddNewDepartctivity extends BaseActivity<DeptAddNewPresenter> implements DeptAddNewView {

    public static final String belongDept = "belongdept";

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.et_dept_code_add)
    EditText etDeptCodeAdd;

    @BindView(R.id.et_dept_name_add)
    EditText etDeptNameAdd;

    @BindView(R.id.tv_dept_belong_add_value)
    TextView tvDeptBelong;

    @BindView(R.id.tv_dept_type_value)
    TextView tvDeptType;


    private String mBelongDepart;

    int DeptID, MasterID;//机构id,上级机构id
    String DeptNo, DeptName;//机构编号,机构名称
    int DeptType;//机构类型

    private Map<String, Object> mData;
    private List<Map<String, Object>> deptTypeData;

    @Override
    protected DeptAddNewPresenter createPresenter() {
        return new DeptAddNewPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_add_new_departctivity;
    }

    @Override
    protected void initView() {
        tvTitle.setText(R.string.text_add_dept);

    }

    @Override
    protected void initData() {
        super.initData();
        String jStr = this.getIntent().getStringExtra("mData");
        LogUtil.e("----------------", "------jStr:" + jStr);
        if (!TextUtils.isEmpty(jStr)) {
            mData = new Gson().fromJson(jStr, Map.class);
            mBelongDepart = mData.get("DeptName").toString();
            MasterID = Math.round(Float.valueOf(mData.get("DeptID").toString()));
            if (!TextUtils.isEmpty(mBelongDepart)) {
                tvDeptBelong.setText(mBelongDepart);
            } else {
                tvDeptBelong.setText("未知");
            }
        }
        //获取机构类型数据
        mPresenter.getDeptTypeData();
    }

    @OnClick({R.id.ll_back, R.id.btn_submit, R.id.rl_dept_type})
    void onIconClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.btn_submit:
                DeptNo = etDeptCodeAdd.getText().toString();
                DeptName = etDeptNameAdd.getText().toString();

                if (TextUtils.isEmpty(DeptNo)) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_dept_num_isnull));
                    break;
                }

                if (TextUtils.isEmpty(DeptName)) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_dept_name_isnull));
                    break;
                }

                if (DeptType == 0) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_choose_dept_type));
                    break;
                }

                mPresenter.deptAdd(DeptID, DeptNo, MasterID, DeptName, DeptType);
                break;

            case R.id.rl_dept_type:
                BottomDialog bottomEmpTypeDialog = new BottomDialog(this, mPresenter.getDeptTypeData(DeptType, deptTypeData));
                bottomEmpTypeDialog.show();
                bottomEmpTypeDialog.setOnClickListener(new DeptTypeClickListener());
                break;

        }

    }

    class DeptTypeClickListener implements BottomDialog.OnClickListener {

        @Override
        public void onClick(Dialog dialog, int which) {
            DeptType = Math.round(Float.valueOf(deptTypeData.get(which).get("TypeID").toString()));
            tvDeptType.setText(deptTypeData.get(which).get("TypeName").toString());
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void getDeptTypeDataSuc(List<Map<String, Object>> deptTypeData) {
        if (deptTypeData != null) {
            this.deptTypeData = deptTypeData;
        }
    }

    @Override
    public void getDeptTypeDataError(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            UIUtils.showToast(msg);
        } else {
            UIUtils.showToast(UIUtils.getString(R.string.warn_get_dept_type_fail));
        }
    }

    @Override
    public void deptAddSuc() {
        UIUtils.showToast(UIUtils.getString(R.string.add_success));
        //新增机构成功,发送一个消息出去
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTION_ACTIVITY_UPDATE));

        finish();
    }

    @Override
    public void deptAddError(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            UIUtils.showToast(msg);
        } else {
            UIUtils.showToast(UIUtils.getString(R.string.add_fail));
        }
    }

}
