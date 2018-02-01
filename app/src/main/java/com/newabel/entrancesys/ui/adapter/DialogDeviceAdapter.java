package com.newabel.entrancesys.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;

import java.util.List;
import java.util.Map;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/18 0018.
 */

public class DialogDeviceAdapter extends BaseQuickAdapter<Map<String, Object>, BaseViewHolder> {

    private SparseArray<Integer> layouts;
    private int mLayoutType = 0;

    public DialogDeviceAdapter(@Nullable List<Map<String, Object>> data, int mLayoutType) {
        super(data);
        this.mLayoutType = mLayoutType;
        addItemType();
    }


//    @Override
//    public int getItemViewType(int position) {
//        return mLayoutType;
//    }


    @Override
    public int getItemViewType(int position) {
        if (mData.size() > 0) {
            return mLayoutType;
        } else {
            return super.getItemViewType(position);
        }
    }

//    @Override
//    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
//        return createBaseViewHolder(parent, layouts.get(viewType));
//    }


    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        if (mData.size() > 0) {
            return createBaseViewHolder(parent, layouts.get(viewType));
        } else {
            return super.onCreateDefViewHolder(parent, viewType);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, Object> item) {
        switch (helper.getItemViewType()) {
            case Constant.MODULE_ENTRANCE_DATA: {
                String zoneName = item.get("PlaceName") == null ? "" : item.get("PlaceName").toString();
                String doorName = item.get("DoorName") == null ? "" : item.get("DoorName").toString();
                if (!TextUtils.isEmpty(zoneName)) {
                    ((ImageView) helper.getView(R.id.iv_image)).setImageResource(R.mipmap.ic_me_message_setting);
                    helper.setText(R.id.tv_name, zoneName);
                }
                if (!TextUtils.isEmpty(doorName)) {
                    ((ImageView) helper.getView(R.id.iv_image)).setImageResource(R.mipmap.ic_me_pwd);
                    helper.setText(R.id.tv_name, doorName);
                }
                break;
            }
            case Constant.MODULE_ENTRANCE_EVENT: {
                String zoneName = item.get("PlaceName") == null ? "" : item.get("PlaceName").toString();
                String doorName = item.get("DoorName") == null ? "" : item.get("DoorName").toString();
                if (!TextUtils.isEmpty(zoneName)) {
                    ((ImageView) helper.getView(R.id.iv_image)).setImageResource(R.mipmap.ic_me_message_setting);
                    helper.setText(R.id.tv_name, zoneName);
                }
                if (!TextUtils.isEmpty(doorName)) {
                    ((ImageView) helper.getView(R.id.iv_image)).setImageResource(R.mipmap.ic_me_pwd);
                    helper.setText(R.id.tv_name, doorName);
                }
                break;
            }
            case Constant.MODULE_ENTRANCE_CONTROL:{
                String zoneName = item.get("PlaceName") == null ? "" : item.get("PlaceName").toString();
                String doorName = item.get("DoorName") == null ? "" : item.get("DoorName").toString();
                if (!TextUtils.isEmpty(zoneName)) {
                    ((ImageView) helper.getView(R.id.iv_image)).setImageResource(R.mipmap.ic_me_message_setting);
                    helper.setText(R.id.tv_name, zoneName);
                }
                if (!TextUtils.isEmpty(doorName)) {
                    ((ImageView) helper.getView(R.id.iv_image)).setImageResource(R.mipmap.ic_me_pwd);
                    helper.setText(R.id.tv_name, doorName);
                }
                break;
            }
            case Constant.MODULE_ZONE_MANAGE:{
                String zoneName = item.get("PlaceName") == null ? "" : item.get("PlaceName").toString();
                String doorName = item.get("DoorName") == null ? "" : item.get("DoorName").toString();
                if (!TextUtils.isEmpty(zoneName)) {
                    ((ImageView) helper.getView(R.id.iv_image)).setImageResource(R.mipmap.ic_me_message_setting);
                    helper.setText(R.id.tv_name, zoneName);
                }
                if (!TextUtils.isEmpty(doorName)) {
                    ((ImageView) helper.getView(R.id.iv_image)).setImageResource(R.mipmap.ic_me_pwd);
                    helper.setText(R.id.tv_name, doorName);
                }
                break;
            }
            case Constant.MODULE_DEVICE_DATA:{
                String zoneName = item.get("PlaceName") == null ? "" : item.get("PlaceName").toString();
                String doorName = item.get("DevName") == null ? "" : item.get("DevName").toString();
                if (!TextUtils.isEmpty(zoneName)) {
                    ((ImageView) helper.getView(R.id.iv_image)).setImageResource(R.mipmap.ic_me_message_setting);
                    helper.setText(R.id.tv_name, zoneName);
                }
                if (!TextUtils.isEmpty(doorName)) {
                    ((ImageView) helper.getView(R.id.iv_image)).setImageResource(R.mipmap.ic_me_pwd);
                    helper.setText(R.id.tv_name, doorName);
                }
                break;
            }
            case Constant.MODULE_DEPARTMENT_MANAGE:

                String deptName = item.get("DeptName") == null ? "" : item.get("DeptName").toString();
//                String doorName = item.get("DoorName") == null ? "" : item.get("DoorName").toString();
                if (!TextUtils.isEmpty(deptName)) {
                    ((ImageView) helper.getView(R.id.iv_image)).setImageResource(R.mipmap.ic_me_message_setting);
                    helper.setText(R.id.tv_name, deptName);
                }
//                if(!TextUtils.isEmpty(doorName)){
//                    ((ImageView) helper.getView(R.id.iv_image)).setImageResource(R.mipmap.ic_me_pwd);
//                    helper.setText(R.id.tv_name, doorName);
//                }
                break;
            case Constant.MODULE_EMPLOYEE_MANAGE:
                String deptName2 = item.get("DeptName") == null ? "" : item.get("DeptName").toString();
                String empName = item.get("EmpName") == null ? "" : item.get("EmpName").toString();
                if (!TextUtils.isEmpty(deptName2)) {
                    ((ImageView) helper.getView(R.id.iv_image)).setImageResource(R.mipmap.ic_me_message_setting);
                    helper.setText(R.id.tv_name, deptName2);
                }
                if (!TextUtils.isEmpty(empName)) {
                    ((ImageView) helper.getView(R.id.iv_image)).setImageResource(R.mipmap.ic_me_pwd);
                    helper.setText(R.id.tv_name, empName);
                }


                break;
        }
    }

    private void addItemType() {
        layouts = new SparseArray<>();
        layouts.put(Constant.MODULE_ENTRANCE_DATA, R.layout.item_moudule_entrance_data);
        layouts.put(Constant.MODULE_ENTRANCE_EVENT, R.layout.item_moudule_entrance_data);
        layouts.put(Constant.MODULE_ENTRANCE_CONTROL, R.layout.item_moudule_entrance_data);
        layouts.put(Constant.MODULE_ZONE_MANAGE, R.layout.item_moudule_entrance_data);
        layouts.put(Constant.MODULE_DEVICE_DATA, R.layout.item_moudule_entrance_data);
        layouts.put(Constant.MODULE_DEPARTMENT_MANAGE, R.layout.item_moudule_entrance_data);
        layouts.put(Constant.MODULE_EMPLOYEE_MANAGE, R.layout.item_moudule_entrance_data);
    }

    public void setLayoutType(int type) {
        this.mLayoutType = type;
    }
}
