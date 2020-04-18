package com.typ.scenery.example;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.typ.scenery.R;
import com.typ.scenery.SceneryView;
import com.typ.scenery.enums.eTimeView;

public class MainActivity extends Activity {

    private SceneryView sceneryView;
    private CountDownTimer countDownTimer;
    private TextView tv;
    private int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenery);
        setupViews();
        changeView();
    }

    private void setupViews() {
        sceneryView = findViewById(R.id.scenery);
        tv = findViewById(R.id.txt);
    }

    private void changeView() {
        countDownTimer = new CountDownTimer(8 * 1000, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                changeSceneryView();
                if (counter >= 5) {
                    counter = 0;
                } else {
                    counter++;
                }
                countDownTimer.start();
            }
        };
        countDownTimer.start();
    }

    private void changeSceneryView() {
        switch (counter) {
            case 0: // Fajr of today
                sceneryView.changeView(eTimeView.DAY);
                tv.setText("Day");
                break;
            case 1: // Sunrise
                sceneryView.changeView(eTimeView.SUNRISE);
                tv.setText("Sunrise");
                break;
            case 2: // Dhuhr
                sceneryView.changeView(eTimeView.MID_DAY);
                tv.setText("Mid Day");
                break;
            case 3: // Asr
                sceneryView.changeView(eTimeView.CENTER_OF_DAY);
                tv.setText("Center of Day");
                break;
            case 4: // Maghrib
                sceneryView.changeView(eTimeView.BEFORE_NIGHT);
                tv.setText("Before Night");
                break;
            case 5: // Isha of today
                sceneryView.changeView(eTimeView.NIGHT);
                tv.setText("Night");
                break;
            default:
                tv.setText("Error in counter " + counter);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO: 17/04/20 Save current sun and moon positions in y axis to continue with these values when the app starts again.
        sceneryView.saveSunAndMoonPositions();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
