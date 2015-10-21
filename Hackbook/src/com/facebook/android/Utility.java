/**
 * Copyright 2010-present Facebook.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.facebook.android;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.provider.MediaStore;
import android.util.Log;

import org.json.JSONObject;

import com.urbanairship.AirshipConfigOptions;
import com.urbanairship.Logger;
import com.urbanairship.UAirship;
import com.urbanairship.push.CustomPushNotificationBuilder;
import com.urbanairship.push.PushManager;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;

public class Utility extends Application {

    public static Facebook mFacebook;
    @SuppressWarnings("deprecation")
    public static AsyncFacebookRunner mAsyncRunner;
    public static JSONObject mFriendsList;
    public static String userUID = null;
    public static String objectID = null;
    
    public static AndroidHttpClient httpclient = null;
    public static Hashtable<String, String> currentPermissions = new Hashtable<String, String>();

    private static int MAX_IMAGE_DIMENSION = 720;
    public static final String HACK_ICON_URL = "https://dl.dropboxusercontent.com/u/90179278/golfhole.jpg";
    
    
    public void onCreate() {

        super.onCreate();

        AirshipConfigOptions options = AirshipConfigOptions.loadDefaultOptions(this);

        // Optionally, customize your config at runtime:
        //
        // options.inProduction = false;
         options.developmentAppKey = "UahWUTJyQ_OvgCqwn4Vcyw";
         options.developmentAppSecret = "p43qg1VyTtGWX-lmQVc4Kg";

        UAirship.takeOff(this, options);
        Logger.logLevel = Log.VERBOSE;

        //use CustomPushNotificationBuilder to specify a custom layout
        CustomPushNotificationBuilder nb = new CustomPushNotificationBuilder();

        nb.statusBarIconDrawableId = R.drawable.golfy_alternative4;//custom status bar icon

        nb.layout = R.layout.notification;
        nb.layoutIconDrawableId = R.drawable.golfy_alternative4;//custom layout icon
        nb.layoutIconId = R.id.icon;
        nb.layoutSubjectId = R.id.subject;
        nb.layoutMessageId = R.id.message;

        // customize the sound played when a push is received
        //nb.soundUri = Uri.parse("android.resource://"+this.getPackageName()+"/" +R.raw.cat);

        PushManager.shared().setNotificationBuilder(nb);
        PushManager.shared().setIntentReceiver(IntentReceiver.class);

    }
    

    public static Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(new FlushedInputStream(is));
            bis.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpclient != null) {
                httpclient.close();
            }
        }
        return bm;
    }

    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break; // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }

    public static byte[] scaleImage(Context context, Uri photoUri) throws IOException {
        InputStream is = context.getContentResolver().openInputStream(photoUri);
        BitmapFactory.Options dbo = new BitmapFactory.Options();
        dbo.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, dbo);
        is.close();

        int rotatedWidth, rotatedHeight;
        int orientation = getOrientation(context, photoUri);

        if (orientation == 90 || orientation == 270) {
            rotatedWidth = dbo.outHeight;
            rotatedHeight = dbo.outWidth;
        } else {
            rotatedWidth = dbo.outWidth;
            rotatedHeight = dbo.outHeight;
        }

        Bitmap srcBitmap;
        is = context.getContentResolver().openInputStream(photoUri);
        if (rotatedWidth > MAX_IMAGE_DIMENSION || rotatedHeight > MAX_IMAGE_DIMENSION) {
            float widthRatio = ((float) rotatedWidth) / ((float) MAX_IMAGE_DIMENSION);
            float heightRatio = ((float) rotatedHeight) / ((float) MAX_IMAGE_DIMENSION);
            float maxRatio = Math.max(widthRatio, heightRatio);

            // Create the bitmap from file
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = (int) maxRatio;
            srcBitmap = BitmapFactory.decodeStream(is, null, options);
        } else {
            srcBitmap = BitmapFactory.decodeStream(is);
        }
        is.close();

        /*
         * if the orientation is not 0 (or -1, which means we don't know), we
         * have to do a rotation.
         */
        if (orientation > 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);

            srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),
                    srcBitmap.getHeight(), matrix, true);
        }

        String type = context.getContentResolver().getType(photoUri);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (type.equals("image/png")) {
            srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        } else if (type.equals("image/jpg") || type.equals("image/jpeg")) {
            srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        }
        byte[] bMapArray = baos.toByteArray();
        baos.close();
        return bMapArray;
    }

    public static int getOrientation(Context context, Uri photoUri) {
        /* it's on the external media. */
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }

        cursor.moveToFirst();
        int orientation = cursor.getInt(0);
        cursor.close();

        return orientation;
    }
}
