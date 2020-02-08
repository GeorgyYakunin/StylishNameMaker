package com.Example.stylishnamemaker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class ActivityPermissRunTime extends Activity {
    private SparseIntArray mErrorString;

    public abstract void onPermissionsGranted(int i);

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mErrorString = new SparseIntArray();
    }

    public void requestAppPermissions(final String[] requestedPermissions, int stringId, final int requestCode) {
        this.mErrorString.put(requestCode, stringId);
        int permissionCheck = 0;
        boolean showRequestPermissions = false;
        for (String permission : requestedPermissions) {
            permissionCheck += ContextCompat.checkSelfPermission(this, permission);
            if (showRequestPermissions || ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                showRequestPermissions = true;
            } else {
                showRequestPermissions = false;
            }
        }
        if (permissionCheck == 0) {
            onPermissionsGranted(requestCode);
        } else if (showRequestPermissions) {
            Snackbar.make(findViewById(16908290), stringId, -2).setAction((CharSequence) "GRANT", new OnClickListener() {
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(ActivityPermissRunTime.this, requestedPermissions, requestCode);
                }
            }).show();
        } else {
            ActivityCompat.requestPermissions(this, requestedPermissions, requestCode);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = 0;
        for (int permisson : grantResults) {
            permissionCheck += permisson;
        }
        if (grantResults.length <= 0 || permissionCheck != 0) {
            Snackbar.make(findViewById(16908290), this.mErrorString.get(requestCode), -2).setAction((CharSequence) "ENABLE", new OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    i.setData(Uri.parse("package:" + ActivityPermissRunTime.this.getPackageName()));
                    i.addCategory("android.intent.category.DEFAULT");
                    i.addFlags(268435456);
                    i.addFlags(1073741824);
                    i.addFlags(8388608);
                    ActivityPermissRunTime.this.startActivity(i);
                }
            }).show();
        } else {
            onPermissionsGranted(requestCode);
        }
    }
}
