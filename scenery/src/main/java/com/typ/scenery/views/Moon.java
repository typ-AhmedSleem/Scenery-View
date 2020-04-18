package com.typ.scenery.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.typ.scenery.R;
import com.typ.scenery.enums.eMoonPhase;
import com.typ.scenery.utils.DisplayManager;
import com.typ.scenery.utils.MathUtils;

import org.shredzone.commons.suncalc.MoonIllumination;
import org.shredzone.commons.suncalc.MoonPhase;
import org.shredzone.commons.suncalc.MoonPosition;
import org.shredzone.commons.suncalc.SunTimes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Moon extends View {

    private Point screenRealSize;
    private int bgColor;
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Moon(Context context) {
        super(context);
        setElevation(15);
        bgColor = getResources().getColor(R.color.color_fajr_header);
        MoonPosition moonPosition = MoonPosition.compute()
                                                .now()
                                                .at(30.033333, 31.233334)
                                                .execute();
        MoonPhase moonPhase = MoonPhase.compute()
                                       .now()
                                       .execute();
        SunTimes sunTimes = SunTimes.compute()
                                    .oneDay()
                                    .today()
                                    .at(30.033333, 31.233334)
                                    .execute();
        String sunRise = dateFormat.format(sunTimes.getRise());
        String sunSet = dateFormat.format(sunTimes.getSet());
        int hours = Integer.parseInt(sunSet.substring(0, 2)) - Integer.parseInt(sunRise.substring(0, 2));
        int minutes = Integer.parseInt(sunSet.substring(3, 5)) - Integer.parseInt(sunRise.substring(3, 5));
        Date diff = new Date(2020, 4, 17, hours, minutes, 0);
        //Log.d("SceneryView", moonPosition.toString());
        //Log.d("SceneryView", "Next Moon Phase in " + dateFormat.format(moonPhase.getTime()));
        //Log.d("SceneryView", "Sunrise in " + sunRise);
        //Log.d("SceneryView", "Sunset in " + sunSet);
        //Log.d("SceneryView", "Difference between sunrise and sunset is " + diff.getHours() + " hours " + diff.getMinutes() + " Minutes");
    }

    public Moon(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Moon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private eMoonPhase getCurrentMoonPhase() {
        eMoonPhase currentMoonPhase = eMoonPhase.FULL_MOON;
        MoonIllumination moonIllumination = MoonIllumination.compute().now().execute();
        int moonCompletion = (int) (moonIllumination.getFraction() * 100);
        //Log.d("SceneryView", "Moon Completion is  " + moonCompletion + "%");
        return currentMoonPhase;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        screenRealSize = DisplayManager.getScreenRealSize(this);
        int width = getWidth();
        int height = getHeight();
        int innerMoonCircleRadius = 130;
        int middleMoonCircleRadius = 170;
        int padding = (width - (innerMoonCircleRadius * 2)) / 2;
        Paint moonPaint = new Paint();
        moonPaint.setStyle(Paint.Style.FILL);
        moonPaint.setColor(Color.TRANSPARENT);
        moonPaint.setAntiAlias(true);
        canvas.drawPaint(moonPaint);
        switch (getCurrentMoonPhase()) {
            case NEW_MOON:
                Log.d("SceneryView", "It's new moon.. don't draw it.");
                break;
            case CRESCENT:
                moonPaint.setColor(Color.WHITE);
                canvas.drawCircle(width / 2, height / 2, innerMoonCircleRadius, moonPaint); // The full moon circle
                moonPaint.setColor(bgColor);
                canvas.drawCircle(250, height / 2, innerMoonCircleRadius, moonPaint); // The new moon circle
                break;
            case FIRST_QUARTER:
                moonPaint.setColor(Color.WHITE);
                moonPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(width / 2, height / 2, innerMoonCircleRadius, moonPaint);
                moonPaint.setColor(Color.BLUE);
                moonPaint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(getLeft() + padding, getTop() + padding, getRight() - padding, getBottom() - padding, 270, 180, false, moonPaint);
                break;
            case WAXING_GIBBOUS:
                break;
            case FULL_MOON:
                moonPaint.setColor(Color.WHITE);
                canvas.drawCircle(width / 2, height / 2, innerMoonCircleRadius, moonPaint);
                moonPaint.setColor(Color.WHITE);
                moonPaint.setAlpha(10);
                canvas.drawCircle(width / 2, height / 2, middleMoonCircleRadius, moonPaint);
                break;
            case WANING_GIBBOUS:
                Path pathWaningGibbous = new Path();
                //pathWaningGibbous.addArc(getLeft() + padding, getTop() + padding, getRight() - padding, getBottom() - padding, 90, 180);
                moonPaint.setColor(Color.WHITE);
                moonPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(width / 2, height / 2, innerMoonCircleRadius, moonPaint);
                break;
            case LAST_QUARTER:
                Path pathLastQuarter = new Path();
                Point firstPoint = new Point(getLeft() + padding, getTop() + padding);
                Point secondPoint = new Point(getLeft() + padding, getBottom() - padding);
                Point firstPoint2 = new Point(getLeft() + padding + 90, getTop() + padding);
                Point secondPoint2 = new Point(getLeft() + padding + 90, getBottom() - padding);
                int startAngle1 = MathUtils.calculateStartAngleBetween2Points(secondPoint, firstPoint) - 2;
                int startAngle2 = MathUtils.calculateStartAngleBetween2Points(secondPoint2, firstPoint2);
                pathLastQuarter.addCircle(width / 2, height / 2, innerMoonCircleRadius, Path.Direction.CW);
                pathLastQuarter.addArc(getLeft() + padding, getTop() + padding, getRight() - padding, getBottom() - padding, startAngle1, startAngle1 + 94);
                pathLastQuarter.addArc(getLeft() + padding + 50, getTop() + padding, getRight() - padding, getBottom() - padding, 102, 155);
                Log.d("SceneryView", "First Angle is " + startAngle1);
                Log.d("SceneryView", "Second Angle is " + startAngle2);
                Log.d("SceneryView", "First Point is " + firstPoint);
                Log.d("SceneryView", "Second Point is " + secondPoint);
                moonPaint.setColor(Color.WHITE);
                moonPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(width / 2, height / 2, innerMoonCircleRadius, moonPaint);
                moonPaint.setColor(Color.WHITE);
                moonPaint.setStyle(Paint.Style.STROKE);
                canvas.drawPath(pathLastQuarter, moonPaint);
                canvas.clipPath(pathLastQuarter);
                //canvas.drawArc(getLeft() + padding, getTop() + padding, getRight() - padding, getBottom() - padding, startAngle1, startAngle1 + 94, false, moonPaint);
                //canvas.drawArc(getLeft() + padding + 50, getTop() + padding, getRight() - padding, getBottom() - padding, 102, 155, false, moonPaint);
                break;
            case WAXING_CRESCENT:
                break;
            // TODO: 16/04/20 To be done today or tomorrow

        }
    }

    public void updateNewMoonBg(int bgColor) {
        invalidate();
        this.bgColor = bgColor;
    }

    public void show(float destination) {
        ObjectAnimator moveAnimation = ObjectAnimator.ofFloat(this, "y", destination);
        moveAnimation.setDuration(5000);
        moveAnimation.setInterpolator(new LinearInterpolator());
        moveAnimation.start();
    }

    public void hide() {
        ObjectAnimator moveAnimation = ObjectAnimator.ofFloat(this, "y", - getHeight());
        moveAnimation.setDuration(5000);
        moveAnimation.setInterpolator(new LinearInterpolator());
        moveAnimation.start();
    }

}
