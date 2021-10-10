package com.threatfabric.developerchallenge.gameengine.core;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class OrientationData implements SensorEventListener {
    private final SensorManager manager;
    private final Sensor accelerometer;
    private final Sensor magnometer;
    private final Sensor gyroscopeSensor;

    private float[] accelOutput;
    private float[] magOutput;

    private final float[] orientation = new float[3];
    private float[] startOrientation = null;
    private final IOrientationListener orientationListener;

    public OrientationData(Context context, IOrientationListener orientationListener) {
        manager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gyroscopeSensor = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        this.orientationListener=orientationListener;
    }

    public float[] getOrientation() {
        return orientation;
    }

    public float[] getStartOrientation() {
        return startOrientation;
    }

    private boolean hasStartOrientationValue() {
        return startOrientation!=null;
    }

    public float getPitch(){
        if (!hasStartOrientationValue())
            return 0;
        return getOrientation()[1]-getStartOrientation()[1];
    }

    public float getRoll(){
        if (!hasStartOrientationValue())
            return 0;
        return getOrientation()[2]-getStartOrientation()[2];
    }



    public void register() {
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(this, magnometer, SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void pause() {
        manager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            accelOutput = event.values;
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            magOutput = event.values;
        else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
            magOutput = event.values;
        if(accelOutput != null && magOutput != null) {
            float[] rotationMatrix= new float[9];
            float[] inclinationMatrix = new float[9];
            boolean success = SensorManager.getRotationMatrix(rotationMatrix, inclinationMatrix, accelOutput, magOutput);
            if(success) {
                SensorManager.getOrientation(rotationMatrix, orientation);
                if(startOrientation == null) {
                    startOrientation = new float[orientation.length];
                    System.arraycopy(orientation, 0, startOrientation, 0, orientation.length);
                }
                if(orientationListener!=null && hasStartOrientationValue()){
                    orientationListener.onPitchRollChange(getPitch(),getRoll());
                }
            }

        }
    }
}
