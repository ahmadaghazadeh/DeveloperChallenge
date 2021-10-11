package com.threatfabric.developerchallenge.gameengine;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.threatfabric.developerchallenge.gameengine.core.IGameObject;
import com.threatfabric.developerchallenge.gameengine.core.IGaveViewUpdate;
import com.threatfabric.developerchallenge.gameengine.core.IOrientationListener;
import com.threatfabric.developerchallenge.logic.Math2D;
import com.threatfabric.developerchallenge.logic.Circle;
import com.threatfabric.developerchallenge.ui.mainactivity.IGameInteractive;

import java.util.LinkedList;
import java.util.List;

public class GamePlay implements IOrientationListener, IGaveViewUpdate {

    private final IGameInteractive gameInteractive;
    private float xSpeed;
    private float ySpeed;

    private final float speedFactor=1500f;
    private Player player;
    private final List<IGameObject> walls=new LinkedList<>();
    private final List<IGameObject> blackHoles=new LinkedList<>();
    private final int thresholdMovement=5;
    private final int ballRadius=Constants.WIDTH_WALL-2;
    private final int holeRadius =ballRadius+5;

    private final int thirdScreenWidth=Constants.SCREEN_WIDTH/4;
    private final int obstacleHeight=(Constants.SCREEN_HEIGHT/5)*4;
    private final int halfWallWidth=(Constants.WIDTH_WALL)/2;
    private boolean playerDeath=false;
    private boolean playerWin=false;
    private boolean gameStopped=false;
    private Point deathPoint;
    private Point winPoint;
    private Hole winHole;

    public GamePlay(IGameInteractive gameInteractive) {
        this.gameInteractive = gameInteractive;
        initializeGameObjects();
    }

    public void initializeGameObjects() {
        initializeBackground();
        initializePlayer();
        initializeWalls();
        initializeBlackHoles();
        initializeWinHole();
    }

    private void initializeWinHole() {
        winHole= new Hole(new Circle(
                Constants.WIDTH_WALL+ holeRadius +15,
                Constants.SCREEN_HEIGHT-3*holeRadius, holeRadius), Color.GREEN,3);
        gameInteractive.initializeObject(winHole);
    }

    @Override
    public void onGameUpdate(long elapsedTime) {
        int x=  player.getX() ;
        int y= player.getY()  ;

        int newX =(int)( (Math.abs(xSpeed*elapsedTime) > thresholdMovement ? xSpeed*elapsedTime : 0)+x);
        int newY =(int)(y-( Math.abs(ySpeed*elapsedTime) >thresholdMovement ? ySpeed*elapsedTime : 0));


        Point nextPoint=getSurroundingObstaclePoint(x,y,newX,newY);

        blackHolesCollide();

        winHoleCollide();

        if(playerDeath){
            playerAnimation(deathPoint, gameInteractive::onGameOver);
        }else if (playerWin){
            playerAnimation(winPoint, gameInteractive::onWin);
        }
        else {
            player.update(nextPoint.x,nextPoint.y);
        }
    }

    private Point getSurroundingObstaclePoint(int x,int y,int newX,int newY){
        boolean down=player.wallDownCollide(walls);
        boolean top=player.wallTopCollide(walls);
        boolean right=player.wallRightCollide(walls);
        boolean left=player.wallLeftCollide(walls);
        if(newY>y){
            if(down){
                newY=y;
            }
        }else {
            if(top){
                newY=y;
            }
        }
        if(newX>x){
            if(right){
                newX=x;
            }
        }else {
            if(left){
                newX=x;
            }
        }

        if(newX<0){
            newX=Constants.WIDTH_WALL+player.getRadius();
        }else if(newX>Constants.SCREEN_WIDTH)
        {
            newX=Constants.SCREEN_WIDTH-Constants.WIDTH_WALL-player.getRadius();
        }

        if(newY<0){
            newY=Constants.WIDTH_WALL+player.getRadius();
        }else if(newY>Constants.SCREEN_HEIGHT) {
            newY=Constants.SCREEN_HEIGHT-Constants.WIDTH_WALL- player.getRadius();
        }
        return new Point(newX,newY);
    }
    private void blackHolesCollide(){
        for (IGameObject gameObject:blackHoles) {
            Hole hole =((Hole)gameObject);
            if(Math2D.distanceTwoPoint(hole.getX(), hole.getY(),
                    player.getX(),player.getY())-player.getRadius()- hole.getRadius()<0){
                playerDeath=true;
                deathPoint=new Point(hole.getX(), hole.getY());
            }
        }
    }

    private void winHoleCollide(){
            if(Math2D.distanceTwoPoint(winHole.getX(), winHole.getY(),
                    player.getX(),player.getY())-player.getRadius()- winHole.getRadius()<0){
                playerWin=true;
                winPoint=new Point(winHole.getX(), winHole.getY());
            }
    }

    private void playerAnimation(Point target,Runnable runnable){
        player.setRadius(player.getRadius()-1);
        Point point=Math2D.move(player.getX(),player.getY(),target.x,target.y,5);
        player.update(point.x,point.y);
        if((target.x==player.getX() && target.y==player.getY()) || player.getRadius()<15 ){
            if(!gameStopped){
                gameStopped=true;
                runnable.run();
            }
        }
    }

    private void initializeBackground() {
        Background background=new Background(Color.WHITE,1);
        gameInteractive.initializeObject(background);
    }

    private void initializeWalls() {
        Wall left= getWall(0,
                0,Constants.WIDTH_WALL,Constants.SCREEN_HEIGHT);
        walls.add(left);

        Wall right=getWall(Constants.SCREEN_WIDTH-Constants.WIDTH_WALL,
                0,Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
        walls.add(right);

        Wall top=getWall(0,
                0,Constants.SCREEN_WIDTH,Constants.WIDTH_WALL);
        walls.add(top);

        Wall down=getWall(0,
                Constants.SCREEN_HEIGHT-Constants.WIDTH_WALL,
                Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
        walls.add(down);


        Wall obstacleDown1= getWall(thirdScreenWidth-halfWallWidth,
                Constants.SCREEN_HEIGHT-obstacleHeight,
                thirdScreenWidth+halfWallWidth,
                Constants.SCREEN_HEIGHT);
        walls.add(obstacleDown1);

        Wall obstacleDown2= getWall(
                3*thirdScreenWidth-halfWallWidth,
                Constants.SCREEN_HEIGHT-obstacleHeight,
                3*thirdScreenWidth+halfWallWidth,
                Constants.SCREEN_HEIGHT);
        walls.add(obstacleDown2);

        Wall obstacleTop= getWall(
                (Constants.SCREEN_WIDTH/2)-halfWallWidth,
                0,
                (Constants.SCREEN_WIDTH/2)+halfWallWidth,
                obstacleHeight);
        walls.add(obstacleTop);

        Wall obstacleFloating= getWall(
                Constants.SCREEN_WIDTH/2-2*ballRadius,
                obstacleHeight+4*ballRadius,
                Constants.SCREEN_WIDTH/2+2*ballRadius,
                obstacleHeight+4*ballRadius+Constants.WIDTH_WALL);

        walls.add(obstacleFloating);
        gameInteractive.initializeObjects(walls);

    }


    private Wall getWall(int left,int top, int right,int bottom) {
        Rect rect = new Rect(left,
                top, right, bottom);
        return new Wall(rect, Color.LTGRAY, 2);
    }

    private void initializeBlackHoles() {

        blackHoles.add(getBlackHole(Constants.WIDTH_WALL+ holeRadius +2,
                Constants.SCREEN_HEIGHT+ holeRadius -10*ballRadius));

        blackHoles.add(getBlackHole(Constants.WIDTH_WALL+ holeRadius +2,
                Constants.SCREEN_HEIGHT+ holeRadius -20*ballRadius));

        blackHoles.add(getBlackHole(Constants.WIDTH_WALL+ holeRadius +2,
                Constants.SCREEN_HEIGHT+ holeRadius -30*ballRadius));

        blackHoles.add(getBlackHole(Constants.WIDTH_WALL+ holeRadius +2,
                Constants.SCREEN_HEIGHT+ holeRadius -40*ballRadius));

        blackHoles.add(getBlackHole(thirdScreenWidth-halfWallWidth- holeRadius -2,
                Constants.SCREEN_HEIGHT+ holeRadius -15*ballRadius));

        blackHoles.add(getBlackHole(thirdScreenWidth-halfWallWidth- holeRadius -2,
                Constants.SCREEN_HEIGHT+ holeRadius -25*ballRadius));

        blackHoles.add(getBlackHole(thirdScreenWidth-halfWallWidth- holeRadius -2,
                Constants.SCREEN_HEIGHT+ holeRadius -35*ballRadius));

        blackHoles.add(getBlackHole(thirdScreenWidth-halfWallWidth- holeRadius -2,
                Constants.SCREEN_HEIGHT+ holeRadius -46*ballRadius));

        blackHoles.add(getBlackHole(thirdScreenWidth+halfWallWidth+ holeRadius +2,
                Constants.SCREEN_HEIGHT+ holeRadius -42*ballRadius));

        blackHoles.add(getBlackHole(thirdScreenWidth+halfWallWidth+ holeRadius +2,
                Constants.SCREEN_HEIGHT+ holeRadius -37*ballRadius));

        blackHoles.add(getBlackHole(thirdScreenWidth+halfWallWidth+ holeRadius +2,
                Constants.SCREEN_HEIGHT+ holeRadius -22*ballRadius));

        blackHoles.add(getBlackHole((Constants.SCREEN_WIDTH/2)-halfWallWidth- holeRadius -2,
                Constants.SCREEN_HEIGHT+ holeRadius -48*ballRadius));

        blackHoles.add(getBlackHole((Constants.SCREEN_WIDTH/2)-halfWallWidth- holeRadius -2,
                Constants.SCREEN_HEIGHT+ holeRadius -30*ballRadius));

        blackHoles.add(getBlackHole((Constants.SCREEN_WIDTH/2)-halfWallWidth- holeRadius -2,
                Constants.SCREEN_HEIGHT+ holeRadius -11*ballRadius));

        blackHoles.add(getBlackHole((Constants.SCREEN_WIDTH/2)+halfWallWidth+ holeRadius +2,
                Constants.SCREEN_HEIGHT+ holeRadius -11*ballRadius));

        blackHoles.add(getBlackHole((Constants.SCREEN_WIDTH/2)+halfWallWidth+ holeRadius +2,
                Constants.SCREEN_HEIGHT+ holeRadius -19*ballRadius));

        blackHoles.add(getBlackHole((Constants.SCREEN_WIDTH/2)+halfWallWidth+ holeRadius +2,
                Constants.SCREEN_HEIGHT+ holeRadius -39*ballRadius));

        blackHoles.add(getBlackHole(3*thirdScreenWidth-halfWallWidth- holeRadius -2,
                Constants.SCREEN_HEIGHT+ holeRadius -29*ballRadius));

        blackHoles.add(getBlackHole(3*thirdScreenWidth+halfWallWidth+ holeRadius +2,
                Constants.SCREEN_HEIGHT+ holeRadius -41*ballRadius));

        blackHoles.add(getBlackHole(3*thirdScreenWidth+halfWallWidth+ holeRadius +2,
                Constants.SCREEN_HEIGHT+ holeRadius -12*ballRadius));

        blackHoles.add(getBlackHole(Constants.SCREEN_WIDTH-Constants.WIDTH_WALL- holeRadius -2,
                Constants.SCREEN_HEIGHT+ holeRadius -28*ballRadius));

        gameInteractive.initializeObjects(blackHoles);

    }

    private Hole getBlackHole(int x, int y) {
        return new Hole(new Circle(x,y, holeRadius), Color.BLACK,3);
    }

    private void initializePlayer(){
        int startX= Constants.SCREEN_WIDTH-Constants.WIDTH_WALL*3;
        int startY=Constants.SCREEN_HEIGHT-Constants.WIDTH_WALL*3;
        player=new Player(new Circle(startX,startY,ballRadius), Color.RED,4);
        gameInteractive.initializeObject(player);
    }

    @Override
    public void onPitchRollChange(float pitch, float roll) {
        xSpeed =2*roll*Constants.SCREEN_WIDTH/speedFactor;
        ySpeed =pitch*Constants.SCREEN_HEIGHT/speedFactor;
    }


}
