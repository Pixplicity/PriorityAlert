package com.pixplicity.priorityalert.services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import com.pixplicity.priorityalert.receivers.PriorityReceiver;


public class NotificationService extends AccessibilityService {

    private static final String TAG = NotificationService.class.getSimpleName();

    @Override
    protected void onServiceConnected() {
        Log.d(TAG, "connected");
        super.onServiceConnected();

        final AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType |= AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        info.feedbackType |= AccessibilityServiceInfo.FEEDBACK_AUDIBLE;
        info.feedbackType |= AccessibilityServiceInfo.FEEDBACK_HAPTIC;
        info.flags |= AccessibilityServiceInfo.DEFAULT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            info.flags |= AccessibilityServiceInfo.FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY;
            info.flags |= AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS;
            info.flags |= AccessibilityServiceInfo.FLAG_REQUEST_FILTER_KEY_EVENTS;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            info.flags |= AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS;
        }
        info.notificationTimeout = 0;
        setServiceInfo(info);
        Log.d(TAG, "set service info");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "accessibility event: " + event);
        if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            Notification notification = (Notification) event.getParcelableData();
            CharSequence title;
            if (notification == null) {
                title = "(unknown)";
            } else {
                title = notification.extras.getCharSequence(Notification.EXTRA_TITLE);
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
            Toast.makeText(this, "Notification: " + title, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInterrupt() {
        // Nothing to do
    }

}
