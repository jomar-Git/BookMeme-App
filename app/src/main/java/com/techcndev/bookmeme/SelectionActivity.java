package com.techcndev.bookmeme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SelectionActivity extends AppCompatActivity {

    private Button BackButton, PlayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        BackButton = findViewById(R.id.back_button);
        PlayButton = findViewById(R.id.play_button);

    }

    public void launchHomeActivityFromSelection(View view) {
        YoYo.with(Techniques.BounceIn)
                .duration(1000)
                .playOn(BackButton);
        Intent intent = new Intent(SelectionActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void launchLevel1Activity(View view) {
        YoYo.with(Techniques.BounceIn)
                .duration(1000)
                .playOn(PlayButton);
        Intent intent = new Intent(SelectionActivity.this,Level1Activity.class);
        startActivity(intent);
    }
}