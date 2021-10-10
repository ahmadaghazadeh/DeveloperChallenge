package com.threatfabric.developerchallenge.gameengine;

import com.threatfabric.developerchallenge.gameengine.core.CollideSide;

public class CollideLine {
    private final CollideSide collideSide;
    private final int value;

    public CollideLine(CollideSide collideSide, int value) {
        this.collideSide = collideSide;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public CollideSide getCollideSide() {
        return collideSide;
    }
}
