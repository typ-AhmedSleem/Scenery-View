package com.typ.scenery.utils;

import android.graphics.Point;

public class MathUtils {

    public static int calculateStartAngleBetween2Points(Point firstPoint, Point secondPoint) {
        return (int) (180 / Math.PI * Math.atan2(firstPoint.y - secondPoint.y, firstPoint.x - secondPoint.x));
    }

}
