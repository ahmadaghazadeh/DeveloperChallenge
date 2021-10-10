package com.threatfabric.developerchallenge.gameengine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.threatfabric.developerchallenge.gameengine.core.CollideSide;
import com.threatfabric.developerchallenge.gameengine.core.IGameObject;
import com.threatfabric.developerchallenge.logic.Math2D;

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

    public CollideLine playerCollide(Player player){
        float playerX=player.getX();
        float playerY=player.getY();
        float radius=player.getRadius();

        float value= (float) (Math2D.distancePointLine(playerX,playerY,
                        rect.left,rect.top,rect.right,rect.top)-radius);

        if(value<=0)
        {
            return new CollideLine(CollideSide.Y,(int)value);
        }

        value= (float) (Math2D.distancePointLine(playerX,playerY,
                rect.left,rect.bottom,rect.right,rect.bottom)-radius);
        if(value<=0)
        {
            return new CollideLine(CollideSide.Y,(int)value);
        }

        value= (float) (Math2D.distancePointLine(playerX,playerY,
                rect.left,rect.top,rect.left,rect.bottom)-radius );
        if(value<=0)
            return new CollideLine(CollideSide.X,(int)value);

        value= (float) (Math2D.distancePointLine(playerX,playerY,
                rect.right,rect.top,rect.right,rect.bottom)-radius );

        if(value<=0){
            return new CollideLine(CollideSide.X,(int)value);
        }

        return new CollideLine(CollideSide.None,0);
    }

    public CollidePoint playerCollideEdge(Player player){
        float playerX=player.getX();
        float playerY=player.getY();
        float radius=player.getRadius();

        double value= (float) (Math2D.distanceTwoPoint(playerX,playerY,
                rect.left,rect.top)-radius);

        if(value<=0)
        {
            return new CollidePoint(new Point(rect.left,rect.top),value);
        }

        value= (float) (Math2D.distanceTwoPoint(playerX,playerY,
                rect.left,rect.bottom)-radius);

        if(value<=0)
        {
            return new CollidePoint(new Point(rect.left,rect.bottom),value);
        }

        value= (float) (Math2D.distanceTwoPoint(playerX,playerY,
                rect.right,rect.top)-radius);

        if(value<=0)
        {
            return new CollidePoint(new Point(rect.right,rect.top),value);
        }

        value= (float) (Math2D.distanceTwoPoint(playerX,playerY,
                rect.right,rect.bottom)-radius);

        if(value<=0)
        {
            return new CollidePoint(new Point(rect.right,rect.bottom),value);
        }

        return new CollidePoint(new Point(rect.left,rect.bottom),value);
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }
    public Rect getRect(){
        return rect;
    }
}
