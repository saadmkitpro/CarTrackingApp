package com.khanstech.ownerdriverapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.khanstech.ownerdriverapp.model.Account;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PreferenceHelper {

    private static SharedPreferences sharedPrefs;

    public static boolean setString(Context ctx, String key, String value) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        Editor edit = sharedPrefs.edit();
        edit.putString(key, value);
        return edit.commit();
    }

    public static String getString(Context ctx, String key) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sharedPrefs.getString(key, "");
    }

    public static boolean setInt(Context ctx, String key, int value) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        Editor edit = sharedPrefs.edit();
        edit.putInt(key, value);
        return edit.commit();
    }

    public static int getInt(Context ctx, String key) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sharedPrefs.getInt(key, 0);
    }

    public static boolean setValues(Context ctx, String key, List<String> value) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        Editor edit = sharedPrefs.edit();
        Set<String> set = new HashSet<>(value);
        edit.putStringSet(key, set);
        return edit.commit();
    }

    @Nullable
    public static List<String> getValues(Context ctx, String key) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        List<String> list = new ArrayList<String>();
        list.addAll(sharedPrefs.getStringSet(key, null));
        return list;
    }

    public static boolean setAccount(Context ctx, Account m) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        Editor edit = sharedPrefs.edit();
        if (m != null) {
            edit.putString("acc_name", m.Name);
            edit.putString("acc_contact", m.Contact);
            edit.putString("acc_email", m.Email);
            edit.putString("acc_ride_type", m.Ride_type);
            edit.putString("acc_ride_cond", m.Ride_condition);
            edit.putString("acc_nor", m.Nor);
            edit.putString("acc_score", m.Score);
            edit.putString("acc_rate", m.Rate);
            return edit.commit();
        }
        return false;
    }

    @Nullable
    public static Account getAccount(Context ctx) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        Account m = new Account();
        if (!sharedPrefs.getString("acc_name", "").isEmpty()) {
            m.Name = sharedPrefs.getString("acc_name", "");
            m.Contact = sharedPrefs.getString("acc_contact", "");
            m.Email = sharedPrefs.getString("acc_email", "");
            m.Ride_type = sharedPrefs.getString("acc_ride_type", "");
            m.Ride_condition = sharedPrefs.getString("acc_ride_cond", "");
            m.Nor = sharedPrefs.getString("acc_nor", "");
            m.Score = sharedPrefs.getString("acc_score", "");
            m.Rate = sharedPrefs.getString("acc_rate", "");
            return m;
        } else
            return null;
    }
}
