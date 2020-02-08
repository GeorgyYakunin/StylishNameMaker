package com.Example.stylishnamemaker.data;

import android.graphics.Bitmap;

public class DataClass {
    public String arrayListEditText = null;
    public Bitmap hashBitmap;

    public DataClass(Bitmap bitmap, String arr) {
        this.hashBitmap = bitmap;
        this.arrayListEditText = arr;
    }
}
