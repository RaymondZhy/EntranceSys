package com.newabel.entrancesys.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.MessageEvent;
import com.newabel.entrancesys.service.presenter.EmployeeInfoListPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.dialog.BottomDialog;
import com.newabel.entrancesys.ui.dialog.CommonDialog;
import com.newabel.entrancesys.ui.dialog.MenuDialog;
import com.newabel.entrancesys.ui.iview.EmployeeInfoListView;
import com.newabel.entrancesys.ui.utils.LogUtil;
import com.newabel.entrancesys.ui.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Date: 2017/12/20 09:42
 * Description:人员管理人员信息详细
 */

public class EmployeeInfoDetailActivity extends BaseActivity<EmployeeInfoListPresenter> implements MenuDialog.OnClickListener,
        CommonDialog.OnClickListener, EmployeeInfoListView {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_add)
    ImageView iv_add;

    @BindView(R.id.et_code)
    EditText etEmpCode;

    @BindView(R.id.et_name)
    EditText etName;

    @BindView(R.id.et_sex)
    TextView etSex;

    @BindView(R.id.et_belong_dept)
    EditText etBelongDept;

    @BindView(R.id.et_person_type)
    TextView etPersonType;

    @BindView(R.id.et_certif_type)
    TextView etCertifType;

    @BindView(R.id.et_certif_num)
    EditText etCertifNum;

    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;


    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private Map<String, Object> empInfo;
    private boolean isEditMode = false;//是否处于编辑状态,默认为否
    private String belongDeptName;
    private int empId;//员工id
    //修改员工信息需要的数据
//    String EmpID, String EmpNo, String EmpName, String DeptID, String sex,
//    String EmpType, String CertifNo, String CertifType, String MobilePhone
    private String EmpNo, EmpName, CertifNo, MobilePhone;
    int DeptID;
    int sex, empType, CertifType;
    String CertifTypeName;

    int position;//记录下位置,用于修改成功后返回时回到该位置


    @Override
    protected EmployeeInfoListPresenter createPresenter() {
        return new EmployeeInfoListPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_employee_infolist;
    }

    @Override
    protected void initView() {
        tvTitle.setText(R.string.text_emp_info);
        iv_add.setVisibility(View.VISIBLE);
        iv_add.setImageResource(R.mipmap.ic_more);

        Intent intent = getIntent();

        if (intent != null) {

            belongDeptName = intent.getStringExtra("belongDept");
            position = intent.getIntExtra("position", 0);
            String jStr = intent.getStringExtra("empInfo");
            if (jStr != null) {
                empInfo = new Gson().fromJson(jStr, Map.class);
                empId = Math.round(Float.valueOf(empInfo.get("EmpID").toString()));
                EmpNo = empInfo.get("EmpNo").toString();
                EmpName = empInfo.get("EmpName").toString();
                DeptID = Math.round(Float.valueOf(empInfo.get("DeptID").toString()));
                sex = Math.round(Float.valueOf(empInfo.get("Sex").toString()));
                empType = Math.round(Float.valueOf(empInfo.get("EmpType").toString()));
                CertifNo = empInfo.get("CertifNo").toString();
                MobilePhone = empInfo.get("MobilePhone").toString();
//            CertifType = Math.round(Float.valueOf(empInfo.get("CertifType").toString()));
            }

        }
        if (!isEditMode && empInfo != null) {
            etEmpCode.setText(EmpNo);
            etName.setText(EmpName);

            LogUtil.e("EmployeeInfoDetailActivity", "------------sex:" + empInfo.get("Sex").toString());

            switch (sex) {
                case 1:
                    //男
                    etSex.setText("男");

                    break;
                case 2:
                    //女
                    etSex.setText("女");
                    break;

                case 0:
                    //未知
                    etSex.setText("未知");
                    break;
            }


            if (belongDeptName != null) {
                etBelongDept.setText(belongDeptName);
            }


            switch (empType) {
                case 1:
                    etPersonType.setText("民警");
                    break;
                case 2:
                    etPersonType.setText("职工");
                    break;
            }


//            rl_state.setVisibility(View.VISIBLE);
//            btn_submit.setVisibility(View.GONE);
//
//            et_code.setEnabled(false);
//            et_name.setEnabled(false);
        } else {
//            et_code.setText("");
//            et_name.setText("");
//            tv_ctr_type.setText("");
//            tv_ctr_device.setText("");
        }


    }


    @OnClick({R.id.ll_back, R.id.iv_add, R.id.btn_submit, R.id.rl_sex, R.id.et_sex, R.id.rl_emp_type, R.id.et_person_type,
            R.id.rl_certif_type, R.id.et_certif_type})
    void onIconClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.iv_add:
                MenuDialog dialog = new MenuDialog(this);
                String[] items = {"编辑", "删除"};
                dialog.setItem(items);
                dialog.setWidth(80);
                dialog.setOnClickListener(this);
                dialog.show(iv_add);
                break;
            case R.id.btn_submit:
                UIUtils.showToast("提交");
//                etEmpCode.setEnabled(true);
//                etName.setEnabled(true);
//                etCertifNum.setEnabled(true);
//                etPhoneNum.setEnabled(true);
//                private String EmpNo, EmpName, DeptID, CertifNo, MobilePhone;
//                int sex, empType, CertifType;
                EmpNo = etEmpCode.getText().toString();
                EmpName = etName.getText().toString();
                CertifNo = etCertifNum.getText().toString();
                MobilePhone = etPhoneNum.getText().toString();

                if (TextUtils.isEmpty(EmpNo)) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_emp_num_isnull));
                    break;
                }

                if (TextUtils.isEmpty(EmpName)) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_emp_name_isnull));
                    break;
                }
                LogUtil.e("aaaaaaaaaaaaaa", "----------------empId:" + empId);
                mPresenter.modify(empId, EmpNo, EmpName, DeptID, sex, empType, CertifNo, CertifTypeName, MobilePhone);

                break;
            case R.id.et_sex:
            case R.id.rl_sex:
                if (isEditMode) {
                    BottomDialog bottomDialog = new BottomDialog(this, mPresenter.getSexTypeData(sex));
                    bottomDialog.show();
                    bottomDialog.setOnClickListener(new SexClickListener());
                }
                break;
            case R.id.et_person_type:
            case R.id.rl_emp_type:
                if (isEditMode) {
                    BottomDialog bottomDialog = new BottomDialog(this, mPresenter.getEmpTypeData(empType));
                    bottomDialog.show();
                    bottomDialog.setOnClickListener(new EmpTypeClickListener());
                }
                break;
            case R.id.et_certif_type:
            case R.id.rl_certif_type:
                if (isEditMode) {
                    BottomDialog bottomDialog = new BottomDialog(this, mPresenter.getCertifTypeData(CertifType));
                    bottomDialog.show();
                    bottomDialog.setOnClickListener(new CertifClickListener());
                }
                break;
        }
    }

    @Override
    public void onClick(PopupWindow window, int which) {
        switch (which) {
            case 0://编辑
                isEditMode = true;
                etEmpCode.setEnabled(true);
                etName.setEnabled(true);
                etCertifNum.setEnabled(true);
                etPhoneNum.setEnabled(true);

                btnSubmit.setVisibility(View.VISIBLE);
                break;
            case 1://删除
                CommonDialog dialog = new CommonDialog(this);
                dialog.setTitle("删除确认");
                dialog.setMessage("确定删除该条记录吗？");
                dialog.setNegativeButton(getString(R.string.dialog_negative));
                dialog.setPositiveButton(getString(R.string.dialog_positive));
                dialog.setOnClickListener(this);
                dialog.show();
                break;
        }
    }

    @Override
    public void onClick(Dialog dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_NEGATIVE:

                break;
            case Dialog.BUTTON_POSITIVE:
                mPresenter.deleteEmp(empId);
                break;
        }
    }

    @Override
    public void empDeleteSuc() {
        //删除成功,关闭当前界面,回到上一个界面,同时发个消息过去,刷新界面
        UIUtils.showToast(UIUtils.getString(R.string.delete_success));
        //发一个消息出去
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTION_ACTIVITY_UPDATE));

        finish();
    }

    @Override
    public void empDeleteError(String msg) {
        if (!TextUtils.isEmpty(msg))
            UIUtils.showToast(msg);
        else UIUtils.showToast(UIUtils.getString(R.string.delete_fail));
    }

    @Override
    public void empModifySuc() {
        UIUtils.showToast(UIUtils.getString(R.string.modify_success));
        //用了一个position是想在回到列表界面后刷新数据后回到该位置(只做到这里,因为那边是重新请求了一遍数据,这个做起来要再考虑考虑吧)
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTION_ACTIVITY_UPDATE, position));

        finish();
    }

    @Override
    public void empModifyError(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            UIUtils.showToast(msg);
            return;
        }
        UIUtils.showToast(UIUtils.getString(R.string.modify_fail));
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
                    etSex.setText("男");
                    break;
                case 1:
                    sex = 2;//女
                    etSex.setText("女");
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
                    etPersonType.setText("民警");
                    break;
                case 1:
                    empType = 2;//职工
                    etPersonType.setText("职工");
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
                    etCertifType.setText("身份证");
                    CertifTypeName = "身份证";
                    break;
                case 1:
                    CertifType = 2;
                    etCertifType.setText("军官证");
                    CertifTypeName = "军官证";
                    break;
                case 2:
                    CertifType = 3;
                    etCertifType.setText("士兵证");
                    CertifTypeName = "士兵证";
                    break;
            }
        }
    }


}
