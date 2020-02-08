package com.Example.stylishnamemaker.comman;

import android.graphics.Bitmap;

public class CWRotater extends BaseRotater {
    void setmDegree() {
        this.mDegree = 90;
    }

    public Bitmap rotate(Bitmap bitmap) {
        setmDegree();
        return super.rotate(bitmap);
    }
}
