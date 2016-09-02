package com.pixplicity.priorityalert.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SilentModeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO check if DND is enabled and toggle NotificationService and PhoneReceiver
    }

}
