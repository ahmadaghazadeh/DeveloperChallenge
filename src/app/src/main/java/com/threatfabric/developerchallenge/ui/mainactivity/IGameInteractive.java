package com.threatfabric.developerchallenge.ui.mainactivity;

import com.threatfabric.developerchallenge.gameengine.core.IGameObject;

import java.util.List;

public interface IGameInteractive {
    void initializeObject(IGameObject gameObject);
    void initializeObjects(List<IGameObject> gameObject);
    void onGameOver();
    void onWin();
}
