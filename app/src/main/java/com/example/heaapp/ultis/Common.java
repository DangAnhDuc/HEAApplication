package com.example.heaapp.ultis;

import android.annotation.SuppressLint;
import android.location.Location;
import android.net.Uri;

import com.example.heaapp.model.food.Data;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Common {

    //Save the current date
    @SuppressLint("SimpleDateFormat")
    //key for api usage
    public static String APIKEY="2955facf-02ae-4d32-aacb-447f15c78f84";
    public static Location current_location=null;
    public static List<Data> foodList= new ArrayList<>();
    public static Uri userImageUri;
    public static StorageTask<UploadTask.TaskSnapshot> uploadUserImageTask;
    public static String currentDate = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    public static String currentMonth = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
    public static String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
}
