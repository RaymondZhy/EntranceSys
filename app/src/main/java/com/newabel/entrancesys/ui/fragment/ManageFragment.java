package com.newabel.entrancesys.ui.fragment;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.BasePresenter;
import com.newabel.entrancesys.ui.adapter.ManageAdapter;
import com.newabel.entrancesys.ui.base.BaseFragment;
import com.newabel.entrancesys.ui.BlueTooth.BlueToothReceiver;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


public class ManageFragment extends BaseFragment implements BlueToothReceiver.BluetoothWatcher, BaseQuickAdapter.OnItemClickListener {

    private final int REQUEST_CODE_PERMISSION = 101;
    private BlueToothReceiver mBlueToothReceiver;
    private List<Map<String, Object>> mList;

    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    @BindView(R.id.tv_send)
    TextView tv_send;

    private ManageAdapter adapter;

    public ManageFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mBlueToothReceiver = new BlueToothReceiver(getContext());
//        mBlueToothReceiver.setBluetoothWatcher(this);
//        mBlueToothReceiver.registerReceiver();
//        mBlueToothReceiver.startDiscovery();
//
//        String[] permissions = {Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
//        if (!PermissionUtils.hasPermissions(getActivity(), permissions)) {
//            PermissionUtils.requestPermissions(getActivity(), permissions, REQUEST_CODE_PERMISSION);
//        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (!PermissionUtils.hasPermissions(grantResults)) {
//            UIUtils.showToast(getString(R.string.permission_str));
//        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void initData() {
        super.initData();
//        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
//        mList = new ArrayList<>();
//        adapter = new ManageAdapter(R.layout.item_manage, mList);
//        rv_list.setAdapter(adapter);
    }


    @Override
    protected void initListener() {
        super.initListener();
//        adapter.setOnItemClickListener(this);
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
//        mBlueToothReceiver.cancelDiscovery();
//        mBlueToothReceiver.unRegisterReceiver();
    }

    @OnClick({R.id.tv_send})
    public void onClick(View v) {
//        mList.clear();
//        adapter.notifyDataSetChanged();
//        mBlueToothReceiver.cancelDiscovery();
//        mBlueToothReceiver.startDiscovery();
    }

    @Override
    public void actionFound(BluetoothDevice device, int rssi) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("device", device);
////        map.put("distance",mBlueToothReceiver.getDistance(rssi) +"米");
//        map.put("distance", rssi);
//        mList.add(map);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void actionFinished() {
//        UIUtils.showToast("扫描完毕");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        try {
//            mBlueToothReceiver.cancelDiscovery();
//            BluetoothDevice device = (BluetoothDevice) mList.get(position).get("device");
////            mBlueToothReceiver.getBluetoothSocket((BluetoothDevice) mList.get(position).get("device"));
//            Intent intent = new Intent(getContext(), BluetoothChatActivity.class);
//            intent.putExtra("BLUETOOTH_DEVICE",device);
//            if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
//                startActivity(intent);
//            } else {
//                if(mBlueToothReceiver.createBound(device)){
//                    startActivity(intent);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
