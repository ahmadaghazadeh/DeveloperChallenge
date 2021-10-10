package com.threatfabric.developerchallenge.dependecyinjection;

import com.threatfabric.developerchallenge.ui.actionctivity.ActionActivity;
import com.threatfabric.developerchallenge.ui.actionctivity.ActionModule;
import com.threatfabric.developerchallenge.ui.mainactivity.MainActivity;
import com.threatfabric.developerchallenge.ui.mainactivity.MainModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuilderModule {

    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = ActionModule.class)
    abstract ActionActivity bindActionActivity();
}
