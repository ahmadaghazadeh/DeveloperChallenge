package com.threatfabric.developerchallenge.logic;


public class Circle {

    private int  x;
    private int  y;
    private int radius;

    public Circle(int  x,int y,int radius) {
        setX(x);
        setY(y);
        setRadius(radius);
        this.radius=radius;
    }


    public void move(int x,int y) {
       setX(x);
       setY(y);
    }


    public int getRadius() {
        return radius;
    }


    public int getX() {
        return x;
    }

    public void setX(int x)   {
        if(x<0){
            x=radius;
        }
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y)   {
        if(y<0){
            y=radius;
        }
        this.y = y;
    }

    public void setRadius(int radius)  {
        this.radius=radius;
    }
}
