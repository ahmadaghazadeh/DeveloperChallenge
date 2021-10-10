package com.threatfabric.developerchallenge.base.activity;

import androidx.annotation.StringRes;

public interface INavigator {
    void snackBar(String title);
    void snackBar(@StringRes int resId);
    void handleError(Throwable throwable);
}
