package com.example.instgramclone;

import com.parse.Parse;
import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("CdbCZ3uLYdwJ4Pow29IowKpfEvx75liXJS7btL3F")
                // if defined
                .clientKey("nvztOc6AETp620nzn85jARxaHToHtFQeLXTMGl45")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}