package com.Example.stylishnamemaker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.Example.stylishnamemaker.comman.BitmapScaler;
import com.Example.stylishnamemaker.comman.PhotoConstant;

import java.io.File;

public class ActivitySharing extends Activity implements OnClickListener {
    public static boolean deleted = false;

    private ImageView deleteBtn;
    private String imgUrl = null;
    private ImageView instaBtn;

    private ImageView mainImageView;
    private ImageView shareBtn;
    private ImageView whatsappBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.share_image);

        RelativeLayout adview=(RelativeLayout) findViewById(R.id.adlayout);
//        Banner(adview,ActivitySharing.this);


        this.mainImageView = (ImageView) findViewById(R.id.mainImageView);
        this.instaBtn = (ImageView) findViewById(R.id.instaBtn);
        this.whatsappBtn = (ImageView) findViewById(R.id.whatsappBtn);
        this.shareBtn = (ImageView) findViewById(R.id.shareBtn);
        this.deleteBtn = (ImageView) findViewById(R.id.deleteBtn);
        this.imgUrl = getIntent().getStringExtra("ImgUrl");
        this.instaBtn.setOnClickListener(this);
        this.whatsappBtn.setOnClickListener(this);
        this.shareBtn.setOnClickListener(this);
        this.deleteBtn.setOnClickListener(this);
        ImageLoader.getInstance().loadImage(this.imgUrl, BitmapScaler.getDisplayImageOptions(true), new ImageLoadingListener() {
            public void onLoadingStarted(String arg0, View arg1) {
            }

            public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                PhotoConstant.errorAlert(ActivitySharing.this);
            }

            public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                ActivitySharing.this.mainImageView.setImageBitmap(arg2);
            }

            public void onLoadingCancelled(String arg0, View arg1) {
            }
        });
    }



    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.shareBtn:
                Intent share = new Intent("android.intent.action.SEND");
                share.setType("image/jpeg");
                share.putExtra("android.intent.extra.STREAM", Uri.parse(this.imgUrl));
                startActivity(Intent.createChooser(share, "via"));
                return;
            case R.id.instaBtn:
                if (instagramappInstalledOrNot()) {
                    try {
                        Intent shareIntent = new Intent("android.intent.action.SEND");
                        shareIntent.setType("image/*");
                        shareIntent.putExtra("android.intent.extra.STREAM", Uri.parse(this.imgUrl));
                        shareIntent.setPackage("com.instagram.android");
                        startActivity(shareIntent);
                        return;
                    } catch (Exception e) {
                        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                Toast.makeText(this, getResources().getString(R.string.instaTxt), Toast.LENGTH_LONG).show();
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.instagram.android")));
                return;
            case R.id.whatsappBtn:

                    shareOnWhatsApp();
                    return;

            case R.id.deleteBtn:
                deleted();
                return;
            default:
                return;
        }
    }

    private void shareOnWhatsApp() {
        if (whatsappInstalledOrNot()) {
            Intent waIntent = new Intent("android.intent.action.SEND");
            waIntent.setType("image/jpeg");
            waIntent.setPackage("com.whatsapp");
            if (waIntent != null) {
                waIntent.putExtra("android.intent.extra.STREAM", Uri.parse(this.imgUrl));
                startActivity(Intent.createChooser(waIntent, getResources().getString(R.string.shareTxt)));
                return;
            }
            return;
        }
        Toast.makeText(this, getResources().getString(R.string.whatsappTxt), Toast.LENGTH_LONG).show();
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp&hl=en")));
    }

    private void deleted() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.removeOpt));
        builder.setMessage(getResources().getString(R.string.removeTxt));
        builder.setPositiveButton(getResources().getString(R.string.yestxt), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                if (new File(Uri.parse(ActivitySharing.this.imgUrl).getPath()).delete()) {
                    ActivitySharing.this.setResult(-1);
                    ActivitySharing.this.finish();
                    return;
                }
                Toast.makeText(ActivitySharing.this.getApplicationContext(), ActivitySharing.this.getResources().getString(R.string.errorImg), Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.canceltxt), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    private boolean instagramappInstalledOrNot() {
        try {
            ApplicationInfo info = getPackageManager().getApplicationInfo("com.instagram.android", 0);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    private boolean whatsappInstalledOrNot() {
        try {
            ApplicationInfo info = getPackageManager().getApplicationInfo("com.whatsapp", 0);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public void onPause() {

        super.onPause();
    }

    public void onResume() {
        super.onResume();

    }

    public void onDestroy() {

        super.onDestroy();
        System.gc();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

//    public void Banner(final RelativeLayout Ad_Layout, final Context context) {
//
//        AdView mAdView = new AdView(context);
//        mAdView.setAdSize(AdSize.BANNER);
//        mAdView.setAdUnitId(getString(R.string.ads_bnr));
//        AdRequest adre = new AdRequest.Builder().build();
//        mAdView.loadAd(adre);
//        Ad_Layout.addView(mAdView);
//
//        mAdView.setAdListener(new AdListener() {
//
//            @Override
//            public void onAdLoaded() {
//                // TODO Auto-generated method stub
//                Ad_Layout.setVisibility(View.VISIBLE);
//                super.onAdLoaded();
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // TODO Auto-generated method stub
//                Ad_Layout.setVisibility(View.GONE);
//
//
//            }
//        });
//    }






}
