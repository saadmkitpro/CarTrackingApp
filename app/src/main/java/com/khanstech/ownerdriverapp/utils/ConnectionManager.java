package com.khanstech.ownerdriverapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

public class ConnectionManager {

    public static boolean isConnectedToInternet(Context context) {
        final ConnectivityManager cm = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static boolean setWifiEnabled(Context ctx, boolean enable) {
        WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        return wifi.setWifiEnabled(enable);
    }
}
