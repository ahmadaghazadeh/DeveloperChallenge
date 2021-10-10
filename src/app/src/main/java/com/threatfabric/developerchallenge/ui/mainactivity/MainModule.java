package com.threatfabric.developerchallenge.ui.mainactivity;

import androidx.lifecycle.ViewModelProvider;

import com.threatfabric.developerchallenge.base.ViewModelProviderFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Provides
    ViewModelProvider.Factory getFactory(MainViewModel model){
        return new ViewModelProviderFactory<>(model);
    }

}

