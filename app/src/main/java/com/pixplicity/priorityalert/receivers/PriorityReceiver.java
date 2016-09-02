package com.pixplicity.priorityalert.receivers;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

public class PriorityReceiver {

    private static final String TAG = PhoneReceiver.class.getSimpleName();

    public static final String ACTION = TAG + "event";

    public static boolean isRelevant(Context context, String phoneNumber) {
        if (!isInZenMode(context)) {
            return false;
        }
        return true;
    }

    public static void invoke(Context context) {
        context.sendBroadcast(new Intent(PriorityReceiver.ACTION));
    }

    private static boolean isInZenMode(Context context) {
        try {
            return Settings.Global.getInt(context.getContentResolver(), "zen_mode") > 0;
        } catch (Settings.SettingNotFoundException e) {
            // TODO handle error and inform of unexpected state
            return false;
        }
    }

    public static boolean isRelevant(Context context, CharSequence notificationTitle, CharSequence[] notificationLines) {
        if (!isInZenMode(context)) {
            return false;
        }
        return true;
    }

}
