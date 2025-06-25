package com.example.triloc;

// ReferenceObject.java
public class ReferenceObject {
    private String name;
    private float objectHeight; // in meters
    private float horizontalAngle; // in degrees (0 = front, 90 = right)

    public ReferenceObject(String name, float objectHeight, float horizontalAngle) {
        this.name = name;
        this.objectHeight = objectHeight;
        this.horizontalAngle = horizontalAngle;
    }

    public String getName() {
        return name;
    }

    public float getObjectHeight() {
        return objectHeight;
    }

    public float getHorizontalAngle() {
        return horizontalAngle;
    }
}
