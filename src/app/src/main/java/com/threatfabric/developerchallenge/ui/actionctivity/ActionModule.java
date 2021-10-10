package com.threatfabric.developerchallenge.ui.actionctivity;

import androidx.lifecycle.ViewModelProvider;

import com.threatfabric.developerchallenge.base.ViewModelProviderFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class ActionModule {

    @Provides
    ViewModelProvider.Factory getFactory(ActionViewModel model){
        return new ViewModelProviderFactory<>(model);
    }

}

