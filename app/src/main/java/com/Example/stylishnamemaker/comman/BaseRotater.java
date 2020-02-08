package com.Example.stylishnamemaker.comman;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public abstract class BaseRotater {
    protected int mDegree;

    abstract void setmDegree();

    public Bitmap rotate(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.setRotate((float) this.mDegree, ((float) bitmap.getWidth()) / 2.0f, ((float) bitmap.getHeight()) / 2.0f);
        try {
            Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (bitmap == rotated) {
                return bitmap;
            }
            bitmap.recycle();
            return rotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public void destroy() {
    }
}
