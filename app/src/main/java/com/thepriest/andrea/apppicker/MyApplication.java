package com.thepriest.andrea.apppicker;

import android.app.Application;
import android.util.Log;

import com.danylovolokh.androidlogger.AndroidLogger;

import java.io.File;
import java.io.IOException;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreateMyApplication");
        File logsDirectory = AndroidLogger.getDefaultLogFilesDirectory(this);
        int logFileMaxSizeBytes = 2 * 1024 * 1024; // 2Mb
        try {
            AndroidLogger.initialize(
                    this,
                    logsDirectory,
                    "Intents",
                    logFileMaxSizeBytes,
                    false
            );
        } catch (IOException e) {
            Log.d(TAG, "IOException: "+e.toString());
            // Some error happened - most likely there is no free space on the system
        }
        AndroidLogger.w("TAG", "Warn Message");
    }

}
