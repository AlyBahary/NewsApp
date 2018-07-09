package com.example.bahary.robabikia.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.net.InetAddress;

public class Helper {

    public static void printText(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    public static void printLog(String key,String message){
        Log.i(key,message);
    }
    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("www.google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

}
