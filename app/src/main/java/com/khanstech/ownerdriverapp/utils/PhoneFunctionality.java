package com.khanstech.ownerdriverapp.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.khanstech.ownerdriverapp.R;

import java.util.ArrayList;
import java.util.List;

public class PhoneFunctionality {

    public final static long VIBRATE_TIME = (long) 500;
    public final static int ALERT_NOTIF_ID = IdGenerator.generateViewId();
    public final static int MEAL_NOTIF_ID = IdGenerator.generateViewId();

    public static void playSound(Context context) {
        Uri notification = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(
                context.getApplicationContext(), notification);
        r.play();
    }

    public static void hideKeyboard(Activity parentAct) {
        InputMethodManager inputManager = (InputMethodManager) parentAct
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(parentAct.getCurrentFocus()
                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void vibrateMobile(Context context) {
        Vibrator vibrator = (Vibrator) context
                .getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VIBRATE_TIME);
    }

    public static void hideNotification(Context context, int id) {
        NotificationManager notifManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notifManager.cancel(id);
    }

    public static void startAnim(Context context, View view) {
        view.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.image_click));
    }

    public static void sendMessage(Context context, String receiver,
                                   String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(receiver, null, message, null, null);
    }

    public static boolean isStringNotNullOrEmpty(String[] strings) {
        for (String string : strings) {
            if (string == null | string.trim().isEmpty())
                return false;
        }
        return true;
    }

    public static void makeToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static List<String> GetRegisteredAccounts(Context context) {
        List<String> emails = new ArrayList<>();
        Account[] accounts;
        try {
            accounts = AccountManager.get(context).getAccountsByType("com.google");
            for (Account account : accounts) {
                emails.add(account.name);
            }
        } catch (Exception e) {
            Log.i("Exception", "Exception: " + e);
        }
        try {
            accounts = AccountManager.get(context).getAccounts();
            for (Account account : accounts) {
                emails.add(account.name);
            }
        } catch (Exception e) {
            Log.i("Exception", "Exception: " + e);
        }
        return emails;
    }
}
