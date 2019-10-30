package com.example.heaapp.ultis;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class Common {

    //Save the current date
    @SuppressLint("SimpleDateFormat")
    public static String today=new SimpleDateFormat("yyyyMMdd").format(new Date());
}
