package com.threatfabric.developerchallenge;

import static org.junit.Assert.assertEquals;

import com.threatfabric.developerchallenge.logic.Circle;

import org.junit.Test;

public class CircleTest {
    @Test
    public void x_Retrieve() {
        int expectedX=10;
        Circle circle=new Circle(expectedX,10,10);
        assertEquals(expectedX , circle.getX());
    }

    @Test
    public void XLessThan0_RetrieveRadius()  {
        int x=-1;
        int radius=10;
        Circle circle=new Circle(x,10,radius);
        assertEquals(radius , circle.getX());
    }


    @Test
    public void y_Retrieve() {
        int expectedY=10;
        Circle circle=new Circle(10,expectedY,10);
        assertEquals(expectedY , circle.getY());
    }

    public void YLessThan0_RetrieveRadius() {
        int y=-1;
        int radius=10;
        Circle circle=new Circle(10,y,radius);
        assertEquals(radius , circle.getX());
    }

    @Test
    public void radius_Retrieve()  {
        int expectedRadius=10;
        Circle circle=new Circle(10,10,expectedRadius);
        assertEquals(expectedRadius , circle.getRadius());
    }
}
