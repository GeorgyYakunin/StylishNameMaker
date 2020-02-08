package com.Example.stylishnamemaker.comman;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CropImageView extends AppCompatImageView {
    private static final int PRESS_AREA_RADIUS = 36;
    private static final int PRESS_LB = 3;
    private static final int PRESS_LT = 0;
    private static final int PRESS_RB = 2;
    private static final int PRESS_RT = 1;
    private static final int REMAIN_AREA_ALPHA = 127;
    private Rect mActualArea;
    private Rect mChooseArea;
    private Paint mChooseAreaPaint;
    private boolean mFirstDrawFlag;
    private Rect mOriginalArea;
    private Bitmap mOriginalBitmap;
    private int mPressAreaFlag;
    private Rect mPressLBArea;
    private Rect mPressLTArea;
    private Rect mPressRBArea;
    private Rect mPressRTArea;
    private Paint mRemainAreaPaint;
    private Rect mRemainBottomArea;
    private Rect mRemainLeftArea;
    private Rect mRemainRightArea;
    private Rect mRemainTopArea;
    private boolean mTouchCorrectFlag;
    private int mX;
    private int mY;

    public CropImageView(Context context) {
        super(context);
        init();
    }

    public CropImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.mActualArea = new Rect();
        this.mChooseArea = new Rect();
        this.mPressLTArea = new Rect();
        this.mPressRTArea = new Rect();
        this.mPressRBArea = new Rect();
        this.mPressLBArea = new Rect();
        this.mRemainLeftArea = new Rect();
        this.mRemainRightArea = new Rect();
        this.mRemainTopArea = new Rect();
        this.mRemainBottomArea = new Rect();
        this.mChooseAreaPaint = new Paint();
        this.mRemainAreaPaint = new Paint();
        this.mRemainAreaPaint.setStyle(Style.FILL);
        this.mRemainAreaPaint.setAlpha(127);
        this.mFirstDrawFlag = true;
        this.mTouchCorrectFlag = false;
    }

    public void setBitmap(Bitmap bitmap) {
        this.mOriginalArea = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        this.mOriginalBitmap = bitmap.copy(Config.ARGB_8888, true);
        setImageBitmap(this.mOriginalBitmap);
    }

    @SuppressLint({"DrawAllocation"})
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mFirstDrawFlag) {
            this.mFirstDrawFlag = false;
            imageScale();
        }
        setRemainArea();
        canvas.drawRect(this.mRemainLeftArea, this.mRemainAreaPaint);
        canvas.drawRect(this.mRemainRightArea, this.mRemainAreaPaint);
        canvas.drawRect(this.mRemainTopArea, this.mRemainAreaPaint);
        canvas.drawRect(this.mRemainBottomArea, this.mRemainAreaPaint);
        this.mChooseAreaPaint.setColor(-1);
        this.mChooseAreaPaint.setStrokeWidth(5.0f);
        this.mChooseAreaPaint.setStyle(Style.STROKE);
        canvas.drawRect(this.mChooseArea, this.mChooseAreaPaint);
        this.mChooseAreaPaint.setColor(Color.argb(153, 255, 255, 255));
        this.mChooseAreaPaint.setStrokeWidth(2.0f);
        int point1x = ((this.mChooseArea.right - this.mChooseArea.left) / 3) + this.mChooseArea.left;
        int point1y = this.mChooseArea.top;
        int point2x = (((this.mChooseArea.right - this.mChooseArea.left) * 2) / 3) + this.mChooseArea.left;
        int point2y = this.mChooseArea.top;
        int point3x = this.mChooseArea.right;
        int point3y = ((this.mChooseArea.bottom - this.mChooseArea.top) / 3) + this.mChooseArea.top;
        int point4x = this.mChooseArea.right;
        int point4y = (((this.mChooseArea.bottom - this.mChooseArea.top) * 2) / 3) + this.mChooseArea.top;
        int point5x = point2x;
        int point5y = this.mChooseArea.bottom;
        int point6x = point1x;
        int point6y = this.mChooseArea.bottom;
        int point7x = this.mChooseArea.left;
        int point7y = point4y;
        int point8x = this.mChooseArea.left;
        int point8y = point3y;
        canvas.drawLine((float) point1x, (float) point1y, (float) point6x, (float) point6y, this.mChooseAreaPaint);
        canvas.drawLine((float) point2x, (float) point2y, (float) point5x, (float) point5y, this.mChooseAreaPaint);
        canvas.drawLine((float) point8x, (float) point8y, (float) point3x, (float) point3y, this.mChooseAreaPaint);
        canvas.drawLine((float) point7x, (float) point7y, (float) point4x, (float) point4y, this.mChooseAreaPaint);
        this.mChooseAreaPaint.setColor(-1);
        this.mChooseAreaPaint.setStyle(Style.FILL);
        setPressArea();
        canvas.drawOval(new RectF(this.mPressLTArea), this.mChooseAreaPaint);
        canvas.drawOval(new RectF(this.mPressRTArea), this.mChooseAreaPaint);
        canvas.drawOval(new RectF(this.mPressRBArea), this.mChooseAreaPaint);
        canvas.drawOval(new RectF(this.mPressLBArea), this.mChooseAreaPaint);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 0) {
            this.mX = (int) event.getX();
            this.mY = (int) event.getY();
            if (isInPressArea(this.mX, this.mY) || isInChooseArea(this.mX, this.mY)) {
                this.mTouchCorrectFlag = true;
                return true;
            }
        }
        if (event.getAction() == 2 && this.mTouchCorrectFlag) {
            if (isMovePressArea((int) event.getX(), (int) event.getY())) {
                invalidate();
                this.mX = (int) event.getX();
                this.mY = (int) event.getY();
                return true;
            } else if (this.mChooseArea.contains(this.mActualArea)) {
                return true;
            } else {
                moveChooseArea(((int) event.getX()) - this.mX, ((int) event.getY()) - this.mY);
                invalidate();
                this.mX = (int) event.getX();
                this.mY = (int) event.getY();
                return true;
            }
        } else if (event.getAction() != 1) {
            return super.onTouchEvent(event);
        } else {
            this.mTouchCorrectFlag = false;
            this.mPressAreaFlag = -1;
            invalidate();
            return true;
        }
    }

    private void imageScale() {
        if (this.mOriginalArea == null) {
            throw new IllegalStateException("setBitmap() must called before...");
        }
        RectF temp1 = new RectF(this.mOriginalArea);
        RectF temp2 = new RectF();
        getImageMatrix().mapRect(temp2, temp1);
        this.mActualArea.set((int) temp2.left, (int) temp2.top, (int) temp2.right, (int) temp2.bottom);
        this.mChooseArea.set(this.mActualArea.left + 50, this.mActualArea.top + 50, this.mActualArea.right - 50, this.mActualArea.bottom - 50);
    }

    private void setRemainArea() {
        this.mRemainLeftArea.set(this.mActualArea.left, this.mActualArea.top, this.mChooseArea.left, this.mActualArea.bottom);
        this.mRemainRightArea.set(this.mChooseArea.right, this.mActualArea.top, this.mActualArea.right, this.mActualArea.bottom);
        this.mRemainTopArea.set(this.mChooseArea.left, this.mActualArea.top, this.mChooseArea.right, this.mChooseArea.top);
        this.mRemainBottomArea.set(this.mChooseArea.left, this.mChooseArea.bottom, this.mChooseArea.right, this.mActualArea.bottom);
    }

    private void setPressArea() {
        this.mPressLTArea.set(this.mChooseArea.left - 18, this.mChooseArea.top - 18, this.mChooseArea.left + 18, this.mChooseArea.top + 18);
        this.mPressRTArea.set(this.mChooseArea.right - 18, this.mChooseArea.top - 18, this.mChooseArea.right + 18, this.mChooseArea.top + 18);
        this.mPressRBArea.set(this.mChooseArea.right - 18, this.mChooseArea.bottom - 18, this.mChooseArea.right + 18, this.mChooseArea.bottom + 18);
        this.mPressLBArea.set(this.mChooseArea.left - 18, this.mChooseArea.bottom - 18, this.mChooseArea.left + 18, this.mChooseArea.bottom + 18);
    }

    public boolean isInChooseArea(int x, int y) {
        return this.mChooseArea.contains(x, y);
    }

    public boolean isInPressArea(int x, int y) {
        Rect temp1 = new Rect(this.mPressLTArea.left - 5, this.mPressLTArea.top - 5, this.mPressLTArea.right + 5, this.mPressLTArea.bottom + 5);
        Rect temp2 = new Rect(this.mPressRTArea.left - 5, this.mPressRTArea.top - 5, this.mPressRTArea.right + 5, this.mPressRTArea.bottom + 5);
        Rect temp3 = new Rect(this.mPressRBArea.left - 5, this.mPressRBArea.top - 5, this.mPressRBArea.right + 5, this.mPressRBArea.bottom + 5);
        Rect temp4 = new Rect(this.mPressLBArea.left - 5, this.mPressLBArea.top - 5, this.mPressLBArea.right + 5, this.mPressLBArea.bottom + 5);
        if (temp1.contains(x, y)) {
            this.mPressAreaFlag = 0;
            return true;
        } else if (temp2.contains(x, y)) {
            this.mPressAreaFlag = 1;
            return true;
        } else if (temp3.contains(x, y)) {
            this.mPressAreaFlag = 2;
            return true;
        } else if (!temp4.contains(x, y)) {
            return false;
        } else {
            this.mPressAreaFlag = 3;
            return true;
        }
    }

    private boolean isMovePressArea(int x, int y) {
        switch (this.mPressAreaFlag) {
            case 0:
                pressLTArea(x - this.mX, y - this.mY);
                break;
            case 1:
                pressRTArea(x - this.mX, y - this.mY);
                break;
            case 2:
                pressRBArea(x - this.mX, y - this.mY);
                break;
            case 3:
                pressLBArea(x - this.mX, y - this.mY);
                break;
            default:
                return false;
        }
        return true;
    }

    private void pressLTArea(int x, int y) {
        int left = this.mChooseArea.left + x;
        int right = this.mChooseArea.right;
        int top = this.mChooseArea.top + y;
        int bottom = this.mChooseArea.bottom;
        if (left < this.mActualArea.left || left > this.mActualArea.right - 36 || top < this.mActualArea.top || top > this.mChooseArea.bottom - 36) {
            if (left < this.mActualArea.left) {
                left = this.mActualArea.left;
            }
            if (top < this.mActualArea.top) {
                top = this.mActualArea.top;
            }
            if (left > this.mChooseArea.right - 36) {
                left = this.mChooseArea.right - 36;
            }
            if (top > this.mChooseArea.bottom - 36) {
                top = this.mChooseArea.bottom - 36;
            }
            this.mChooseArea.set(left, top, right, bottom);
            return;
        }
        this.mChooseArea.set(left, top, right, bottom);
    }

    private void pressRTArea(int x, int y) {
        int left = this.mChooseArea.left;
        int right = this.mChooseArea.right + x;
        int top = this.mChooseArea.top + y;
        int bottom = this.mChooseArea.bottom;
        if (right > this.mActualArea.right || right < this.mChooseArea.left + 36 || top < this.mActualArea.top || top > this.mChooseArea.bottom - 36) {
            if (right > this.mActualArea.right) {
                right = this.mActualArea.right;
            }
            if (top < this.mActualArea.top) {
                top = this.mActualArea.top;
            }
            if (right < this.mChooseArea.left + 36) {
                right = this.mChooseArea.left + 36;
            }
            if (top > this.mChooseArea.bottom - 36) {
                top = this.mChooseArea.bottom - 36;
            }
            this.mChooseArea.set(left, top, right, bottom);
            return;
        }
        this.mChooseArea.set(left, top, right, bottom);
    }

    private void pressRBArea(int x, int y) {
        int left = this.mChooseArea.left;
        int right = this.mChooseArea.right + x;
        int top = this.mChooseArea.top;
        int bottom = this.mChooseArea.bottom + y;
        if (right > this.mActualArea.right || left < this.mChooseArea.left + 36 || bottom > this.mActualArea.bottom || bottom < this.mChooseArea.top + 36) {
            if (right > this.mActualArea.right) {
                right = this.mActualArea.right;
            }
            if (bottom > this.mActualArea.bottom) {
                bottom = this.mActualArea.bottom;
            }
            if (right < this.mChooseArea.left + 36) {
                right = this.mChooseArea.left + 36;
            }
            if (bottom < this.mChooseArea.top + 36) {
                bottom = this.mChooseArea.top + 36;
            }
            this.mChooseArea.set(left, top, right, bottom);
            return;
        }
        this.mChooseArea.set(left, top, right, bottom);
    }

    private void pressLBArea(int x, int y) {
        int left = this.mChooseArea.left + x;
        int right = this.mChooseArea.right;
        int top = this.mChooseArea.top;
        int bottom = this.mChooseArea.bottom + y;
        if (left < this.mActualArea.left || left > this.mChooseArea.right - 36 || bottom > this.mActualArea.bottom || bottom < this.mChooseArea.top + 36) {
            if (left < this.mActualArea.left) {
                left = this.mActualArea.left;
            }
            if (bottom > this.mActualArea.bottom) {
                bottom = this.mActualArea.bottom;
            }
            if (left > this.mChooseArea.right - 36) {
                left = this.mChooseArea.right - 36;
            }
            if (bottom < this.mChooseArea.top + 36) {
                bottom = this.mChooseArea.top + 36;
            }
            this.mChooseArea.set(left, top, right, bottom);
            return;
        }
        this.mChooseArea.set(left, top, right, bottom);
    }

    public void moveChooseArea(int x, int y) {
        int left = this.mChooseArea.left + x;
        int right = this.mChooseArea.right + x;
        int top = this.mChooseArea.top + y;
        int bottom = this.mChooseArea.bottom + y;
        if (!this.mActualArea.contains(left, top, right, bottom)) {
            if (left < this.mActualArea.left) {
                left = this.mActualArea.left;
                right = this.mChooseArea.right;
            }
            if (right > this.mActualArea.right) {
                right = this.mActualArea.right;
                left = this.mChooseArea.left;
            }
            if (top < this.mActualArea.top) {
                top = this.mActualArea.top;
                bottom = this.mChooseArea.bottom;
            }
            if (bottom > this.mActualArea.bottom) {
                bottom = this.mActualArea.bottom;
                top = this.mChooseArea.top;
            }
        }
        this.mChooseArea.set(left, top, right, bottom);
    }

    public Bitmap getCropBitmap() {
        float ratioWidth = ((float) this.mOriginalBitmap.getWidth()) / ((float) (this.mActualArea.right - this.mActualArea.left));
        float ratioHeight = ((float) this.mOriginalBitmap.getHeight()) / ((float) (this.mActualArea.bottom - this.mActualArea.top));
        int left = (int) (((float) (this.mChooseArea.left - this.mActualArea.left)) * ratioWidth);
        int top = (int) (((float) (this.mChooseArea.top - this.mActualArea.top)) * ratioHeight);
        return Bitmap.createBitmap(this.mOriginalBitmap, left, top, ((int) (((float) left) + (((float) (this.mChooseArea.right - this.mChooseArea.left)) * ratioWidth))) - left, ((int) (((float) top) + (((float) (this.mChooseArea.bottom - this.mChooseArea.top)) * ratioHeight))) - top);
    }

    public void cropBitmap() {
        this.mFirstDrawFlag = true;
        this.mTouchCorrectFlag = false;
        setBitmap(getCropBitmap());
    }

    public void reset() {
        this.mFirstDrawFlag = true;
        this.mTouchCorrectFlag = false;
    }
}
