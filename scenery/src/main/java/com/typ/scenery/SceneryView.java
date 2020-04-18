package com.typ.scenery;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;

import com.typ.scenery.enums.eSunType;
import com.typ.scenery.enums.eTimeView;
import com.typ.scenery.views.Cloud;
import com.typ.scenery.views.Moon;
import com.typ.scenery.views.Star;
import com.typ.scenery.views.Sun;

import java.util.ArrayList;
import java.util.Random;

public class SceneryView extends FrameLayout {

    private Sun sun;
    private Moon moon;
    private Point screenSize = new Point();
    private Random random = new Random();
    private int sunWidth;
    private int sunHeight;
    private int moonWidth;
    private int moonHeight;
    private int starsCount;
    private int cloudsCount;
    @ColorRes private int[] colorsList;
    private eTimeView defaultSceneryView;
    private ValueAnimator colorTransactionAnimator;

    public SceneryView(Context context) {
        super(context);
        Log.d("SceneryView", "Scenery View Created without attrs");
        initializeSceneryView(null);
    }

    public SceneryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d("SceneryView", "Scenery View Created with attrs");
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.SceneryView);
        initializeSceneryView(attributes);
        attributes.recycle();
    }

    public SceneryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initializeSceneryView(TypedArray attributes) {
        if (attributes == null) {
            Log.d("SceneryView", "Initialize SceneryView without attrs");
            sunWidth = 340;
            sunHeight = 340;
            moonWidth = 340;
            moonHeight = 340;
            starsCount = 12;
            cloudsCount = 3;
            defaultSceneryView = eTimeView.DAY;
            colorsList = getResources().getIntArray(R.array.colors);
        } else {
            Log.d("SceneryView", "Initialize SceneryView with attrs");
            //sunWidth = attributes.getInt(R.styleable.SceneryView_svSunWidth, 340);
            //sunHeight = attributes.getInt(R.styleable.SceneryView_svSunHeight, 340);
            //moonWidth = attributes.getInt(R.styleable.SceneryView_svMoonWidth, 340);
            //moonHeight = attributes.getInt(R.styleable.SceneryView_svMoonHeight, 340);
            sunWidth = 340;
            sunHeight = 340;
            moonWidth = 340;
            moonHeight = 340;
            starsCount = attributes.getInt(R.styleable.SceneryView_svStarsNumber, 12);
            cloudsCount = attributes.getInt(R.styleable.SceneryView_svCloudsNumber, 3);
            colorsList = getResources().getIntArray(attributes.getResourceId(R.styleable.SceneryView_svColorsList, R.array.colors));
            //defaultSceneryView = eTimeView.getEnumByOrdinal(attributes.getInt(R.styleable.SceneryView_svDefaultView, 0));
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        screenSize = new Point();
        getDisplay().getRealSize(screenSize);
        createViews();
    }

    @SuppressLint("NewApi")
    public void createViews() {
        random.setSeed(100);
        createSun();
        createMoon();
        createStars();
        createClouds();
        moveClouds();
        setupColorAnimator();
    }

    private void setupColorAnimator() {
        colorTransactionAnimator = new ValueAnimator();
        colorTransactionAnimator.setIntValues(colorsList[5], colorsList[0]);
        colorTransactionAnimator.setDuration(2000);
        colorTransactionAnimator.setInterpolator(new LinearInterpolator());
        colorTransactionAnimator.setEvaluator(new ArgbEvaluator());
        colorTransactionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setBackgroundColor((int) animation.getAnimatedValue());
            }
        });
    }

    public void createStars() {
        if (isStarExists()) {
            Log.d("SceneryView", "Stars exists.. doing nothing.");
        } else {
            Log.d("SceneryView", "Stars not exists.. creating them now.");
            ViewGroup.LayoutParams layoutParams = new LayoutParams(20, 20);
            for (int i = 0; i < starsCount; i++) {
                Log.d("Scenery", "Star number " + i);
                Star star = new Star(getContext());
                int x = random.nextInt(screenSize.x);
                int y = random.nextInt(400);
                star.setTranslationX(x);
                star.setTranslationY(y);
                star.setTag("Star " + i);
                addView(star, i, layoutParams);
            }
            Log.d("SceneryView", "Created " + starsCount + " Stars :)");
        }
    }

    private boolean isStarExists() {
        boolean isStarsExists = false;
        for (int i = 0; i < starsCount; i++) {
            Star star = findViewWithTag("Star " + i);
            isStarsExists = star != null;
        }
        return isStarsExists;
    }

    private void showStars() {
        if (isStarExists()) {
            for (int i = 0; i < getStars().size(); i++) {
                getStars().get(i).showStar();
            }
        } else {
            Log.d("SceneryView", "Stars not exists.. doing nothing.");
        }
    }

    private void hideStars() {
        if (isStarExists()) {
            for (int i = 0; i < getStars().size(); i++) {
                getStars().get(i).hideStar();
            }
        } else {
            Log.d("SceneryView", "Stars not exists.. doing nothing.");
        }
    }

    private void randomizeStarsLocations() {
        ArrayList<Star> stars = getStars();
        for (int i = 0; i < starsCount; i++) {
            int x = random.nextInt(screenSize.x);
            int y = random.nextInt(400);
            stars.get(i).setTranslationX(x);
            stars.get(i).setTranslationY(y);
        }
    }

    private ArrayList<Star> getStars() {
        ArrayList<Star> starsList = new ArrayList<>();
        if (isStarExists()) {
            for (int i = 0; i < starsCount; i++) {
                starsList.add(i, (Star) findViewWithTag("Star " + i));
            }
        } else {
            Log.d("SceneryView", "There is no stars right now.");
        }
        return starsList;
    }

    @SuppressLint("NewApi")
    private void createSun() {
        sun = new Sun(getContext());
        float target = ((screenSize.x / 2) + 40);
        sun.setY(- sunHeight);
        sun.setX(target);
        addView(sun, sunWidth, sunHeight);
    }

    @SuppressLint("NewApi")
    private void createMoon() {
        float target = ((screenSize.x / 2) + 40);
        moon = new Moon(getContext());
        moon.setY(- moonHeight);
        moon.setX(target);
        addView(moon, moonWidth, moonHeight);
    }

    private void createClouds() {
        if (isCloudsExists()) {
            Log.d("SceneryView", "Clouds exists.. doing nothing.");
        } else {
            Log.d("SceneryView", "Clouds not exists.. creating them now.");
            Random random1 = new Random();
            random1.setSeed(100);
            ViewGroup.LayoutParams layoutParams = new LayoutParams(380, 380);
            for (int i = 0; i < cloudsCount; i++) {
                Log.d("Scenery", "Cloud number " + i);
                Cloud cloud = new Cloud(getContext());
                cloud.setTranslationY(random.nextInt(400));
                cloud.setTranslationX(screenSize.x);
                cloud.setTag("Cloud " + i);
                addView(cloud, i, layoutParams);
            }
            Log.d("SceneryView", "Created " + cloudsCount + " Clouds :)");
        }
    }

    private boolean isCloudsExists() {
        boolean isCloudsExists = false;
        for (int i = 0; i < cloudsCount; i++) {
            Cloud cloud = findViewWithTag("Cloud " + i);
            isCloudsExists = cloud != null;
        }
        return isCloudsExists;
    }

    private ArrayList<Cloud> getClouds() {
        ArrayList<Cloud> clouds = new ArrayList<>();
        if (isCloudsExists()) {
            for (int i = 0; i < cloudsCount; i++) {
                clouds.add((Cloud) findViewWithTag("Cloud " + i));
            }
        } else {
            Log.d("SceneryView", "There is no clouds right now.");
        }
        return clouds;
    }

    private void moveClouds() {
        ArrayList<Cloud> clouds = getClouds();
        for (int i = 0; i < cloudsCount; i++) {
            clouds.get(i).moveCloud();
        }
    }

    public void changeView(eTimeView timeView) {
        switch (timeView) {
            case DAY: // Fajr
                sun.sunSet();
                moon.show(Math.round(- moonHeight / 1.2f));
                showStars();
                colorTransactionAnimator.setIntValues(colorsList[5], colorsList[0]);
                colorTransactionAnimator.start();
                break;
            case SUNRISE: // Sunrise
                sun.changeSun(eSunType.WEAK_SUN);
                sun.sunRise(Math.round(- sunHeight / 1.5f));
                moon.hide();
                hideStars();
                colorTransactionAnimator.setIntValues(colorsList[0], colorsList[1]);
                colorTransactionAnimator.start();
                randomizeStarsLocations();
                break;
            case MID_DAY: // Dhuhr
                moon.hide();
                sun.changeSun(eSunType.MID_SUN);
                sun.sunRise(Math.round((- sunHeight / 2f) + (sunHeight / 10f)));
                colorTransactionAnimator.setIntValues(colorsList[1], colorsList[2]);
                colorTransactionAnimator.start();
                break;
            case CENTER_OF_DAY: // Asr
                moon.hide();
                sun.changeSun(eSunType.WEAK_SUN);
                sun.sunRise(Math.round(- sunHeight / 2.2f));
                colorTransactionAnimator.setIntValues(colorsList[2], colorsList[3]);
                colorTransactionAnimator.start();
                break;
            case BEFORE_NIGHT: // Maghrib & Sunset
                sun.sunSet();
                moon.hide();
                showStars();
                colorTransactionAnimator.setIntValues(colorsList[3], colorsList[4]);
                colorTransactionAnimator.start();
                break;
            case NIGHT: // Isha & Qiyam
                sun.sunSet();
                moon.show(Math.round(- moonHeight / 2f));
                showStars();
                colorTransactionAnimator.setIntValues(colorsList[4], colorsList[5]);
                colorTransactionAnimator.start();
                break;
        }
    }

    @SuppressLint("NewApi")
    public Sun getSun() {
        Sun s = sun;
        if (sun != null) {
            return s;
        } else {
            s = new Sun(getContext());
        }
        return s;
    }

    @SuppressLint("NewApi")
    public Moon getMoon() {
        Moon m = moon;
        if (moon != null) {
            return m;
        } else {
            m = new Moon(getContext());
        }
        return m;
    }

    public int getSunWidth() {
        return sunWidth;
    }

    public void setSunWidth(int sunWidth) {
        this.sunWidth = sunWidth;
    }

    public int getSunHeight() {
        return sunHeight;
    }

    public void setSunHeight(int sunHeight) {
        this.sunHeight = sunHeight;
    }

    public int getMoonWidth() {
        return moonWidth;
    }

    public void setMoonWidth(int moonWidth) {
        this.moonWidth = moonWidth;
    }

    public int getMoonHeight() {
        return moonHeight;
    }

    public void setMoonHeight(int moonHeight) {
        this.moonHeight = moonHeight;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public void setStarsCount(int starsCount) {
        this.starsCount = starsCount;
    }

    public int getCloudsCount() {
        return cloudsCount;
    }

    public void setCloudsCount(int cloudsCount) {
        this.cloudsCount = cloudsCount;
    }

    public int[] getColorsList() {
        return colorsList;
    }

    public void setColorsList(int[] colorsList) {
        this.colorsList = colorsList;
    }

    public eTimeView getDefaultSceneryView() {
        return defaultSceneryView;
    }

    public void setDefaultSceneryView(eTimeView defaultSceneryView) {
        this.defaultSceneryView = defaultSceneryView;
    }

    protected int[] getIntArray(int index) {
        return getResources().getIntArray(index);
    }

    public void saveSunAndMoonPositions() {
        // TODO: 17/04/20 save y position of sun and moon in shared preference to continue with when app starts again
    }

}
