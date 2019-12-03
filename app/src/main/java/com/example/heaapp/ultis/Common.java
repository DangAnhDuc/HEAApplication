package com.example.heaapp.ultis;

import android.annotation.SuppressLint;
import android.location.Location;
import android.net.Uri;

import com.example.heaapp.model.food.Data;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
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
    //key for api usage
    public static String APIKEY="2955facf-02ae-4d32-aacb-447f15c78f84";
    public static Location current_location=null;
    public static List<Data> foodList= new ArrayList<>();
    public static Uri userImageUri;
    public static StorageTask<UploadTask.TaskSnapshot> uploadUserImageTask;
}
