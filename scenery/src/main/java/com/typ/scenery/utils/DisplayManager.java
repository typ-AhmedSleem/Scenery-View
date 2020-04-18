package com.typ.scenery.utils;

import android.app.Activity;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;

public class DisplayManager {

    public static Point getScreenRealSize(Activity activity) {
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getRealSize(point);
        return point;
    }

    public static Point getScreenRealSize(ViewGroup viewGroup) {
        Point point = new Point();
        viewGroup.getDisplay().getRealSize(point);
        return point;
    }

    public static Point getScreenRealSize(View view) {
        Point point = new Point();
        view.getDisplay().getRealSize(point);
        return point;
    }

}
