package com.howdy.buddy.vr;

import android.app.Application;
import com.google.firebase.database.FirebaseDatabase;

public class HowdyBuddy extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
