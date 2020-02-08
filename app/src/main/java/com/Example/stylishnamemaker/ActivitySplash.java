package com.Example.stylishnamemaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;

public class ActivitySplash extends Activity {
    protected boolean _active = true;
    protected int _splashTime = ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED;
    final ActivitySplash activitySplashScreen = this;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.act_splash);
        new Thread() {
            public void run() {
                int waited = 0;
                while (waited < 2000) {
                    try {
                        Thread.sleep(100);
                        waited += 100;
                    } catch (InterruptedException e) {
                        ActivitySplash.this.finish();
                        ActivitySplash.this.startActivity(new Intent(ActivitySplash.this, ActivityClassSolution.class));
                        return;
                    } catch (Throwable th) {
                        ActivitySplash.this.finish();
                        ActivitySplash.this.startActivity(new Intent(ActivitySplash.this, ActivityClassSolution.class));
                    }
                }
                ActivitySplash.this.finish();
                ActivitySplash.this.startActivity(new Intent(ActivitySplash.this, ActivityClassSolution.class));
            }
        }.start();
    }

    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
