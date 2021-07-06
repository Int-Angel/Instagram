package com.example.instagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("8NxTVdCL269pPHy0MKUizj0pFPgVH2uNtRDRSrju")
                .clientKey("jmNEpAi1ezfhqTY2mIVvTiNOqHWSVJ8ll97BxhBY")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
