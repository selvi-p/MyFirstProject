package com.example.selvip.employeedetails;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by selvi.p on 11-08-2017.
 */

public class EmployeeDetails extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);

    }
}
