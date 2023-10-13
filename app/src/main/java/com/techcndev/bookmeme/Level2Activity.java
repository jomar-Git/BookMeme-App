package com.techcndev.bookmeme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Level2Activity extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private boolean isPaused = false;
    private int time = 30;
    private TextView textTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2);
        if (isPaused) {
        } else {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
        }
        isPaused = !isPaused;


    }

    private void startPlay() {
        countDownTimer = new CountDownTimer(time * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                time = (int) (millisUntilFinished / 1000);
                textTimer.setText("0:" + checkDigit(time) + " sec");
            }

            public void onFinish() {
                textTimer.setText("try again");
            }
        }.start();
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}