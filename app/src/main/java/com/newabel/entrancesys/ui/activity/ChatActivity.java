package com.newabel.entrancesys.ui.activity;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.presenter.ChatPresenter;
import com.newabel.entrancesys.ui.adapter.ChatAdapter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.ChatView;
import com.newabel.entrancesys.ui.BlueTooth.BlueToothReceiver;
import com.newabel.entrancesys.ui.BlueTooth.BlueToothThread;
import com.newabel.entrancesys.ui.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends BaseActivity<ChatPresenter> implements ChatView, View.OnClickListener, Handler.Callback {

    private Button btn_send;
    private LinearLayout ll_back;
    private TextView tv_title;
    private EditText et_input;
    private RecyclerView rv_list;
    private List<Map<String, Object>> list;
    private ChatAdapter chatAdapter;
    private LinearLayout ll_more;
    private BlueToothReceiver mBlueToothReceiver;
    private BluetoothDevice mDevice;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
        initViews();
        initListeners();
    }

    @Override
    protected ChatPresenter createPresenter() {
        return new ChatPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_chat;
    }

    private void initViews() {
        mDevice = this.getIntent().getParcelableExtra("BLUETOOTH_DEVICE");

        ll_back = findViewById(R.id.ll_back);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("蓝牙通信");
        btn_send = findViewById(R.id.btn_send);
        et_input = findViewById(R.id.et_input);
        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        chatAdapter = new ChatAdapter(list);
        rv_list.setAdapter(chatAdapter);

        mBlueToothReceiver = new BlueToothReceiver(this);

        mHandler = new Handler(this);

        BlueToothThread mBlueToothThread = new BlueToothThread();
        mBlueToothThread.setHandler(mHandler);
        mBlueToothThread.start();
    }

    private void initListeners() {
        ll_back.setOnClickListener(this);
        btn_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.btn_send:
                if (TextUtils.isEmpty(et_input.getText().toString().trim())) {
                    UIUtils.showToast("发送消息不能为空");
                    return;
                }
                try {
                    mBlueToothReceiver.sendData(mDevice, et_input.getText().toString().trim());
                    Map<String, Object> map = new HashMap<>();
                    map.put("publisher", 2);
                    map.put("body", et_input.getText().toString().trim());
                    list.add(map);
                    chatAdapter.notifyDataSetChanged();
                    et_input.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean handleMessage(Message msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("publisher", 1);
        map.put("body", msg.obj.toString());
        list.add(map);
        chatAdapter.notifyDataSetChanged();
        return false;
    }
}
