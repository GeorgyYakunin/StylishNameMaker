package com.Example.stylishnamemaker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class ColorPickerDialog extends AlertDialog {
    private ColorPicker colorPickerView;
    private OnClickListener onClickListener = new OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case -2:
                    dialog.dismiss();
                    return;
                case -1:
                    ColorPickerDialog.this.onColorSelectedListener.onColorSelected(ColorPickerDialog.this.colorPickerView.getColor());
                    return;
                default:
                    return;
            }
        }
    };
    private final OnColorSelectedListener onColorSelectedListener;

    public interface OnColorSelectedListener {
        void onColorSelected(int i);
    }

    public ColorPickerDialog(Context context, int initialColor, OnColorSelectedListener onColorSelectedListener) {
        super(context);
        this.onColorSelectedListener = onColorSelectedListener;
        RelativeLayout relativeLayout = new RelativeLayout(context);
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.addRule(13);
        this.colorPickerView = new ColorPicker(context);
        this.colorPickerView.setColor(initialColor);
        relativeLayout.addView(this.colorPickerView, layoutParams);
        setButton(-1, context.getString(17039370), this.onClickListener);
        setButton(-2, context.getString(17039360), this.onClickListener);
        setView(relativeLayout);
    }
}
