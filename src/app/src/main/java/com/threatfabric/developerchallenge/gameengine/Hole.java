package com.threatfabric.developerchallenge.gameengine;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.threatfabric.developerchallenge.gameengine.core.IGameObject;
import com.threatfabric.developerchallenge.logic.Math2D;
import com.threatfabric.developerchallenge.logic.Circle;

public class Hole implements IGameObject {

    private final Circle circle;
    private final Paint paint;
    private final int zIndex;

    public Hole(Circle circle, int color, int zIndex) {
        this.circle = circle;
        this.zIndex = zIndex;
        paint=new Paint();
        paint.setColor(color);
    }

    public double playerCollide(Player player){
        return (Math2D.distanceTwoPoint(
                player.getX(),player.getY(),
                circle.getX(),circle.getY())-
                (player.getRadius()+circle.getRadius()));
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(circle.getX(),circle.getY(),circle.getRadius(),paint);
    }

    @Override
    public void update() {

    }

    @Override
    public int getZIndex() {
        return zIndex;
    }

    public void update(int x, int y){

    }

    public int getX(){
        return circle.getX();
    }

    public int getY(){
        return circle.getY();
    }
    public int getRadius(){
        return circle.getRadius();
    }
}
