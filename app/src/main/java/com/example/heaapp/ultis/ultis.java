package com.example.heaapp.ultis;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import com.shashank.sony.fancytoastlib.FancyToast;

public class ultis{
    public static Intent setIntent(Context context, Class destination) {
        Intent intent = new Intent(context, destination);
        context.startActivity(intent);
        return intent ;
    }

    public static void showSuccessMessage(Context context, String message) {
        FancyToast.makeText(context, message, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
    }

    public static void showErrorMessage(Context context, String message) {
        FancyToast.makeText(context, message, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
    }

    public static void showWarningMessage(Context context, String message) {
        FancyToast.makeText(context, message, FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
    }

    public static void showConfusingMessage(Context context, String message) {
        FancyToast.makeText(context, message, FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
    }

    public static String getFileExtension(Uri uri, Context context){
        ContentResolver contentResolver= context.getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
