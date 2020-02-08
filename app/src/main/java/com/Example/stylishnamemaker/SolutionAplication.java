package com.Example.stylishnamemaker;

import android.app.Application;

//import com.google.android.gms.ads.MobileAds;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class SolutionAplication extends Application {
    public void onCreate() {
        super.onCreate();
        ImageLoader.getInstance().init(new Builder(getApplicationContext()).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY).displayer(new FadeInBitmapDisplayer(300)).build()).memoryCache(new WeakMemoryCache()).diskCacheSize(104857600).build());

//        MobileAds.initialize(this, getString(R.string.ads_id));

    }
}
