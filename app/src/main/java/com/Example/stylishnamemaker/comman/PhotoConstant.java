package com.Example.stylishnamemaker.comman;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Process;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PhotoConstant {
    public static final int CAMERA_REQUEST = 200;
    public static final int CROP_REQUEST = 203;
    public static final String CROP_TEMP = "IstorePath";
    public static final int GALLERY_REQUEST = 201;
    public static final String TEMP_FILE_JPG = "/tmp_file.png";
    public static final int TRANSFORM_REQUEST = 202;
    private static Bitmap bitmap;

    public static void insertBitmap(Bitmap bitmap1) {
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
        bitmap = bitmap1;
    }

    public static Bitmap getBitmap() {
        return bitmap.copy(Config.ARGB_8888, true);
    }

    public static void errorAlert(Context context) {
        Builder builder = new Builder(context);
        builder.setTitle("Error");
        builder.setMessage("Application is runnig out of memory. \nPlease restart application to continue..");
        builder.setNegativeButton("Restart", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ImageLoader.getInstance().clearMemoryCache();
                ImageLoader.getInstance().clearDiskCache();
                Process.killProcess(Process.myPid());
            }
        });
        builder.create().show();
    }
}
