package com.mlx.android.learnandroid.services;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;

import java.util.List;

public class MyAccessibilityService extends AccessibilityService {
    private static final String TAG = "MyAccessibilityService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                Log.i(TAG, "onAccessibilityEvent: capture click event");
                AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
                if (null != nodeInfo) {
                    //查找text为Text!的控件
                    List<AccessibilityNodeInfo> button = nodeInfo.findAccessibilityNodeInfosByText("Text!");
                    nodeInfo.recycle();
                    for (AccessibilityNodeInfo item :
                            button) {
                        Log.i(TAG, "onAccessibilityEvent: long-click button");
                        item.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
                    }
                }


            default:
                break;
        }
    }

    @Override
    public void onInterrupt() {
        Log.i(TAG, "onInterrupt: ");
    }
}
