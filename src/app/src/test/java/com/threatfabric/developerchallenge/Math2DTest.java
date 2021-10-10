package com.threatfabric.developerchallenge;

import static org.junit.Assert.assertEquals;

import com.threatfabric.developerchallenge.logic.Math2D;

import org.junit.Test;

public class Math2DTest {

    @Test
    public void distancePointLine_Calculated() {
        double expectedDistance=1;
        double calculatedDistance=Math2D.distancePointLine(0,0,1,1,1,2);
        assertEquals(expectedDistance ,calculatedDistance,1);
    }

    @Test
    public void distanceTwoPoint_Calculated() {
        double expectedDistance=1;
        double calculatedDistance=Math2D.distanceTwoPoint(0,0,1,0);
        assertEquals(expectedDistance ,calculatedDistance,1);
    }
}
