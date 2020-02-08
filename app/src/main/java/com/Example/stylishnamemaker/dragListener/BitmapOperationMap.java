package com.Example.stylishnamemaker.dragListener;

import android.graphics.Matrix;

public class BitmapOperationMap {
    DraggableBitmap mBitmap;
    Matrix mOperationMtx;
    OPERATION mOpt;

    public enum OPERATION {
        NEW,
        ADD,
        DELETE
    }

    public BitmapOperationMap(DraggableBitmap bmp, Matrix mtx, OPERATION op) {
        this.mBitmap = bmp;
        this.mOperationMtx = mtx;
        this.mOpt = op;
    }

    public DraggableBitmap getDraggableBitmap() {
        return this.mBitmap;
    }

    public Matrix getOperationMatrix() {
        return this.mOperationMtx;
    }

    public OPERATION getOption() {
        return this.mOpt;
    }
}
