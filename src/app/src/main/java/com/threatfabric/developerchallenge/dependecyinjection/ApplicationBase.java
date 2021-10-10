package com.threatfabric.developerchallenge.dependecyinjection;

import android.app.Application;
import android.content.Context;


import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class ApplicationBase extends Application implements HasAndroidInjector {
    private Thread.UncaughtExceptionHandler defaultHandler;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }


    public void handleUncaughtException(Thread thread, Throwable e) {
        StackTraceElement[] arr = e.getStackTrace();
        final StringBuffer report = new StringBuffer(e.toString());
        final String lineSeperator = "-------------------------------\n\n";
        report.append("--------- Stack trace ---------\n\n");
        for (StackTraceElement anArr : arr) {
            report.append("    ");
            report.append(anArr.toString());
            report.append(lineSeperator);
        }
        int x=0;
        defaultHandler.uncaughtException(thread, e);
    }
    @Inject
    DispatchingAndroidInjector<Object> androidInjector;

    @Override
    public AndroidInjector<Object> androidInjector() {

        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this::handleUncaughtException);
        AppComponent component = DaggerAppComponent.builder().getApp(this).build();
        component.inject(this);
        return androidInjector;
    }
}
