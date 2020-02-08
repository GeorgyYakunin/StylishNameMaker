package com.Example.stylishnamemaker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import com.Example.stylishnamemaker.comman.BitmapScaler;
import com.Example.stylishnamemaker.constant.SaveToStorageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ActivityGalleryOfSolution extends Activity {

    private GridView gridView;
    private int height;
    private String[] imageFileList;
    private DisplayImageOptions options;
    private ArrayList<String> photo = new ArrayList();
    private int width;

    public class MyGalleryAsy extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog;

        protected void onPreExecute() {
            this.dialog = ProgressDialog.show(ActivityGalleryOfSolution.this, "", "Loading ...", true);
            this.dialog.show();
        }

        protected Void doInBackground(Void... arg0) {
            ActivityGalleryOfSolution.this.readImage();
            return null;
        }

        protected void onPostExecute(Void result) {
            int fakeimg;
            this.dialog.dismiss();
            if (ActivityGalleryOfSolution.this.photo.size() == 0) {
                fakeimg = 2;
            } else if ((ActivityGalleryOfSolution.this.photo.size() - 1) % 3 == 0) {
                fakeimg = 1;
            } else if ((ActivityGalleryOfSolution.this.photo.size() - 2) % 3 == 0) {
                fakeimg = 0;
            } else {
                fakeimg = -1;
            }
            for (int i = 0; i <= fakeimg; i++) {
                ActivityGalleryOfSolution.this.photo.add("empty.png");
            }
            ActivityGalleryOfSolution.this.gridView.setAdapter(new WorkAdapter(ActivityGalleryOfSolution.this));



        }
    }


    static class ViewHolder {
        RelativeLayout bookLayout;
        ImageView imglesson;

        ViewHolder() {
        }
    }

    public class WorkAdapter extends BaseAdapter {
          final /* synthetic */ boolean $assertionsDisabled = (!ActivityGalleryOfSolution.class.desiredAssertionStatus());
        private LayoutInflater inflater;

        WorkAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
            HashMap hashMap = new HashMap();
        }

        public int getCount() {
            return ActivityGalleryOfSolution.this.photo.size();
        }

        public Object getItem(int position) {
            return ActivityGalleryOfSolution.this.photo.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            View view = convertView;
            if (view == null) {
                view = this.inflater.inflate(R.layout.background_frame_adapter, parent, false);
                holder = new ViewHolder();
                if ($assertionsDisabled || view != null) {
                    holder.imglesson = (ImageView) view.findViewById(R.id.imglesson);
                    holder.bookLayout = (RelativeLayout) view.findViewById(R.id.bookLayout);
                    view.setTag(holder);
                } else {
                    throw new AssertionError();
                }
            }
            holder = (ViewHolder) view.getTag();
            LayoutParams layoutParamsImg = new LayoutParams(ActivityGalleryOfSolution.this.width, ActivityGalleryOfSolution.this.height);
            layoutParamsImg.addRule(1);
            if (position % 3 == 0) {
                layoutParamsImg.addRule(11);
                layoutParamsImg.setMargins(20, 0, 0, 0);
            } else if ((position - 1) % 3 == 0) {
                layoutParamsImg.addRule(14);
                layoutParamsImg.setMargins(12, 0, 12, 0);
            } else if ((position - 2) % 3 == 0) {
                layoutParamsImg.addRule(9);
                layoutParamsImg.setMargins(0, 0, 24, 0);
            }
            if (((String) ActivityGalleryOfSolution.this.photo.get(position)).equalsIgnoreCase("empty.png")) {
                holder.imglesson.setBackgroundResource(R.drawable.empty);
            } else {
                final ViewHolder finalHolder = holder;
                ImageLoader.getInstance().loadImage((String) ActivityGalleryOfSolution.this.photo.get(position), new ImageSize(150, 150), ActivityGalleryOfSolution.this.options, new ImageLoadingListener() {
                    public void onLoadingStarted(String arg0, View arg1) {
                    }

                    public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                        Toast.makeText(ActivityGalleryOfSolution.this.getApplicationContext(), arg2.toString(), Toast.LENGTH_SHORT).show();
                    }

                    public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                        finalHolder.imglesson.setImageBitmap(arg2);
                    }

                    public void onLoadingCancelled(String arg0, View arg1) {
                    }
                }, new ImageLoadingProgressListener() {
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {
                    }
                });
            }
            holder.bookLayout.setLayoutParams(layoutParamsImg);
            return view;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.main_work_category);
        this.options = BitmapScaler.getDisplayImageOptions(true);
        DisplayMetrics displayMatrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMatrics);
        this.width = (displayMatrics.widthPixels / 3) - 30;
        this.height = displayMatrics.heightPixels / 4;

        RelativeLayout adview=(RelativeLayout) findViewById(R.id.adlayout);
//        Banner(adview, ActivityGalleryOfSolution.this);


        this.gridView = (GridView) findViewById(R.id.gridView);
        this.gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                if (!((String) ActivityGalleryOfSolution.this.photo.get(arg2)).equalsIgnoreCase("empty.png")) {
                    Intent intent = new Intent(ActivityGalleryOfSolution.this, ActivitySharing.class);
                    intent.putExtra("ImgUrl", (String) ActivityGalleryOfSolution.this.photo.get(arg2));
                    ActivityGalleryOfSolution.this.startActivityForResult(intent, 45);
                }
            }
        });
        new MyGalleryAsy().execute(new Void[0]);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            this.photo.clear();
            new MyGalleryAsy().execute(new Void[0]);
        }
    }

    private void readImage() {
        try {
            File file1 = SaveToStorageUtil.getRealFolderFile();
            if (!file1.exists()) {
                file1.mkdirs();
            }
            String uri = Uri.fromFile(file1).toString() + "/";
            if (file1.isDirectory()) {
                this.imageFileList = file1.list();
                if (this.imageFileList != null) {
                    for (String str : this.imageFileList) {
                        this.photo.add(uri + str);
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public static boolean isSdPresent() {
        return Environment.getExternalStorageState().equals("mounted");
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
