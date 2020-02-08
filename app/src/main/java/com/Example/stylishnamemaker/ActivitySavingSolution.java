package com.Example.stylishnamemaker;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.content.FileProvider;
import android.view.KeyEvent;
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
import com.Example.stylishnamemaker.constant.SaveToStorageUtil;

import java.io.File;

public class ActivitySavingSolution extends Activity implements OnClickListener {
    public static boolean deleted = false;

    private ImageView backBtn;
    private String imgUrl = null;
    private ImageView instaBtn;

    private ImageView mainImageView;
    private Bitmap saveBitmap;
    private ImageView saveBtn;
    private ImageView shareBtn;
    private ImageView whatsappBtn;
    Uri imageuri;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.cake_saving);


        RelativeLayout adview=(RelativeLayout) findViewById(R.id.adlayout);
//        Banner(adview, ActivitySavingSolution.this);



        this.mainImageView = (ImageView) findViewById(R.id.mainImageView);
        this.backBtn = (ImageView) findViewById(R.id.backBtn);
        this.instaBtn = (ImageView) findViewById(R.id.instaBtn);
        this.whatsappBtn = (ImageView) findViewById(R.id.whatsappBtn);
        this.shareBtn = (ImageView) findViewById(R.id.shareBtn);
        this.saveBtn = (ImageView) findViewById(R.id.saveBtn);

        this.imgUrl = getIntent().getStringExtra("ImgUrl");
        File  file= new File(imgUrl);

        imageuri= FileProvider.getUriForFile(ActivitySavingSolution.this, BuildConfig.APPLICATION_ID + ".provider", file);
        this.backBtn.setOnClickListener(this);
        this.instaBtn.setOnClickListener(this);
        this.whatsappBtn.setOnClickListener(this);
        this.shareBtn.setOnClickListener(this);
        this.saveBtn.setOnClickListener(this);

        String uri = Uri.parse("file:///" + this.imgUrl).toString();
        ImageLoader.getInstance().loadImage(uri, BitmapScaler.getDisplayImageOptions(true), new ImageLoadingListener() {
            public void onLoadingStarted(String arg0, View arg1) {
            }

            public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                PhotoConstant.errorAlert(ActivitySavingSolution.this);
            }

            public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                ActivitySavingSolution.this.mainImageView.setImageBitmap(arg2);
                ActivitySavingSolution.this.saveBitmap = arg2;
            }

            public void onLoadingCancelled(String arg0, View arg1) {
            }
        });
    }



    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.backBtn:
                setResult(-1);
                finish();
                return;
            case R.id.shareBtn:
                Intent share = new Intent("android.intent.action.SEND");
                share.setType("image/jpeg");
                share.putExtra("android.intent.extra.STREAM", imageuri);
                startActivity(Intent.createChooser(share, "via"));
                return;
            case R.id.instaBtn:
                if (instagramappInstalledOrNot()) {
                    try {
                        Intent shareIntent = new Intent("android.intent.action.SEND");
                        shareIntent.setType("image/*");
                        shareIntent.putExtra("android.intent.extra.STREAM", imageuri);
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
                if (whatsappInstalledOrNot()) {
                    Intent waIntent = new Intent("android.intent.action.SEND");
                    waIntent.setType("image/jpeg");
                    waIntent.setPackage("com.whatsapp");
                    if (waIntent != null) {
                        waIntent.putExtra("android.intent.extra.STREAM", imageuri);
                        startActivity(Intent.createChooser(waIntent, getResources().getString(R.string.shareTxt)));
                        return;
                    }
                    return;
                }
                Toast.makeText(this, getResources().getString(R.string.whatsappTxt), Toast.LENGTH_LONG).show();
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp&hl=en")));
                return;
            case R.id.saveBtn:

                    saveImage();
                    return;

            default:
                return;
        }
    }

    public void saveImage() {
        try {

            SaveToStorageUtil.saveReal(this.saveBitmap, this);
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.saveImg), Toast.LENGTH_LONG).show();

//            Google_Itrestial_Ads(ActivitySavingSolution.this);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.errorImg), Toast.LENGTH_LONG).show();
        }
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



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            setResult(-1);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onPause() {

        super.onPause();
    }

    public void onResume() {
        super.onResume();

    }

    public void onDestroy() {

        super.onDestroy();
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


//    public void Google_Itrestial_Ads(final Context context) {
//
//
//        try {
//
//            AdRequest adRequest = new AdRequest.Builder().build();
//            final InterstitialAd interstitialAds = new InterstitialAd(context);
//            interstitialAds.setAdUnitId(getString(R.string.ads_inter));
//
//            interstitialAds.loadAd(adRequest);
//
//            interstitialAds.setAdListener(new AdListener() {
//                @Override
//                public void onAdLoaded() {
//
//                    interstitialAds.show();
//
//                }
//
//                @Override
//                public void onAdClosed() {
//
//
//                }
//
//                @Override
//                public void onAdFailedToLoad(int errorCode) {
//
//                }
//            });
//        } catch (Exception e) {
//
//        }
//    }
}
