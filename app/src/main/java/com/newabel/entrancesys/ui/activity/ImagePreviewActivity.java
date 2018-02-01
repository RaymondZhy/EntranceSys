package com.newabel.entrancesys.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.newabel.entrancesys.R;
import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.service.entity.PhotoPreInfo;
import com.newabel.entrancesys.service.presenter.ImagePreviewPresenter;
import com.newabel.entrancesys.ui.adapter.ImagePreviewAdapter;
import com.newabel.entrancesys.ui.base.BaseActivity;
import com.newabel.entrancesys.ui.iview.ImagePreviewView;

import java.util.ArrayList;
import java.util.List;

public class ImagePreviewActivity extends BaseActivity<ImagePreviewPresenter> implements ImagePreviewView, View.OnClickListener, ViewPager.OnPageChangeListener {

    private LinearLayout ll_back;
    private LinearLayout ll_delete;
    private ViewPager vp_content;
    private List<ImageView> vList;
    private ImagePreviewAdapter adapter;
    private PhotoPreInfo mPhotoPreInfo;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_image_preview);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        receiveData();
//        initData();
//        initViews();
//        initListeners();

    }

    protected void receiveData() {
        mPhotoPreInfo = this.getIntent().getParcelableExtra(Constant.IMAGE_DATA);
        if(mPhotoPreInfo == null){
            mPhotoPreInfo = new PhotoPreInfo();
        }

        vList = getViews(mPhotoPreInfo.getList());
    }

    @Override
    protected void initView() {
        receiveData();

        ll_back = findViewById(R.id.ll_back);
        tv_title = findViewById(R.id.tv_title);
        ll_delete = findViewById(R.id.ll_delete);
        vp_content = findViewById(R.id.vp_content);

        adapter = new ImagePreviewAdapter(this,vList, mPhotoPreInfo.getList());
        vp_content.setAdapter(adapter);

        ll_delete.setVisibility(mPhotoPreInfo.isEditable() ? View.VISIBLE : View.INVISIBLE);
        if(mPhotoPreInfo.getList().size()>0){
            vp_content.setCurrentItem(mPhotoPreInfo.getCurrentItem());
            tv_title.setText(mPhotoPreInfo.getCurrentItem()+1 + "/" + mPhotoPreInfo.getList().size());
        }else{
            tv_title.setText(getString(R.string.title_activity_image_preview));
        }
    }

    @Override
    protected void initListener() {
        ll_back.setOnClickListener(this);
        ll_delete.setOnClickListener(this);
        vp_content.addOnPageChangeListener(this);
    }

    @Override
    protected ImagePreviewPresenter createPresenter() {
        return new ImagePreviewPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_image_preview;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                setResult();
                this.finish();
                break;
            case R.id.ll_delete:
                int currentItem = vp_content.getCurrentItem();
                vList.remove(currentItem);
                mPhotoPreInfo.getList().remove(currentItem);
                adapter.notifyDataSetChanged();
                tv_title.setText(mPhotoPreInfo.getList().size() > 0 ? vp_content.getCurrentItem()+1 + "/" + mPhotoPreInfo.getList().size() : getString(R.string.title_activity_image_preview));
                break;
        }
    }

    public List<ImageView> getViews(List<String> list){
        List<ImageView> vList = new ArrayList<>();
        for(int i = 0;i<list.size();i++){
            ImageView iv = new ImageView(this);
            vList.add(iv);
        }
        return vList;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int size = mPhotoPreInfo.getList().size();
        tv_title.setText(size > 0 ? position+1 + "/" + size : getString(R.string.title_activity_image_preview));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        setResult();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        setResult();
        super.onBackPressed();
    }

    private void setResult() {
        Intent intent = new Intent();
        intent.putExtra(Constant.IMAGE_DATA, mPhotoPreInfo);
        setResult(RESULT_OK,intent);
        finish();
    }
}
