package com.threatfabric.developerchallenge.gameengine.core;

import android.graphics.Canvas;

public interface IGameObject {
    void draw(Canvas canvas);
    void update();
    int getZIndex();
}
