package com.typ.scenery.views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.typ.scenery.R;

import java.util.Random;

public class Cloud extends View {

    Random random = new Random();
    private int width = 380;
    private int height = 380;
    private ObjectAnimator moveAnimation;
    private ObjectAnimator hideShowAnimation;

    public Cloud(Context context) {
        super(context);
    }

    public Cloud(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Cloud(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i("SceneryView", "Location Changed to " + bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setWidth(w);
        setHeight(h);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundResource(R.drawable.cloud_icon2);
    }

    public void moveCloud() {
        int duration = random.nextInt(60 * 1000);
        if (duration < 12000) {
            duration = 20000;
        }
        moveAnimation = ObjectAnimator.ofFloat(this, "x", - width);
        moveAnimation.setInterpolator(new LinearInterpolator());
        moveAnimation.setDuration(duration);
        moveAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setTranslationY(random.nextInt(400));
                animation.setDuration(random.nextInt(60 * 1000));
                moveAnimation.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        moveAnimation.start();
    }

    public void stopCloud() {
        if (moveAnimation.isRunning()) {
            moveAnimation.pause();
        }
    }

}
