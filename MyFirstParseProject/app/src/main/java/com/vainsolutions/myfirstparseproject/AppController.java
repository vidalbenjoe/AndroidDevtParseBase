package com.vainsolutions.myfirstparseproject;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by vidalbenjoe on 23/01/2016.
 */
public class AppController extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, Constant.APPLICATION_ID, Constant.CLIENT_KEY);

//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();
    }
}
