package com.Example.stylishnamemaker;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class ActivityClassSolution extends ActivityPermissRunTime implements OnClickListener {
    static final int CODE_RESULT_REQUEST = 2;
    static final int WRITE_EXTERNAL_STORAGE = 2;
    protected boolean _taken;

    Context mContext=this;

    private boolean isStartCheck = false;
    private ImageView myWork;
    private ImageView privacy;
    private ImageView startImage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.menu);
        this.isStartCheck = false;
        showPermission();

        RelativeLayout adview=(RelativeLayout) findViewById(R.id.adlayout);
//        Banner(adview, ActivityClassSolution.this);

        this.startImage = (ImageView) findViewById(R.id.startBtn);
        this.myWork = (ImageView) findViewById(R.id.myWork);
        this.privacy = (ImageView) findViewById(R.id.policyBtn);
        this.startImage.setOnClickListener(this);
        this.myWork.setOnClickListener(this);
        this.privacy.setOnClickListener(this);
    }

    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.startBtn:
                this.isStartCheck = true;
                showPermission();
                return;
            case R.id.myWork:
                startActivity(new Intent(this, ActivityGalleryOfSolution.class));
//                Google_Itrestial_Ads(mContext);
                return;
            case R.id.policyBtn:
                try{
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id="+getPackageName())));
                }
                catch (ActivityNotFoundException e){
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                }
                return;
            default:
                return;
        }
    }

    public void onPermissionsGranted(int requestCode) {
        if (this.isStartCheck) {
            startActivityForResult(new Intent(this, ActivitySolutioning.class), 2);
        }
    }

    private void showPermission() {
        requestAppPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, R.string.msg, 2);
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
