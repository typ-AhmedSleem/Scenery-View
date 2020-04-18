package com.typ.scenery.views;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.typ.scenery.enums.eSunType;


public class Sun extends View {

    private static final int SUN_RISE = 1;
    private static final int SUN_SET = 0;
    private eSunType sunType;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Sun(Context context) {
        super(context);
        sunType = eSunType.FULL_SUN;
        setElevation(15);
    }

    public Sun(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Sun(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        invalidate();
        int width = getWidth();
        int height = getHeight();
        int innerSunCircleRadius = 130;
        int middleSunCircleRadius = 170;
        int outerSunCircleRadius = 210;
        switch (sunType) {
            case FULL_SUN:
                Paint sunPaint1 = new Paint();
                sunPaint1.setStyle(Paint.Style.FILL);
                sunPaint1.setColor(Color.TRANSPARENT);
                canvas.drawPaint(sunPaint1);
                sunPaint1.setColor(Color.YELLOW);
                canvas.drawCircle(width / 2, height / 2, innerSunCircleRadius, sunPaint1);
                sunPaint1.setColor(Color.YELLOW);
                sunPaint1.setAlpha(100);
                canvas.drawCircle(width / 2, height / 2, middleSunCircleRadius, sunPaint1);
                sunPaint1.setColor(Color.YELLOW);
                sunPaint1.setAlpha(50);
                canvas.drawCircle(width / 2, height / 2, outerSunCircleRadius, sunPaint1);
                break;
            case MID_SUN:
                Paint sunPaint2 = new Paint();
                sunPaint2.setStyle(Paint.Style.FILL);
                sunPaint2.setColor(Color.TRANSPARENT);
                canvas.drawPaint(sunPaint2);
                sunPaint2.setColor(Color.YELLOW);
                canvas.drawCircle(width / 2, height / 2, innerSunCircleRadius, sunPaint2);
                sunPaint2.setColor(Color.YELLOW);
                sunPaint2.setAlpha(100);
                canvas.drawCircle(width / 2, height / 2, middleSunCircleRadius, sunPaint2);
                break;
            case WEAK_SUN:
                Paint sunPaint3 = new Paint();
                sunPaint3.setStyle(Paint.Style.FILL);
                sunPaint3.setColor(Color.TRANSPARENT);
                canvas.drawPaint(sunPaint3);
                sunPaint3.setColor(Color.YELLOW);
                canvas.drawCircle(width / 2, height / 2, innerSunCircleRadius, sunPaint3);
                break;
        }
    }

    public void changeSun(eSunType sunType) {
        this.sunType = sunType;
    }

    public void sunRise(float destination) {
        ObjectAnimator moveAnimation = ObjectAnimator.ofFloat(this, "y", destination);
        moveAnimation.setDuration(5000);
        moveAnimation.setInterpolator(new LinearInterpolator());
        moveAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                reportMyPositionToSceneryView(SUN_RISE, animation.getCurrentPlayTime());
            }
        });
        moveAnimation.start();
    }

    private void reportMyPositionToSceneryView(int moveType, float currentPosition) {

    }

    public void sunSet() {
        // TODO: 17/04/20 change 450 in move animation with (screenSize.height + getHeight);
        ObjectAnimator moveAnimation = ObjectAnimator.ofFloat(this, "y", - getHeight());
        moveAnimation.setDuration(5000);
        moveAnimation.setInterpolator(new LinearInterpolator());
        moveAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                reportMyPositionToSceneryView(SUN_SET, animation.getCurrentPlayTime());
            }
        });
        moveAnimation.start();
    }

}