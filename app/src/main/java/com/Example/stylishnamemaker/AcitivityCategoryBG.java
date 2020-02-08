package com.Example.stylishnamemaker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.Example.stylishnamemaker.constant.Constant;

import java.util.HashMap;

public class AcitivityCategoryBG extends Activity {

    private HashMap<Integer, Bitmap> cache = new HashMap();
    private ImageAdapter galleryAdapter;
    private GridView gridView;
    private int screenWidth;

    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        private int width;

        public ImageAdapter(Context c) {
            this.context = c;
        }

        public ImageAdapter(Context c, int width) {
            this.context = c;
            this.width = width;
        }

        public int getCount() {
            return Constant.imageGallery.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            this.layoutInflater = (LayoutInflater) this.context.getSystemService(LAYOUT_INFLATER_SERVICE);
            v = this.layoutInflater.inflate(R.layout.galery_adapter, null);
            SquareRelativeLayout layout = (SquareRelativeLayout) v.findViewById(R.id.galleryLayout);
            ImageView imageView = new ImageView(this.context);
            layout.addView(imageView, new LayoutParams(-1, this.width));
            imageView.setScaleType(ScaleType.FIT_XY);
            layout.setLayoutParams(new AbsListView.LayoutParams(-1, this.width));
            imageView.setImageBitmap(AcitivityCategoryBG.this.cacheImage(Integer.valueOf(position)));
            return v;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.category_back_ground);

        RelativeLayout adview=(RelativeLayout) findViewById(R.id.adlayout);
//        Banner(adview,AcitivityCategoryBG.this);

        DisplayMetrics displayMatrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMatrics);
        this.screenWidth = displayMatrics.widthPixels / 3;
        Bundle exBundle = getIntent().getExtras();
        if (exBundle != null) {
            exBundle.getInt("Index");
        }
        this.gridView = (GridView) findViewById(R.id.gridView);
        this.gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", arg2 + "");
                AcitivityCategoryBG.this.setResult(-1, returnIntent);
                AcitivityCategoryBG.this.finish();
            }
        });
        this.galleryAdapter = new ImageAdapter(this, this.screenWidth);
        this.gridView.setAdapter(this.galleryAdapter);
    }

    private Bitmap cacheImage(Integer position) {
        Bitmap captureBitmap = null;
        try {
            if (this.cache.containsKey(position)) {
                return (Bitmap) this.cache.get(position);
            }
            Options options = new Options();
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Config.RGB_565;
            options.inSampleSize = 1;
            captureBitmap = BitmapFactory.decodeResource(getResources(), Constant.imageGallery[position.intValue()], options);
            captureBitmap = Bitmap.createScaledBitmap(captureBitmap, captureBitmap.getWidth() / 3, captureBitmap.getHeight() / 3, true);
            this.cache.put(position, captureBitmap);
            return captureBitmap;
        } catch (OutOfMemoryError e) {
            this.cache.clear();
        }
        return captureBitmap;
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

}
