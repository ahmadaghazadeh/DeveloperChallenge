package com.threatfabric.developerchallenge.ui.mainactivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import com.threatfabric.developerchallenge.BR;
import com.threatfabric.developerchallenge.R;
import com.threatfabric.developerchallenge.base.activity.ActivityBase;
import com.threatfabric.developerchallenge.databinding.ActivityMainBinding;
import com.threatfabric.developerchallenge.gameengine.Constants;
import com.threatfabric.developerchallenge.gameengine.core.IGameObject;
import com.threatfabric.developerchallenge.gameengine.core.IGaveViewUpdate;
import com.threatfabric.developerchallenge.gameengine.core.IOrientationListener;
import com.threatfabric.developerchallenge.gameengine.core.OrientationData;
import com.threatfabric.developerchallenge.ui.GameAction;
import com.threatfabric.developerchallenge.ui.actionctivity.ActionActivity;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends ActivityBase<ActivityMainBinding, MainViewModel>
        implements IMainNavigator, IOrientationListener,IGaveViewUpdate {
    OrientationData orientationData;
    @Inject
    ViewModelProvider.Factory factory;

    @Override
    public MainViewModel getViewModel() {
        return new ViewModelProvider(this, factory).get(MainViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().setNavigator(this);
        initialize();
    }
    private void initialize(){
        orientationData= new OrientationData(this,this);
        orientationData.register();
        initializeMetrics();
        getViewModel().initializeGame();
        mViewDataBinding.gameView.setGaveViewUpdate(this);
    }


    private void initializeMetrics() {
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Constants.SCREEN_HEIGHT=displayMetrics.heightPixels;
        Constants.SCREEN_WIDTH=displayMetrics.widthPixels;
        if(Constants.SCREEN_WIDTH*4>Constants.SCREEN_HEIGHT*3){
            Constants.WIDTH_WALL=((Constants.SCREEN_HEIGHT*4/3)/37)+2;
        }else{
            Constants.WIDTH_WALL=((Constants.SCREEN_HEIGHT*3/4)/37)+2;
        }

    }


    @Override
    public void onPitchRollChange(float pitch, float roll) {
        mViewDataBinding.getViewModel().onPitchRollChange(pitch,roll);
    }

    @Override
    public void onGameUpdate(long elapsedTime) {
        mViewDataBinding.getViewModel().onGameUpdate(elapsedTime);
    }

    @Override
    public void initializeObject(IGameObject gameObject) {
        mViewDataBinding.gameView.addGameObject(gameObject);
    }

    @Override
    public void initializeObjects(List<IGameObject> gameObject) {
        mViewDataBinding.gameView.addGameObjects(gameObject);
    }

    @Override
    public void onGameOver() {
        actionActivity.launch(ActionActivity.getGameActionIntent(this, GameAction.GameOver));
    }

    @Override
    public void onWin() {
        actionActivity.launch(ActionActivity.getGameActionIntent(this, GameAction.YouWin));
    }

    ActivityResultLauncher<Intent> actionActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            });

    @Override
    protected void onPause() {
        super.onPause();
        orientationData.pause();
    }
}
