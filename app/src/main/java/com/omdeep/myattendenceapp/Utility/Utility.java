package com.omdeep.myattendenceapp.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Utility {
    public static void showShortToast(Context context, String msg){
        Toast.makeText(context,  msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showFragmentToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static String convertBitmaptoBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap convertBase64ToBitmap(String base64){
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return bitmap;
    }

//    public static String convertImageToString(ImageView capturedImage) {
//        capturedImage.buildDrawingCache();
//        Bitmap bitmap = capturedImage.getDrawingCache();
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,80,byteArrayOutputStream);
//        byte[] b = byteArrayOutputStream.toByteArray();
//        return Base64.encodeToString(b,Base64.DEFAULT);
//    }

//    public static Bitmap convertStringTOBitmap(String stringImage){
//        byte[] decodedString = Base64.decode(stringImage,Base64.DEFAULT);
//        return BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
//    }
}
