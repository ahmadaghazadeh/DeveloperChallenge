package com.threatfabric.developerchallenge.logic;

public class  Math2D {
    public static Double distancePointLine(float pointX,float pointY,
            float x1,float y1,float x2,float y2){
        float px=x2-x1;
        float py=y2-y1;
        float temp=(px*px)+(py*py);
        float u=((pointX - x1) * px + (pointY - y1) * py) / (temp);
        if(u>1){
            u=1;
        }
        else if(u<0){
            u=0;
        }
        float x = x1 + u * px;
        float y = y1 + u * py;

        float dx = x - pointX;
        float dy = y - pointY;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public static double distanceTwoPoint(
            double point1X,
            double point1Y,
            double point2X,
            double point2y) {
        return Math.sqrt((point2y - point1Y) * (point2y - point1Y) + (point2X - point1X) * (point2X - point1X));
    }
}
