package com.threatfabric.developerchallenge.gameengine;

import android.graphics.Canvas;

import com.threatfabric.developerchallenge.gameengine.core.IGameObject;

public class Background implements IGameObject {

    private final int color;
    private final int zIndex;
    public Background(int color, int zIndex){
        this.color = color;
        this.zIndex = zIndex;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(color);
    }

    @Override
    public void update() {

    }

    @Override
    public int getZIndex() {
        return zIndex;
    }
}
