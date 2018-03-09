package com.newabel.entrancesys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.helper.Realm.SearchRecord;
import com.newabel.entrancesys.service.presenter.SearchRecordPresenter;
import com.newabel.entrancesys.ui.adapter.SearchRecordAdapter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.SearchRecordView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchRecordActivity extends BaseActivity<SearchRecordPresenter> implements SearchRecordView, View.OnClickListener, TextWatcher, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.rv_record)
    RecyclerView rv_record;

    @BindView(R.id.et_keyword)
    EditText et_keyword;

    @BindView(R.id.ll_close)
    LinearLayout ll_close;

    @BindView(R.id.tv_cancel)
    TextView tv_cancel;

    private List<SearchRecord> rList;
    private SearchRecordAdapter recordAdapter;
    private String mark;
    private String hint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search_record);
    }

    @Override
    protected SearchRecordPresenter createPresenter() {
      return  new SearchRecordPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_search_record;
    }

    @Override
    protected void initView() {
        super.initView();
        ll_close.setVisibility(View.INVISIBLE);

        rv_record.setLayoutManager(new LinearLayoutManager(this));
        rList = new ArrayList<>();
        recordAdapter = new SearchRecordAdapter(R.layout.item_search_record, rList, mPresenter);
        rv_record.setAdapter(recordAdapter);
    }

    @Override
    protected void initData() {
        mark = this.getIntent().getStringExtra("mark");
        mark = mark == null ? "" : mark;
        hint = this.getIntent().getStringExtra("hint");
        hint = hint == null ? getString(R.string.search_record_str_1) : hint;

        et_keyword.setHint(hint);
        rList.addAll(mPresenter.getRecords(mark,et_keyword.getText().toString()));
        recordAdapter.notifyDataSetChanged();

    }

    @Override
    protected void initListener() {
        super.initListener();
        ll_close.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        et_keyword.addTextChangedListener(this);
        recordAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_close:
                et_keyword.setText("");
                break;
            case R.id.tv_cancel:
                String keyword = et_keyword.getText().toString();
                if(TextUtils.isEmpty(keyword)) {
                    this.finish();
                }else {
                    if(mPresenter.getRecordCount(mark)>=mPresenter.maxCount){
                        mPresenter.deleteOldestRecord(mark);
                    }
                    mPresenter.insert(mark, keyword);
                    setResult(keyword);
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String input = et_keyword.getText().toString();
        rList.clear();
        rList.addAll(mPresenter.getRecords(mark,input));
        recordAdapter.notifyDataSetChanged();
        if(TextUtils.isEmpty(input)){
            tv_cancel.setText(getString(R.string.search_record_str_3));
            ll_close.setVisibility(View.INVISIBLE);
        }else{
            tv_cancel.setText(getString(R.string.search_record_str_2));
            ll_close.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String keyword = rList.get(position).getKeyword();
        if(mPresenter.getRecordCount(mark)>=mPresenter.maxCount){
            mPresenter.deleteOldestRecord(mark);
        }
        mPresenter.insert(mark, keyword);
        setResult(keyword);
    }

    private void setResult(String keyword) {
        Intent intent = new Intent();
        intent.putExtra("keyword",keyword);
        setResult(RESULT_OK,intent);
        this.finish();
    }
}
