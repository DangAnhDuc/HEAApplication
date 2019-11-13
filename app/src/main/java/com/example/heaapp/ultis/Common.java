package com.example.heaapp.ultis;

import android.annotation.SuppressLint;

import com.example.heaapp.model.food.Data;
import com.example.heaapp.model.food.Dishes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Common {

    //Save the current date
    @SuppressLint("SimpleDateFormat")
    public static String today=new SimpleDateFormat("yyyyMMdd").format(new Date());

    //Save the username;
    public static String name;

    public static List<Data> foodList= new ArrayList<>();
}
