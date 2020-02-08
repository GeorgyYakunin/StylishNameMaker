package com.Example.stylishnamemaker.comman;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.Example.stylishnamemaker.R;

import java.io.File;
import java.io.FileInputStream;

public class BitmapScaler {
    public static Bitmap scaleToFitWidth(Bitmap b, int width) {
        return Bitmap.createScaledBitmap(b, width, (int) (((float) b.getHeight()) * (((float) width) / ((float) b.getWidth()))), true);
    }

    public static Bitmap scaleToFitHeight(Bitmap b, int height) {
        return Bitmap.createScaledBitmap(b, (int) (((float) b.getWidth()) * (((float) height) / ((float) b.getHeight()))), height, true);
    }

    public static Bitmap makeTransparent(Bitmap src) {
        Bitmap transBitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Config.RGB_565);
        Canvas canvas = new Canvas(transBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawBitmap(src, 0.0f, 0.0f, new Paint(2));
        return transBitmap;
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / ((float) width);
        float scaleHeight = ((float) newHeight) / ((float) height);
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return makeTransparent(Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true));
    }

    public static Bitmap decodeFile(File f, int req_Height, int req_Width) {
        try {
            Options o1 = new Options();
            o1.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o1);
            int width_tmp = o1.outWidth;
            int height_tmp = o1.outHeight;
            int scale = 1;
            if (width_tmp > req_Width || height_tmp > req_Height) {
                int heightRatio = Math.round(((float) height_tmp) / ((float) req_Height));
                int widthRatio = Math.round(((float) width_tmp) / ((float) req_Width));
                if (heightRatio < widthRatio) {
                    scale = heightRatio;
                } else {
                    scale = widthRatio;
                }
            }
            Options o2 = new Options();
            o2.inSampleSize = scale;
            o2.inPurgeable = true;
            o2.inScaled = false;
            return BitmapFactory.decodeFile(f.getAbsolutePath(), o2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DisplayImageOptions getDisplayImageOptions(boolean isCache) {
        return new Builder().showImageOnLoading(R.drawable.ic_empty).showImageForEmptyUri(R.drawable.ic_empty).showImageOnFail(R.drawable.ic_empty).cacheInMemory(isCache).cacheOnDisk(isCache).considerExifParams(true).bitmapConfig(Config.RGB_565).build();
    }
}
