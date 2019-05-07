package com.example.acerpc.wedknot;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnection  {

    public static Boolean ConnectedOrNot(Context context){
        boolean flag = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        flag = networkInfo!=null && networkInfo.isConnected();
        return flag;
    }
}