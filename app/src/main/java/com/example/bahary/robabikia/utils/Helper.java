package com.example.bahary.robabikia.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Helper {

    public static void printText(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    public static void printLog(String key,String message){
        Log.i(key,message);
    }

}
