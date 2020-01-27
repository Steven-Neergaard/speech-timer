package com.example.toastmastertimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvFrom;
    TextView tvTo;

    long fromTime;
    long toTime;
    int fromInt;
    int toInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvFrom = findViewById(R.id.tvFrom);
        tvTo = findViewById(R.id.tvTo);
        fromInt = 3;
        toInt = 5;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.btnTableTopic):
                fromTime = 60000;
                toTime = 90000;
                break;
            case R.id.btnStartTimer:
                fromTime = fromInt * 60000;
                toTime = toInt * 60000;
        }
        Bundle timerBundle = new Bundle();
        Intent timerIntent = new Intent(this, TimerActivity.class);
        timerBundle.putLong("From",fromTime);
        timerBundle.putLong("To",toTime);
        timerIntent.putExtras(timerBundle);
        startActivity(timerIntent);
    }

    public void update(){
        tvTo.setText("" + toInt);
        tvFrom.setText("" + fromInt);
    }

    public void onMath(View view) {
        switch (view.getId()) {
            case R.id.btnFromPlus:
                fromInt++;
                if(fromInt == toInt){
                    toInt++;}
                else if(fromInt == 60){
                    break;
                }
                update();
                break;
            case R.id.btnToPlus:
                toInt++;
                if (toInt == 61){
                    break;
                }
                update();
                break;
            case R.id.btnFromMinus:
                if(fromInt==1){
                    break;
                }
                update();
                fromInt--;
                update();
                break;
            case R.id.btnToMinus:
                if(toInt==2){
                    break;
                }
                toInt--;
                if(fromInt == toInt){
                fromInt--; }
                update();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("toInt", toInt);
        outState.putInt("fromInt", fromInt);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        toInt = savedInstanceState.getInt("toInt");
        fromInt = savedInstanceState.getInt("fromInt");
        update();
    }

}
