package com.Example.stylishnamemaker.comman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.text.format.DateFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class MenuActivityHelper {
    private static final String TAG = "MenuActivityHelper";
    static String mCurrentPhotoPath;

    public static void callGalleryApp(Activity menuActivity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        menuActivity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PhotoConstant.GALLERY_REQUEST);
    }

    public static String saveCropBit(Bitmap bitmap, Context context) {
        return saveCrop(bitmap, generateImageName(), context);
    }

    public static String generateImageName() {
        return "IMG_" + DateFormat.format("yyyyMMdd_kkmmss", new Date()) + ".jpg";
    }

    public static String saveCrop(Bitmap bitmap, String imgName, Context context) {
        String path = getCropFolderPath() + "/" + imgName;
        try {
            FileOutputStream out = new FileOutputStream(path);
            bitmap.compress(CompressFormat.JPEG, 100, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    private static File getCropFolderPath() {
        return checkCropPath(Environment.getExternalStorageDirectory(), PhotoConstant.CROP_TEMP);
    }

    private static File checkCropPath(File pathLvl1, String lvl2) {
        File pathLvl2 = new File(pathLvl1, lvl2);
        if (!pathLvl2.exists()) {
            pathLvl2.mkdir();
        }
        return pathLvl2;
    }

    public static void setCropImgPath(String path) {
        mCurrentPhotoPath = path;
    }

    public static String getCropImgPath() {
        return mCurrentPhotoPath;
    }

    public static void deleteFolder(Activity activity) {
        try {
            File folderLvl1 = checkCropPath(Environment.getExternalStorageDirectory(), PhotoConstant.CROP_TEMP);
            if (!folderLvl1.exists()) {
                folderLvl1.mkdirs();
            }
            String uri = Uri.fromFile(folderLvl1).toString() + "/";
            if (folderLvl1.isDirectory()) {
                String[] children = folderLvl1.list();
                if (children != null) {
                    for (String str : children) {
                        new File(Uri.parse(uri + str).getPath()).delete();
                    }
                }
            }
        } catch (Exception e) {
        }
    }
}
