package com.Example.stylishnamemaker.constant;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;

public class ImageScannerAdapter implements MediaScannerConnectionClient {
    MediaScannerConnection conn;
    Context context;
    String path;

    public ImageScannerAdapter(Context c) {
        this.context = c;
    }

    public void scanImage(String path) {
        this.path = path;
        if (this.conn != null) {
            this.conn.disconnect();
        }
        this.conn = new MediaScannerConnection(this.context, this);
        this.conn.connect();
    }

    public void onMediaScannerConnected() {
        try {
            this.conn.scanFile(this.path, "image/*");
        } catch (IllegalStateException e) {
        }
    }

    public void onScanCompleted(String path, Uri uri) {
        this.conn.disconnect();
    }
}
