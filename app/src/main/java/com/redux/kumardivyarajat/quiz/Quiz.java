package com.redux.kumardivyarajat.quiz;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.PushService;


public class Quiz extends Application {
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "3gLhF7a7iVahhG3tef32em78RQES0l3FDKiHBqVm", "TSQyw3mocTBeM77MPdpVc9LjzecaPD7FvPoCneI0");

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();

        PushService.setDefaultPushCallback(this,MainActivity.class);
    }
}
