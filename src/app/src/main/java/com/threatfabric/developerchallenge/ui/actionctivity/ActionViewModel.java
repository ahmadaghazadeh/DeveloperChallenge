package com.threatfabric.developerchallenge.ui.actionctivity;

import androidx.lifecycle.MutableLiveData;

import com.threatfabric.developerchallenge.base.activity.ViewModelBase;

import javax.inject.Inject;

public class ActionViewModel extends ViewModelBase<IActionNavigator> {

    public MutableLiveData<String> actionText=new MutableLiveData<>();
    public MutableLiveData<Integer> actionColor=new MutableLiveData<>();

    @Inject
    public ActionViewModel() {
    }

    public void promptGameWin(String title,int color) {
        actionText.setValue(title);
        actionColor.setValue(color);
        getNavigator().gotoBack();
    }

}
