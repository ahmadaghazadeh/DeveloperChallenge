package com.threatfabric.developerchallenge.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public abstract class ActivityBase<T extends ViewDataBinding, V extends ViewModelBase> extends AppCompatActivity
        implements HasAndroidInjector {

    protected T mViewDataBinding;
    protected V mViewModel;
    @Inject
    DispatchingAndroidInjector<Object> androidInjector;


    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }

    public abstract V getViewModel();

    public abstract int getBindingVariable();

    @LayoutRes
    public abstract int getLayoutId();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setEnvironment();
        performDataBinding();
    }

    public void handleError(Throwable throwable) {
        snackBar(throwable.getMessage());
    }



    @Override
    protected void onStart() {
        super.onStart();

    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.setLifecycleOwner(this);

    }

    private void setEnvironment() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void snackBar(String title) {
        Snackbar snackbar = Snackbar.make(mViewDataBinding.getRoot(), title, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void snackBar(int resId) {
        Snackbar snackbar = Snackbar.make(mViewDataBinding.getRoot(), resId, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}
