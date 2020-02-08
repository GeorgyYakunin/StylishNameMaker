package com.Example.stylishnamemaker.constant;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.text.format.DateFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class SaveToStorageUtil {
    public static String save(Bitmap bitmap, String imgName, Context context) {
        String path = getFolderPath() + "/" + imgName;
        try {
            bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    private static String getFolderPath() {
        return checkPath(Environment.getExternalStorageDirectory(), "StylishNameColTst").getPath();
    }

    private static File checkPath(File pathLvl1, String lvl2) {
        File pathLvl2 = new File(pathLvl1, lvl2);
        if (!pathLvl2.exists()) {
            pathLvl2.mkdir();
        }
        return pathLvl2;
    }

    public static String save(Bitmap bitmap, Context context) {
        return save(bitmap, generateImageName(), context);
    }

    public static String generateImageName() {
        return "IMG_" + DateFormat.format("yyyyMMdd_kkmmss", new Date()) + ".jpg";
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String file : children) {
                if (!deleteDir(new File(dir, file))) {
                }
            }
        }
        return dir.delete();
    }

    public static void deleteFolder() {
        deleteDir(checkPath(Environment.getExternalStorageDirectory(), "StylishNameColTst"));
    }

    public static String saveReal(Bitmap bitmap, Context context) {
        return saveReal(bitmap, generateImageName(), context);
    }

    public static String saveReal(Bitmap bitmap, String imgName, Context context) {
        String path = getRealFolderPath() + "/" + imgName;
        try {
            bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    private static String getRealFolderPath() {
        return checkPath(Environment.getExternalStorageDirectory(), "StylishNamePics").getPath();
    }

    public static File getRealFolderFile() {
        return checkPath(Environment.getExternalStorageDirectory(), "StylishNamePics");
    }
}
