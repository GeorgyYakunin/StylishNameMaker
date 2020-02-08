package com.Example.stylishnamemaker.constant;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


import com.Example.stylishnamemaker.R;
import com.Example.stylishnamemaker.ScalingUtilities;

public class Constant {
    public static String SETTING_PREFERENCE = "settingPreference";
    public static int[] imageGallery = new int[]{R.drawable.bg_1, R.drawable.bg_2, R.drawable.bg_3, R.drawable.bg_4, R.drawable.bg_5, R.drawable.bg_6, R.drawable.bg_7, R.drawable.bg_8, R.drawable.bg_9, R.drawable.bg_10, R.drawable.bg_11, R.drawable.bg_12, R.drawable.bg_13, R.drawable.bg_14, R.drawable.bg_15, R.drawable.bg_16, R.drawable.bg_17, R.drawable.bg_18, R.drawable.bg_19, R.drawable.bg_20, R.drawable.bg_21, R.drawable.bg_22, R.drawable.bg_23, R.drawable.bg_24, R.drawable.bg_25, R.drawable.bg_26, R.drawable.bg_27, R.drawable.bg_28, R.drawable.bg_29, R.drawable.bg_30, R.drawable.bg_31, R.drawable.bg_32, R.drawable.bg_33, R.drawable.bg_34, R.drawable.bg_35, R.drawable.bg_36, R.drawable.bg_37, R.drawable.bg_38, R.drawable.bg_39, R.drawable.bg_40, R.drawable.bg_41, R.drawable.bg_42, R.drawable.bg_43, R.drawable.bg_44, R.drawable.bg_45, R.drawable.bg_46, R.drawable.bg_47, R.drawable.bg_48, R.drawable.bg_49, R.drawable.bg_50, R.drawable.bg_51, R.drawable.bg_52, R.drawable.bg_53, R.drawable.bg_54, R.drawable.bg_55, R.drawable.bg_56, R.drawable.bg_57, R.drawable.bg_58, R.drawable.bg_59, R.drawable.bg_60, R.drawable.bg_61, R.drawable.bg_62, R.drawable.bg_63, R.drawable.bg_64, R.drawable.bg_65, R.drawable.bg_66, R.drawable.bg_67, R.drawable.bg_68, R.drawable.bg_69, R.drawable.bg_70, R.drawable.bg_71, R.drawable.bg_72, R.drawable.bg_73, R.drawable.bg_74, R.drawable.bg_75, R.drawable.bg_76, R.drawable.bg_77, R.drawable.bg_78, R.drawable.bg_79, R.drawable.bg_80, R.drawable.bg_81, R.drawable.bg_82, R.drawable.bg_83, R.drawable.bg_84, R.drawable.bg_85, R.drawable.bg_86, R.drawable.bg_87, R.drawable.bg_88, R.drawable.bg_89, R.drawable.bg_90, R.drawable.bg_91, R.drawable.bg_92, R.drawable.bg_93, R.drawable.bg_94, R.drawable.bg_95, R.drawable.bg_96, R.drawable.bg_97, R.drawable.bg_98, R.drawable.bg_99, R.drawable.bg_100};

    public static Bitmap createScaledBitmap(Bitmap unscaledBitmap, ScalingUtilities.ScalingLogic scalingLogic, int width, int height) {
        Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), width, height, scalingLogic);
        Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), width, height, scalingLogic);
        Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(), Config.ARGB_8888);
        new Canvas(scaledBitmap).drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(2));
        return scaledBitmap;
    }

    public static Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingUtilities.ScalingLogic scalingLogic) {
        if (scalingLogic != ScalingUtilities.ScalingLogic.CROP) {
            return new Rect(0, 0, srcWidth, srcHeight);
        }
        float dstAspect = ((float) dstWidth) / ((float) dstHeight);
        if (((float) srcWidth) / ((float) srcHeight) > dstAspect) {
            int srcRectWidth = (int) (((float) srcHeight) * dstAspect);
            int srcRectLeft = (srcWidth - srcRectWidth) / 2;
            return new Rect(srcRectLeft, 0, srcRectLeft + srcRectWidth, srcHeight);
        }
        int srcRectHeight = (int) (((float) srcWidth) / dstAspect);
        int scrRectTop = (srcHeight - srcRectHeight) / 2;
        return new Rect(0, scrRectTop, srcWidth, scrRectTop + srcRectHeight);
    }

    public static Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingUtilities.ScalingLogic scalingLogic) {
        if (scalingLogic != ScalingUtilities.ScalingLogic.FIT) {
            return new Rect(0, 0, dstWidth, dstHeight);
        }
        float srcAspect = ((float) srcWidth) / ((float) srcHeight);
        if (srcAspect > ((float) dstWidth) / ((float) dstHeight)) {
            return new Rect(0, 0, dstWidth, (int) (((float) dstWidth) / srcAspect));
        }
        return new Rect(0, 0, (int) (((float) dstHeight) * srcAspect), dstHeight);
    }
}
