package com.stepone.component.image;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * FileName: MediaFetcher
 * Author: y.liang
 * Date: 2019-12-27 10:00
 */

public class MediaFetcher {
    private final static String TAG = "MediaFetcher";
    private final String[] projection = new String[]{
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media._ID
    };
    public void fetch(@NonNull final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentResolver resolver = context.getContentResolver();
                Cursor cursor = context.getContentResolver()
                        .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                                null, null, MediaStore.Images.Media.DATE_ADDED);
                int size = cursor.getCount();
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        Log.e(TAG, "title = " + cursor.getString(0) +
                                " id = " + cursor.getInt(1));
                    } while (cursor.moveToNext());

                    cursor.close();
                }
            }
        }).start();

    }
}
