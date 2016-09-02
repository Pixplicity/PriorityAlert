package com.pixplicity.priorityalert.services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.pixplicity.priorityalert.receivers.PriorityReceiver;


public class NotificationService extends AccessibilityService {

    private static final String TAG = NotificationService.class.getSimpleName();

    @Override
    protected void onServiceConnected() {
        Log.d(TAG, "connected");
        super.onServiceConnected();
        if (false) {
            AccessibilityServiceInfo info = new AccessibilityServiceInfo();
            info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
            info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
            info.notificationTimeout = 100;
            setServiceInfo(info);
            Log.d(TAG, "set service info");
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "accessibility event: " + event);
        Notification notification = (Notification) event.getParcelableData();
        CharSequence title = notification.extras.getCharSequence(Notification.EXTRA_TITLE);
        if (title != null) {
            Log.d(TAG, title.toString());
        }
        CharSequence[] lines = notification.extras.getCharSequenceArray(Notification.EXTRA_TEXT_LINES);
        if (lines != null) {
            for (CharSequence msg : lines) {
                Log.d(TAG, msg.toString());
            }
        }
        if (PriorityReceiver.isRelevant(this, title, lines)) {
            PriorityReceiver.invoke(this);
        }
    }

    @Override
    public void onInterrupt() {
        // Nothing to do
    }

}
