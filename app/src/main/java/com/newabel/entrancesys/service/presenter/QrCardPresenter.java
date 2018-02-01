package com.newabel.entrancesys.service.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import com.newabel.entrancesys.ui.iview.QrCardView;
import com.newabel.entrancesys.ui.utils.FileUtils;
import com.newabel.entrancesys.ui.utils.LogUtil;
import com.newabel.entrancesys.ui.utils.UIUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/12/8 0008.
 */

public class QrCardPresenter extends BasePresenter<QrCardView> {

    private String TAG = "QrCardPresenter";

    public QrCardPresenter(QrCardView view) {
        super(view);
    }


    public void saveBitmap(Context context, Bitmap bitmap){
        File file = new File(FileUtils.PATH_DCIM,System.currentTimeMillis()+".png");
        while (file.exists()){
            file = new File(FileUtils.PATH_DCIM,System.currentTimeMillis()+".png");
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            UIUtils.scanFile(context,file);
        }catch (Exception e){
            LogUtil.e(TAG,e.getMessage());
        }finally {
            if(os!=null) {
                try {
                    os.close();
                } catch (IOException e) {
                    LogUtil.e(TAG,e.getMessage());
                }
            }
        }

    }
}
