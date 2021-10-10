package com.threatfabric.developerchallenge.gameengine;

import android.graphics.Point;

public class CollidePoint {
    private final Point point;
    private final double value;

    public CollidePoint(Point collideSide, double value) {
        this.point = collideSide;
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public Point getPoint() {
        return point;
    }
}
