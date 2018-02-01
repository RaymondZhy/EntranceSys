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
import com.newabel.entrancesys.service.presenter.EmployeeAddNewPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.dialog.BottomDialog;
import com.newabel.entrancesys.ui.iview.EmployeeAddNewView;
import com.newabel.entrancesys.ui.utils.LogUtil;
import com.newabel.entrancesys.ui.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AddNewEmployeeActivity extends BaseActivity<EmployeeAddNewPresenter> implements EmployeeAddNewView {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.et_employee_code_add)
    EditText etEmpCodeAdd;

    @BindView(R.id.et_employee_name_add)
    EditText etEmpNameAdd;

    @BindView(R.id.et_certif_num_add)
    EditText etEmpCertifNumAdd;

    @BindView(R.id.et_phone_num_add)
    EditText etEmpPhoneNumAdd;

    @BindView(R.id.tv_belong)
    TextView tvBelong;

    @BindView(R.id.tv_employee_sex_add_value)
    TextView tvSex;

    @BindView(R.id.et_person_type_add)
    TextView tvEmpType;

    @BindView(R.id.et_certif_type_add)
    TextView tvCertifType;


    private Map<String, Object> mData;

    private String EmpNo, EmpName, CertifNo, MobilePhone;

    int DeptID;
    int sex, empType, CertifType;
    String CertifTypeName;
    int EmpID;//人员id,感觉新增时人员id应该是没有的,但是接口文档里面有这个参数


    @Override
    protected EmployeeAddNewPresenter createPresenter() {
        return new EmployeeAddNewPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_add_new_employee;
    }

    @Override
    protected void initView() {
        tvTitle.setText(R.string.text_add_emp);


    }

    @Override
    protected void initData() {
        super.initData();
        String jStr = getIntent().getStringExtra("mData");
        LogUtil.e("-------------", "------------jStr:" + jStr);

        if (jStr != null) {
            mData = new Gson().fromJson(jStr, Map.class);

            String deptName = mData.get("DeptName").toString();
            DeptID = Math.round(Float.valueOf(mData.get("DeptID").toString()));
            if (!TextUtils.isEmpty(deptName)) {
                tvBelong.setText(deptName);
            }
        }

    }

    @OnClick({R.id.ll_back, R.id.btn_submit, R.id.rl_emp_sex_add, R.id.rl_emp_type_add, R.id.rl_certif_type_add})
    void onIconClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.btn_submit:
                EmpNo = etEmpCodeAdd.getText().toString();
                EmpName = etEmpNameAdd.getText().toString();
                CertifNo = etEmpCertifNumAdd.getText().toString();
                MobilePhone = etEmpPhoneNumAdd.getText().toString();

                if (TextUtils.isEmpty(EmpNo)) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_emp_num_isnull));
                    break;
                }

                if (TextUtils.isEmpty(EmpName)) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_emp_name_isnull));
                    break;
                }

                if (0 == sex) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_choose_emp_sex));
                    break;
                }

                if (0 == empType) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_choose_emp_type));
                    break;
                }

                if (0 == CertifType) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_choose_emp_certif));
                    break;
                }

                mPresenter.empAdd(EmpID, EmpNo, EmpName, DeptID, sex, empType, CertifNo, CertifTypeName, MobilePhone);


                break;
            case R.id.rl_emp_sex_add:
                BottomDialog bottomSexDialog = new BottomDialog(this, mPresenter.getSexTypeData(sex));
                bottomSexDialog.show();
                bottomSexDialog.setOnClickListener(new SexClickListener());
                break;
            case R.id.rl_emp_type_add:
                BottomDialog bottomEmpTypeDialog = new BottomDialog(this, mPresenter.getEmpTypeData(empType));
                bottomEmpTypeDialog.show();
                bottomEmpTypeDialog.setOnClickListener(new EmpTypeClickListener());
                break;
            case R.id.rl_certif_type_add:
                BottomDialog bottomCertifTypeDialog = new BottomDialog(this, mPresenter.getCertifTypeData(CertifType));
                bottomCertifTypeDialog.show();
                bottomCertifTypeDialog.setOnClickListener(new CertifClickListener());
                break;


        }
    }

    @Override
    public void empAddSuc() {
        UIUtils.showToast(UIUtils.getString(R.string.add_success));
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTION_ACTIVITY_UPDATE));

        finish();
    }

    @Override
    public void empAddError(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            UIUtils.showToast(msg);
        } else {
            UIUtils.showToast(UIUtils.getString(R.string.add_fail));
        }
    }

    @Override
    public Context getContext() {
        return this;
    }


    class SexClickListener implements BottomDialog.OnClickListener {

        @Override
        public void onClick(Dialog dialog, int which) {
            switch (which) {
                case 0:
                    sex = 1;//男
                    tvSex.setText("男");
                    break;
                case 1:
                    sex = 2;//女
                    tvSex.setText("女");
                    break;
            }
        }
    }

    class EmpTypeClickListener implements BottomDialog.OnClickListener {

        @Override
        public void onClick(Dialog dialog, int which) {
            switch (which) {
                case 0:
                    empType = 1;//民警
                    tvEmpType.setText("民警");
                    break;
                case 1:
                    empType = 2;//职工
                    tvEmpType.setText("职工");
                    break;
            }
        }
    }

    class CertifClickListener implements BottomDialog.OnClickListener {

        @Override
        public void onClick(Dialog dialog, int which) {
            switch (which) {
                case 0:
                    CertifType = 1;
                    tvCertifType.setText("身份证");
                    CertifTypeName = "身份证";
                    break;
                case 1:
                    CertifType = 2;
                    tvCertifType.setText("军官证");
                    CertifTypeName = "军官证";
                    break;
                case 2:
                    CertifType = 3;
                    tvCertifType.setText("士兵证");
                    CertifTypeName = "士兵证";
                    break;
            }
        }
    }
}
