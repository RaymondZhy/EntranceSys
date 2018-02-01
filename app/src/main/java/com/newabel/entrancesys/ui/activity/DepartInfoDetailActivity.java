package com.newabel.entrancesys.ui.activity;

import android.app.Dialog;
import android.content.Context;
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
import com.newabel.entrancesys.service.presenter.DeptModifyPresenter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.dialog.BottomDialog;
import com.newabel.entrancesys.ui.dialog.CommonDialog;
import com.newabel.entrancesys.ui.dialog.MenuDialog;
import com.newabel.entrancesys.ui.iview.DeptModifyView;
import com.newabel.entrancesys.ui.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 机构管理机构详情
 */
public class DepartInfoDetailActivity extends BaseActivity<DeptModifyPresenter>
        implements MenuDialog.OnClickListener, CommonDialog.OnClickListener, DeptModifyView {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_add)
    ImageView iv_add;

    @BindView(R.id.et_code)
    EditText etCode;

    @BindView(R.id.et_name)
    EditText etName;

    @BindView(R.id.tv_belong_dept_value)
    TextView tvBelongDept;

    @BindView(R.id.tv_depart_type_value)
    TextView tvDeptType;

    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private boolean isEditMode = false;//是否处于编辑状态,默认为否

    private Map<String, Object> deptInfo;
    private String mRoot;
    private String deptNum, deptName;
    private String deptTypeValue;//机构类型名称
    private int deptType;//机构类型

    private int MasterID, DeptID;//上级机构id,当前机构id

    private List<Map<String, Object>> deptTypeData;

    @Override
    protected DeptModifyPresenter createPresenter() {
        return new DeptModifyPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_depart_info_detail;
    }

    @Override
    protected void initView() {
        tvTitle.setText(R.string.text_dept_info);

        iv_add.setVisibility(View.VISIBLE);
        iv_add.setImageResource(R.mipmap.ic_more);
    }

    @Override
    protected void initData() {
        super.initData();
        mRoot = this.getIntent().getStringExtra("mRoot");
        String jStr = this.getIntent().getStringExtra("mData");

        if (jStr != null) {
            deptInfo = new Gson().fromJson(jStr, Map.class);
        }
        if (deptInfo != null) {
            deptNum = deptInfo.get("DeptNo").toString();
            deptName = deptInfo.get("DeptName").toString();
            deptType = Math.round(Float.valueOf(deptInfo.get("DeptType").toString()));
            MasterID = Math.round(Float.valueOf(deptInfo.get("MasterID").toString()));
            DeptID = Math.round(Float.valueOf(deptInfo.get("DeptID").toString()));
        }

        etCode.setText(deptNum);
        etName.setText(deptName);
        tvBelongDept.setText(mRoot);
        mPresenter.getDeptTypeData();

    }

    @OnClick({R.id.ll_back, R.id.iv_add, R.id.btn_submit, R.id.rl_dept_type})
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
                deptNum = etCode.getText().toString();
                deptName = etName.getText().toString();

                if (TextUtils.isEmpty(deptNum)) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_dept_num_isnull));
                    break;
                }

                if (TextUtils.isEmpty(deptName)) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_dept_name_isnull));
                    break;
                }

                if (deptType == 0) {
                    UIUtils.showToast(UIUtils.getString(R.string.warn_choose_dept_type));
                    break;
                }

                mPresenter.deptModify(DeptID, deptNum, MasterID, deptName, deptType);

                break;
            case R.id.rl_dept_type:
                if (isEditMode) {
                    BottomDialog bottomDialog = new BottomDialog(this, mPresenter.getDeptTypeData(deptType, deptTypeData));
                    bottomDialog.show();
                    bottomDialog.setOnClickListener(new DeptTypeClickListener());
                    break;
                }
        }
    }

    @Override
    public void onClick(PopupWindow window, int which) {
        switch (which) {
            case 0://编辑
                etName.setEnabled(true);
                etCode.setEnabled(true);
                btnSubmit.setVisibility(View.VISIBLE);
                isEditMode = true;
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
                if (DeptID != 0) {
                    mPresenter.deleteDept(DeptID);
                }

                break;
        }
    }

    class DeptTypeClickListener implements BottomDialog.OnClickListener {

        @Override
        public void onClick(Dialog dialog, int which) {
            deptType = Math.round(Float.valueOf(deptTypeData.get(which).get("TypeID").toString()));
            tvDeptType.setText(deptTypeData.get(which).get("TypeName").toString());
        }
    }

    @Override
    public void getDeptTypeDataSuc(List<Map<String, Object>> deptTypeData) {
        if (deptTypeData != null) {
            this.deptTypeData = deptTypeData;
        }

        for (Map<String, Object> mapListItem : this.deptTypeData) {
            int deptTypeInMapList = Math.round(Float.valueOf(mapListItem.get("TypeID").toString()));

            if (deptType == deptTypeInMapList) {
                deptTypeValue = mapListItem.get("TypeName").toString();
                break;
            }
        }

        if (!TextUtils.isEmpty(deptTypeValue)) {
            tvDeptType.setText(deptTypeValue);
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
    public void modifyDeptSuc() {
        UIUtils.showToast(UIUtils.getString(R.string.modify_success));
        //新增机构成功,发送一个消息出去
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTION_ACTIVITY_UPDATE));

        finish();
    }

    @Override
    public void modifyDeptError(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            UIUtils.showToast(msg);
        } else {
            UIUtils.showToast(UIUtils.getString(R.string.modify_fail));
        }
    }

    @Override
    public void deleteDeptSuc() {
        UIUtils.showToast(UIUtils.getString(R.string.delete_success));
        //新增机构成功,发送一个消息出去
        EventBus.getDefault().post(new MessageEvent(MessageEvent.ACTION_ACTIVITY_UPDATE));

        finish();
    }

    @Override
    public void deleteDeptError(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            UIUtils.showToast(msg);
        } else {
            UIUtils.showToast(UIUtils.getString(R.string.delete_fail));
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}
