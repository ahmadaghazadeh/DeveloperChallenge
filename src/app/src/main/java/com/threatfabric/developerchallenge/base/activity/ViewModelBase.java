package com.threatfabric.developerchallenge.base.activity;

import androidx.lifecycle.ViewModel;

import java.lang.ref.WeakReference;

public abstract class ViewModelBase<N extends INavigator> extends ViewModel {
    private WeakReference<N> mNavigator;

    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

}
