package com.typ.scenery.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.typ.scenery.R;

public class Star extends View {

    public Star(Context context) {
        super(context);

    }

    public Star(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Star(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        invalidate();
        int width = getWidth();
        int height = getHeight();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.TRANSPARENT);
        canvas.drawPaint(paint);
        paint.setColor(getResources().getColor(R.color.white));
        paint.setAlpha(150);
        canvas.drawCircle(width / 2, height / 2, 5, paint);
        paint.setColor(getResources().getColor(R.color.white));
        paint.setAlpha(30);
        canvas.drawCircle(width / 2, height / 2, 10, paint);
    }

    public void showStar() {
        final ValueAnimator alphaAnimation = ValueAnimator.ofFloat(0, 255);
        alphaAnimation.setDuration(2500);
        alphaAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setAlpha((float) alphaAnimation.getAnimatedValue());
            }
        });
        alphaAnimation.start();
    }

    public void hideStar() {
        final ValueAnimator alphaAnimation = ValueAnimator.ofFloat(255, 0);
        alphaAnimation.setDuration(2500);
        alphaAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setAlpha((float) alphaAnimation.getAnimatedValue());
            }
        });
        alphaAnimation.start();
    }

}
