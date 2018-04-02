package com.adityaedu.themathwizz.Server;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

/**
 * Created by Preetham on 24-03-2018.
 *
 */

public class DatabaseServer extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);


        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("pKC2KO7L0niIwNQp7rmZFsSEw7xjLOozstVeYXxg")
                .clientKey("BEpIlRVxShJdyAIou6BpcJFiPAERnNs5sEibDlB7")
                .server("https://parseapi.back4app.com")
                .enableLocalDataStore()
                .build()

        );

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        ParseFacebookUtils.initialize(getApplicationContext());


    }
}
