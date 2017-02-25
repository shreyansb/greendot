package org.shreyans.greendot;

import android.app.Application;
import android.util.Log;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(getClass().getName(), "we're in here, making a new application");

        // initialize DBFlow databases
        FlowManager.init(new FlowConfig.Builder(this)
                .openDatabasesOnInit(true).build());
    }
}

