package com.rn_bgservice_demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

public class ReminderEventService extends HeadlessJsTaskService {
    private static final String TAG = "ReminderEventService";
    private static final long TIMEOUT = 5000;

    @Nullable
    protected HeadlessJsTaskConfig getTaskConfig(Intent intent) {
        Bundle extras = intent.getExtras();

        WritableMap data = extras != null ? Arguments.fromBundle(extras): Arguments.createMap();

        return new HeadlessJsTaskConfig(
            "ReminderService",
            data,
            TIMEOUT,
            true
        );
    }
}
