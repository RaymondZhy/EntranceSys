package com.newabel.entrancesys.service.presenter;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.newabel.entrancesys.R;
import com.newabel.entrancesys.service.entity.PhotoDirectory;
import com.newabel.entrancesys.app.Loader.PhotoDirectoryLoader;
import com.newabel.entrancesys.ui.iview.PhotoPickerView;

import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME;
import static android.provider.MediaStore.Images.ImageColumns.BUCKET_ID;
import static android.provider.MediaStore.MediaColumns.DATA;
import static android.provider.MediaStore.MediaColumns.DATE_ADDED;
import static android.provider.MediaStore.MediaColumns.SIZE;

/**
 * DESCR:
 * AUTHOR:zhangyue
 * DATE: 2017/11/20 0020.
 */

public class PhotoPickerPresenter extends BasePresenter<PhotoPickerView> implements LoaderManager.LoaderCallbacks<Cursor> {


    public PhotoPickerPresenter(PhotoPickerView view) {
        super(view);
    }

    public void getPhotoDirs(AppCompatActivity context, boolean isShowGif){
        Bundle args = new Bundle();
        args.putBoolean("IS_SHOW_GIF",isShowGif);
        context.getSupportLoaderManager().initLoader(0,args,this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new PhotoDirectoryLoader(mView.getContext(),args.getBoolean("IS_SHOW_GIF",false));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {
        if (data == null)  return;
        List<PhotoDirectory> directories = new ArrayList<>();
        PhotoDirectory photoDirectoryAll = new PhotoDirectory();
        photoDirectoryAll.setName(mView.getContext().getString(R.string.photo_picker_activity_str_3));
        photoDirectoryAll.setId("ALL");

        while (data.moveToNext()) {

//            int imageId  = data.getInt(data.getColumnIndexOrThrow(_ID));
            String bucketId = data.getString(data.getColumnIndexOrThrow(BUCKET_ID));
            String name = data.getString(data.getColumnIndexOrThrow(BUCKET_DISPLAY_NAME));
            String path = data.getString(data.getColumnIndexOrThrow(DATA));
            long size = data.getInt(data.getColumnIndexOrThrow(SIZE));

            if (size < 1) continue;

            PhotoDirectory photoDirectory = new PhotoDirectory();
            photoDirectory.setId(bucketId);
            photoDirectory.setName(name);

            if (!directories.contains(photoDirectory)) {
                photoDirectory.setCoverPath(path);
                photoDirectory.addPhoto(path);
                photoDirectory.setDateAdded(data.getLong(data.getColumnIndexOrThrow(DATE_ADDED)));
                directories.add(photoDirectory);
            } else {
                directories.get(directories.indexOf(photoDirectory)).addPhoto(path);
            }

            photoDirectoryAll.addPhoto(path);
        }
        if (photoDirectoryAll.getPhotos().size() > 0) {
            photoDirectoryAll.setCoverPath(photoDirectoryAll.getPhotos().get(0));
        }
        directories.add(0, photoDirectoryAll);
        mView.updateData(directories);
    }

}
