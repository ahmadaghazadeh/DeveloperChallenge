package com.threatfabric.developerchallenge.gameengine.core;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.threatfabric.developerchallenge.gameengine.Constants;

public class MainThread extends Thread{
    public  static  final int MAX_FPS=120;
    private double averageFPS;
    private final SurfaceHolder surfaceHolder;
    private final GameView gameView;
    private boolean isRunning;
    private static final int ONE_MILLION=1000000;
    private long elapsedTime=5000;
    private long frameTime;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView){
        super();
        this.surfaceHolder=surfaceHolder;
        this.gameView = gameView;
    }
    public void setRunning(boolean isRunning){
        this.isRunning=isRunning;
    }

    @Override
    public void run(){
        long startTime;
        long timeMillis=1000/MAX_FPS;
        long waitTime;
        int frameCount=0;
        long totalTime=0;
        long targetTime=1000/MAX_FPS;
        if(frameTime < Constants.INIT_TIME)
            frameTime = Constants.INIT_TIME;
        int elapsedTime = (int)(System.currentTimeMillis() - frameTime);
        frameTime = System.currentTimeMillis();


        while (isRunning){
            startTime=System.nanoTime();
            Canvas canvas = null;

            try{
                canvas =this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gameView.update(elapsedTime);
                    this.gameView.draw(canvas);

                }
            }catch (Exception ignored){}
            finally {
                if(canvas !=null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception ignored){}
                }
             }
            timeMillis=(System.nanoTime()-startTime)/ONE_MILLION;
            waitTime=targetTime-timeMillis;
            try {
                if(waitTime>0){
                    this.sleep(waitTime);
                }
            }catch (Exception ignored){}

            totalTime+=System.nanoTime()-startTime;
            frameCount++;
            if(frameCount==MAX_FPS){
                averageFPS=1000/((totalTime/frameCount)/ONE_MILLION);
                frameCount=0;
                totalTime=0;
                System.out.println(averageFPS);
            }

        }

    }
}
