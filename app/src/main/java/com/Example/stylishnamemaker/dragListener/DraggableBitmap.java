package com.Example.stylishnamemaker.dragListener;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class DraggableBitmap {
    private boolean activated;
    private Matrix currentMatrix = null;
    public Bitmap mBitmap;
    private int mId = -1;
    private Matrix marginMatrix;
    private Matrix savedMatrix = null;
    private boolean touched;

    public DraggableBitmap(Bitmap b) {
        this.mBitmap = b;
        this.activated = false;
    }

    public void setCurrentMatrix(Matrix m) {
        this.currentMatrix = null;
        this.currentMatrix = new Matrix(m);
    }

    public void setSavedMatrix(Matrix m) {
        this.savedMatrix = null;
        this.savedMatrix = new Matrix(m);
    }

    public Matrix getCurrentMatrix() {
        return this.currentMatrix;
    }

    public Matrix getSavedMatrix() {
        return this.savedMatrix;
    }

    public void activate() {
        this.activated = true;
    }

    public void deActivate() {
        this.activated = false;
    }

    public boolean isActivate() {
        return this.activated;
    }

    public boolean isTouched() {
        return this.touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public Matrix getMarginMatrix() {
        return this.marginMatrix;
    }

    public void setMarginMatrix(Matrix marginMatrix) {
        this.marginMatrix = null;
        this.marginMatrix = new Matrix(marginMatrix);
    }

    public int getmId() {
        return this.mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }
}
