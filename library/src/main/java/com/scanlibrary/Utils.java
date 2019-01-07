package com.scanlibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by jhansi on 05/04/15.
 */
public class Utils {

    private Utils() {

    }

    public static Uri getUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    public static Bitmap getBitmap(Context context, Uri uri) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        Bitmap scaled = scaleToSize(bitmap,2560);
        return scaled;
    }

    private static Bitmap scaleToSize(Bitmap bitmap,int size) {
        Matrix m = new Matrix();
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        double ratio = originalWidth / originalHeight;
        int newWidth = originalWidth > originalHeight ? size : (int)(size / ratio);
        int newHeight = originalWidth > originalHeight ? (int)(size/ratio) : size;
        m.setRectToRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), new RectF(0, 0, newWidth, newHeight), Matrix.ScaleToFit.CENTER);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
    }
}
