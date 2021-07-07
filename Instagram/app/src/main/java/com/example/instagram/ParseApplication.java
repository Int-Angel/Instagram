package com.example.instagram;

import android.app.Application;

import com.example.instagram.models.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("8NxTVdCL269pPHy0MKUizj0pFPgVH2uNtRDRSrju")
                .clientKey("jmNEpAi1ezfhqTY2mIVvTiNOqHWSVJ8ll97BxhBY")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
