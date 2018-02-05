package com.newabel.entrancesys.ui.adapter;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newabel.entrancesys.R;

import java.util.List;
import java.util.Map;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2018/2/2 0002.
 */

public class ManageAdapter extends BaseQuickAdapter<Map<String, Object>, BaseViewHolder> {

    public ManageAdapter(int layoutResId, @Nullable List<Map<String, Object>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, Object> item) {
        BluetoothDevice device = (BluetoothDevice) item.get("device");
        helper.setText(R.id.tv_name, TextUtils.isEmpty(device.getName()) ? device.getAddress() : device.getName());
        helper.setText(R.id.tv_rssi, item.get("distance").toString());
    }
}
