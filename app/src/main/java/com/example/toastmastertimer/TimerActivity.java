package com.example.toastmastertimer;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    TextView tvTimer;
    ProgressBar pbTimer;
    ProgressBar pbTimerRed;
    CountDownTimer mCountdownTimer;
    ConstraintLayout layout;

    long fromTime;
    long toTime;
    long yellowTime;
    int timeInYellow;
    int timeInRed;
    long secondsUntilFinished;
    long millisleft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        tvTimer = findViewById(R.id.tvTimer);
        pbTimer = findViewById(R.id.pbTimer);
        pbTimerRed = findViewById(R.id.pbTimerRed);
        layout = findViewById(R.id.layout);

        Intent timerIntent = getIntent();
        Bundle timerBundle =  timerIntent.getExtras();
        fromTime = timerBundle.getLong("From");
        toTime = timerBundle.getLong("To");
        millisleft = toTime;
        yellowTime = toTime - fromTime;
        startTimer();
    }

    private void startTimer(){
        mCountdownTimer = new CountDownTimer(millisleft, 1000) {

            public void onTick(long millisUntilFinished) {

                millisleft = millisUntilFinished;
                secondsUntilFinished = millisUntilFinished/1000;
                updateTimer();
                timeInYellow = ((int)toTime+1000-(int)millisUntilFinished)*100/(int)fromTime;
                pbTimer.setProgress(timeInYellow);

                if (millisUntilFinished < yellowTime){
                    layout.setBackgroundColor(Color.YELLOW);
                    timeInRed = ((int)yellowTime +1000-(int)millisUntilFinished)*100/(int)yellowTime;
                    pbTimerRed.setProgress(timeInRed);
                }
            }

            public void onFinish() {
                    layout.setBackgroundColor(Color.RED);
                    tvTimer.setText("done!");
            }
        }.start();
    }

    private void updateTimer(){
        int minutes = (int) secondsUntilFinished / 60;
        int seconds = (int) secondsUntilFinished % 60;
        String timeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        tvTimer.setText(timeLeft);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putLong("millisLeft", millisleft);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        millisleft = savedInstanceState.getLong("millisLeft");
        updateTimer();
        mCountdownTimer.cancel();
        startTimer();
    }
}
