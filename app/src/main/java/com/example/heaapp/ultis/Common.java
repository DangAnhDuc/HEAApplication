package com.example.heaapp.ultis;

import android.annotation.SuppressLint;

import com.example.heaapp.model.food.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Common {

    //Save the current date
    @SuppressLint("SimpleDateFormat")
    public static String today=new SimpleDateFormat("yyyyMMdd").format(new Date());

    public static List<Data> foodList= new ArrayList<>();
}
