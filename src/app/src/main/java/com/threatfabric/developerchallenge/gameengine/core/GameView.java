package com.threatfabric.developerchallenge.gameengine.core;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.threatfabric.developerchallenge.gameengine.Constants;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private IGaveViewUpdate gaveViewUpdate;
    MainThread thread;
    private final List<IGameObject> gameObjects=new LinkedList<>();

    public GameView(Context context){
        super(context);

        initialize();
    }

    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initialize();

    }

    public void setGaveViewUpdate(IGaveViewUpdate gaveViewUpdate){
        this.gaveViewUpdate=gaveViewUpdate;
    }
    public void initialize() {

        Constants.INIT_TIME=System.currentTimeMillis();

        getHolder().addCallback(this);

        thread=new MainThread(getHolder(),this);

        setFocusable(true);
    }

    public void addGameObject(IGameObject object){
        gameObjects.add(object);
        Collections.sort(gameObjects, (firstItem, secondItem) -> firstItem.getZIndex()-secondItem.getZIndex());
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        thread=new MainThread(getHolder(),this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
    public void update(long elapsedTime) {
        if(gaveViewUpdate!=null){
            gaveViewUpdate.onGameUpdate(elapsedTime);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(canvas==null) return;
        for (IGameObject object:gameObjects) {
            object.draw(canvas);
        }
    }

    public void addGameObjects(List<IGameObject> gameObject) {
        gameObjects.addAll(gameObject);
        Collections.sort(gameObjects, (firstItem, secondItem) -> firstItem.getZIndex()-secondItem.getZIndex());
    }
}
