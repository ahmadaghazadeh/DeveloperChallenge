package com.threatfabric.developerchallenge.gameengine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.threatfabric.developerchallenge.gameengine.core.IGameObject;

public class Wall implements IGameObject {

    private final Rect rect;
    private final Paint paint;
    private final int zIndex;

    public Wall(Rect rect, int color,int zIndex){
        this.rect = rect;
        this.paint=new Paint();
        paint.setColor(color);
        this.zIndex=zIndex;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(rect,paint);
    }

    @Override
    public void update() {

    }

    @Override
    public int getZIndex() {
        return zIndex;
    }
    public Rect getRect(){
        return rect;
    }
}
