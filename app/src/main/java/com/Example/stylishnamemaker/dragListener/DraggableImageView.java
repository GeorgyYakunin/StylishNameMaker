package com.Example.stylishnamemaker.dragListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;

public class DraggableImageView extends ImageView {
    private static final String TAG = "Draggable Bitmap";
    private DraggableBitmap mActiveBitmap = null;
    private boolean mDrawOpacityBackground = false;
    private RectF mInnerImageBounds = null;
    private Stack<BitmapOperationMap> mOperationStack = new Stack();
    private List<DraggableBitmap> mOverlayBitmaps;
    private Paint mPaint = new Paint();
    private OnTouchListener touchListener = new OnTouchListener() {
        // to get mode [drag, zoom, rotate]
        private EDITMODE mEditMode = EDITMODE.NONE;

        private float[] mLastEvent;
        private PointF mStart = new PointF();
        private PointF mMid = new PointF();
        private float mOldDistance;
        private float mNewRotation = 0f;
        private float mDist = 0f;

        // this variable use to deal with android odd touch behavior (MOVE -> UP
        // -> MOVE -> UP)
        private boolean touchMoveEndChecker = false;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            // switch finger events
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case (MotionEvent.ACTION_DOWN):
                    touchMoveEndChecker = true;
                    mDrawOpacityBackground = true;
                    int activebmpIdx = getActiveBitmap(event.getX(), event.getY());

                    if (activebmpIdx != -1) {
                        mActiveBitmap = mOverlayBitmaps.get(activebmpIdx);
                        rearrangeOverlayList();
                    } else {
                        mActiveBitmap = null;
                        break;
                    }
                    mLastEvent = null;
                    mEditMode = EDITMODE.DRAG;
                    mStart.set(event.getX(), event.getY());

                    if (mActiveBitmap != null) {
                        mActiveBitmap.setSavedMatrix(mActiveBitmap.getCurrentMatrix());
                    }
                    break;

                case (MotionEvent.ACTION_POINTER_DOWN):
                    touchMoveEndChecker = false;
                    mDrawOpacityBackground = true;
                    if (mActiveBitmap != null) {
                        mOldDistance = spacing(event);
                        if (mOldDistance > 10f) {
                            mActiveBitmap.setSavedMatrix(mActiveBitmap.getCurrentMatrix());
                            midPoint(mMid, event);
                            mEditMode = EDITMODE.ZOOM;
                        }

                        mLastEvent = new float[4];
                        mLastEvent[0] = event.getX(0);
                        mLastEvent[1] = event.getX(1);
                        mLastEvent[2] = event.getY(0);
                        mLastEvent[3] = event.getY(1);

                        mDist = rotation(event);
                    }
                    break;

                case (MotionEvent.ACTION_POINTER_UP):
                    mEditMode = EDITMODE.NONE;
                    break;

                case (MotionEvent.ACTION_MOVE):
                    touchMoveEndChecker = false;
                    mDrawOpacityBackground = true;

                    if (mActiveBitmap != null) {
                        if (mEditMode == EDITMODE.DRAG) {
                            mActiveBitmap.setCurrentMatrix(mActiveBitmap.getSavedMatrix());
                            mActiveBitmap.getCurrentMatrix().postTranslate(event.getX() - mStart.x,
                                    event.getY() - mStart.y);
                        } else if (mEditMode == EDITMODE.ZOOM && event.getPointerCount() == 2) {
                            float newDistance = spacing(event);
                            mActiveBitmap.setCurrentMatrix(mActiveBitmap.getSavedMatrix());
                            if (newDistance > 10f) {
                                float scale = newDistance / mOldDistance;
                                mActiveBitmap.getCurrentMatrix()
                                        .postScale(scale, scale, mMid.x, mMid.y);
                            }

                            if (mLastEvent != null) {
                                mNewRotation = rotation(event);
                                float r = mNewRotation - mDist;
                                RectF rec = new RectF(0, 0, mActiveBitmap.mBitmap.getWidth(),
                                        mActiveBitmap.mBitmap.getHeight());
                                mActiveBitmap.getCurrentMatrix().mapRect(rec);
                                mActiveBitmap.getCurrentMatrix().postRotate(r,
                                        rec.left + rec.width() / 2, rec.top + rec.height() / 2);
                            }
                        }

                    }

                case (MotionEvent.ACTION_UP):
                    if (touchMoveEndChecker) { // means 2 continuous ACTION_UP, or
                        // real finger up after moving
                        mDrawOpacityBackground = false;
                        if (mActiveBitmap != null) {
                            // push a map to bitmap and clone of current matrix
                            mOperationStack
                                    .push(new BitmapOperationMap(mActiveBitmap, new Matrix(
                                            mActiveBitmap.getCurrentMatrix()),
                                            BitmapOperationMap.OPERATION.ADD));
                            mActiveBitmap.deActivate();
                        }
                    }
                    touchMoveEndChecker = true;
                default:
                    break;
            }

            invalidate();
            return true;
        }

    };


        public enum EDITMODE {
        NONE,
        DRAG,
        ZOOM,
        ROTATE
    }

    public DraggableImageView(Context context) {
        super(context);
        initMembers();
        setOnTouchListener(this.touchListener);
    }

    public DraggableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMembers();
        setOnTouchListener(this.touchListener);
    }

    private void initMembers() {
        this.mOverlayBitmaps = new ArrayList();
    }

    public void removeAll() {
        this.mOverlayBitmaps.clear();
        this.mOverlayBitmaps = new ArrayList();
        invalidate();
    }

    public int addOverlayBitmap(DraggableBitmap dBitmap) {
        Matrix marginMtx = new Matrix();
        marginMtx.postTranslate(200.0f, 200.0f);
        dBitmap.setMarginMatrix(marginMtx);
        Matrix curMtx = new Matrix();
        curMtx.postConcat(marginMtx);
        dBitmap.setCurrentMatrix(curMtx);
        this.mOperationStack.push(new BitmapOperationMap(dBitmap, null, BitmapOperationMap.OPERATION.NEW));
        this.mOperationStack.push(new BitmapOperationMap(dBitmap, dBitmap.getCurrentMatrix(), BitmapOperationMap.OPERATION.ADD));
        dBitmap.setmId(this.mOverlayBitmaps.size());
        this.mOverlayBitmaps.add(dBitmap);
        invalidate();
        return dBitmap.getmId();
    }

    private int getActiveBitmap(float event_x, float event_y) {
        int size = this.mOverlayBitmaps.size();
        int retidx = -1;
        DraggableBitmap retBmp = null;
        for (int i = 0; i < size; i++) {
            Matrix mtx;
            DraggableBitmap dBmp = (DraggableBitmap) this.mOverlayBitmaps.get(i);
            dBmp.deActivate();
            RectF r = new RectF(0.0f, 0.0f, (float) dBmp.mBitmap.getWidth(), (float) dBmp.mBitmap.getHeight());
            if (dBmp.getCurrentMatrix() == null) {
                mtx = dBmp.getMarginMatrix();
            } else {
                mtx = dBmp.getCurrentMatrix();
            }
            mtx.mapRect(r);
            float bmp_x = r.left;
            float bmp_y = r.top;
            if (event_x >= bmp_x && event_x < r.width() + bmp_x && event_y >= bmp_y && event_y < r.height() + bmp_y) {
                retBmp = dBmp;
                retidx = i;
            }
        }
        if (retBmp != null) {
            if (!retBmp.isTouched()) {
                retBmp.setTouched(true);
            }
            retBmp.activate();
        }
        return retidx;
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }

    private void midPoint(PointF point, MotionEvent event) {
        point.set((event.getX(0) + event.getX(1)) / 2.0f, (event.getY(0) + event.getY(1)) / 2.0f);
    }

    private float rotation(MotionEvent event) {
        return (float) Math.toDegrees(Math.atan2((double) (event.getY(0) - event.getY(1)), (double) (event.getX(0) - event.getX(1))));
    }

    public List<DraggableBitmap> getOverlayList() {
        return this.mOverlayBitmaps;
    }

    public void undo() {
        if (!this.mOperationStack.empty()) {
            BitmapOperationMap prev = (BitmapOperationMap) this.mOperationStack.pop();
            if (!this.mOperationStack.empty()) {
                prev = (BitmapOperationMap) this.mOperationStack.peek();
            }
            DraggableBitmap bmp = prev.getDraggableBitmap();
            Matrix mtx = prev.getOperationMatrix();
            switch (prev.getOption()) {
                case NEW:
                    this.mOverlayBitmaps.remove(bmp);
                    return;
                case ADD:
                    bmp.setCurrentMatrix(mtx);
                    return;
                default:
                    return;
            }
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF bitmapRect = getInnerBitmapSize();
        if (bitmapRect != null) {
            this.mInnerImageBounds = bitmapRect;
            canvas.clipRect(bitmapRect);
            Enumeration<DraggableBitmap> e = Collections.enumeration(this.mOverlayBitmaps);
            while (e.hasMoreElements()) {
                DraggableBitmap dBmp = (DraggableBitmap) e.nextElement();
                if (dBmp.getCurrentMatrix() != null) {
                    canvas.drawBitmap(dBmp.mBitmap, dBmp.getCurrentMatrix(), null);
                    RectF r = getStampBounding(dBmp);
                    if (this.mDrawOpacityBackground && dBmp == this.mActiveBitmap) {
                        this.mPaint.setColor(0);
                        this.mPaint.setStyle(Style.FILL);
                        this.mPaint.setAlpha(20);
                        canvas.drawRect(r, this.mPaint);
                    }
                }
            }
        }
    }

    public RectF getInnerBitmapSize() {
        RectF bitmapRect = new RectF();
        if (getDrawable() == null) {
            return null;
        }
        bitmapRect.right = (float) getDrawable().getIntrinsicWidth();
        bitmapRect.bottom = (float) getDrawable().getIntrinsicHeight();
        getImageMatrix().mapRect(bitmapRect);
        return bitmapRect;
    }

    private RectF getStampBounding(DraggableBitmap bmp) {
        if (bmp.mBitmap == null) {
            return null;
        }
        RectF r = new RectF(0.0f, 0.0f, (float) bmp.mBitmap.getWidth(), (float) bmp.mBitmap.getHeight());
        bmp.getCurrentMatrix().mapRect(r);
        return r;
    }

    public int deleteActiveBitmap() {
        if (this.mActiveBitmap == null) {
            return -1;
        }
        int index = this.mOverlayBitmaps.indexOf(this.mActiveBitmap);
        replaceOverlayBitmap(new DraggableBitmap(Bitmap.createBitmap(1, 1, Config.ARGB_8888)), index);
        return index;
    }

    public int undoBitmap(int index) {
        replaceOverlayBitmap(new DraggableBitmap(Bitmap.createBitmap(1, 1, Config.ARGB_8888)), index);
        return index;
    }

    public void replaceOverlayBitmap(DraggableBitmap dBitmap, int replaceIndex) {
        if (replaceIndex <= this.mOverlayBitmaps.size()) {
            this.mActiveBitmap = (DraggableBitmap) this.mOverlayBitmaps.get(replaceIndex);
            dBitmap.setCurrentMatrix(this.mActiveBitmap.getCurrentMatrix());
            this.mOverlayBitmaps.add(replaceIndex, dBitmap);
            this.mOverlayBitmaps.remove(this.mActiveBitmap);
            this.mActiveBitmap = (DraggableBitmap) this.mOverlayBitmaps.get(replaceIndex);
            invalidate();
        }
    }

    public void flipActiveBitmap() {
        try {
            Matrix flipHorizontalMtx = new Matrix();
            flipHorizontalMtx.setScale(-1.0f, 1.0f);
            flipHorizontalMtx.postTranslate((float) this.mActiveBitmap.mBitmap.getWidth(), 0.0f);
            Matrix mtx = this.mActiveBitmap.getCurrentMatrix();
            mtx.preConcat(flipHorizontalMtx);
            this.mActiveBitmap.setCurrentMatrix(mtx);
        } catch (NullPointerException e) {
            Log.v(TAG, "active bitmap is null");
        } catch (Exception e2) {
            Log.v(TAG, "error ocurred");
        }
    }

    public void rearrangeOverlayList() {
        int idx = this.mOverlayBitmaps.indexOf(this.mActiveBitmap);
        this.mOverlayBitmaps.add(this.mActiveBitmap);
        this.mOverlayBitmaps.remove(idx);
    }
}