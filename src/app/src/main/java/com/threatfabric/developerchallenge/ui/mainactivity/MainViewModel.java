package com.threatfabric.developerchallenge.ui.mainactivity;

import com.threatfabric.developerchallenge.base.activity.ViewModelBase;
import com.threatfabric.developerchallenge.gameengine.GamePlay;
import com.threatfabric.developerchallenge.gameengine.core.IGameObject;

import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends ViewModelBase<IMainNavigator>implements IGameInteractive {
    GamePlay gamePlay;
    @Inject
    public MainViewModel() {

    }
    public void initializeGame() {
        gamePlay=new GamePlay(this);
    }

    @Override
    public void initializeObject(IGameObject gameObject) {
        getNavigator().initializeObject(gameObject);
    }

    @Override
    public void initializeObjects(List<IGameObject> gameObject) {
        getNavigator().initializeObjects(gameObject);
    }

    @Override
    public void onGameOver() {
        getNavigator().onGameOver();
    }

    @Override
    public void onWin() {
        getNavigator().onWin();
    }

    public void onPitchRollChange(float pitch, float roll) {
        gamePlay.onPitchRollChange(pitch,roll);
    }

    public void onGameUpdate(long elapsedTime) {
        gamePlay.onGameUpdate(elapsedTime);
    }
}
