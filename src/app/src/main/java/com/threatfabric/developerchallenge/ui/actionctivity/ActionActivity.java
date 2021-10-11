package com.threatfabric.developerchallenge.ui.actionctivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.lifecycle.ViewModelProvider;

import com.threatfabric.developerchallenge.BR;
import com.threatfabric.developerchallenge.R;
import com.threatfabric.developerchallenge.base.activity.ActivityBase;
import com.threatfabric.developerchallenge.databinding.ActivityActionBinding;
import com.threatfabric.developerchallenge.ui.GameAction;

import javax.inject.Inject;

public class ActionActivity extends ActivityBase<ActivityActionBinding, ActionViewModel>
        implements IActionNavigator {

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    public ActionViewModel getViewModel() {
        return new ViewModelProvider(this, factory).get(ActionViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_action;
    }

    public static Intent getGameActionIntent(Context context,GameAction gameAction){
        Intent intent = new Intent(context, ActionActivity.class);
        intent.putExtra("GameAction",gameAction.ordinal());
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().setNavigator(this);
        Intent intent=getIntent();
        if(intent!=null){
            GameAction gameAction=GameAction.values()[intent.getIntExtra("GameAction",0)];
            switch (gameAction){
                case GameOver:
                    getViewModel().promptGameWin(
                            getResources().getString(R.string.game_over),
                            getResources().getColor(R.color.red_800));
                    break;
                case YouWin:
                    getViewModel().promptGameWin(
                            getResources().getString(R.string.you_win),
                            getResources().getColor(R.color.teal_700));
                    break;
            }
        }
    }


    @Override
    public void gotoBack() {
        Handler handler = new Handler();
        handler.postDelayed(this::finish, 2500);
    }
}