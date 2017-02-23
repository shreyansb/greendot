package org.shreyans.greendot;

import android.app.Application;
import android.util.Log;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(getClass().getName(), "we're in here, making a new application");
    }
}