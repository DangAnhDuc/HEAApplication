package com.example.heaapp.ultis;

import android.annotation.SuppressLint;
import android.net.Uri;

import com.example.heaapp.model.food.Data;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Common {

    //Save the current date
    @SuppressLint("SimpleDateFormat")
    public static String today=new SimpleDateFormat("yyyyMMdd").format(new Date());

    public static List<Data> foodList= new ArrayList<>();
    public static Uri userImageUri;
    public static StorageTask<UploadTask.TaskSnapshot> uploadUserImageTask;
}
