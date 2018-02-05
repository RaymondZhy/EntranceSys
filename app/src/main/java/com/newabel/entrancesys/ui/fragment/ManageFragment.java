package com.newabel.entrancesys.ui.fragment;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.MessageEvent;
import com.newabel.entrancesys.service.presenter.BasePresenter;
import com.newabel.entrancesys.ui.adapter.ManageAdapter;
import com.newabel.entrancesys.ui.base.BaseFragment;
import com.newabel.entrancesys.ui.receiver.BlueToothReceiver;
import com.newabel.entrancesys.ui.utils.LogUtil;
import com.newabel.entrancesys.ui.utils.PermissionUtils;
import com.newabel.entrancesys.ui.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


public class ManageFragment extends BaseFragment implements BlueToothReceiver.BluetoothWatcher, BaseQuickAdapter.OnItemClickListener {

    private final int REQUEST_CODE_PERMISSION = 101;
    private BlueToothReceiver mBlueToothReceiver;
    private List<Map<String, Object>> mList;

    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    private ManageAdapter adapter;
    private Handler mHandler;

    public ManageFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBlueToothReceiver = new BlueToothReceiver(getContext());
        mBlueToothReceiver.setBluetoothWatcher(this);
        mBlueToothReceiver.registerReceiver();
        mBlueToothReceiver.startDiscovery();

        String[] permissions = {Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (!PermissionUtils.hasPermissions(getActivity(), permissions)) {
            PermissionUtils.requestPermissions(getActivity(), permissions, REQUEST_CODE_PERMISSION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (!PermissionUtils.hasPermissions(grantResults)) {
            UIUtils.showToast(getString(R.string.permission_str));
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void initData() {
        super.initData();
        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        adapter = new ManageAdapter(R.layout.item_manage, mList);
        rv_list.setAdapter(adapter);
    }


    @Override
    protected void initListener() {
        super.initListener();
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_manage;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBlueToothReceiver.cancelDiscovery();
        mBlueToothReceiver.unRegisterReceiver();
    }


    @Override
    public void actionFound(BluetoothDevice device, int rssi) {
        Map<String, Object> map = new HashMap<>();
        map.put("device", device);
//        map.put("distance",mBlueToothReceiver.getDistance(rssi) +"米");
        map.put("distance", rssi);
        mList.add(map);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void actionFinished() {
//        UIUtils.showToast("扫描完毕");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        try {
            mBlueToothReceiver.cancelDiscovery();
            BluetoothDevice device = (BluetoothDevice) mList.get(position).get("device");
//            mBlueToothReceiver.getBluetoothSocket((BluetoothDevice) mList.get(position).get("device"));
            if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                mBlueToothReceiver.connect(device);
                UIUtils.showToast("正在建立链接...");
            } else {
                mBlueToothReceiver.createBound(device);
                UIUtils.showToast("正在配对...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
