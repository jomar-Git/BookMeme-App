package com.techcndev.bookmeme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {
    private TextView TapTextview, TitleTextview;
    private ImageButton SettingsButton, LikeButton;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public MediaPlayer BGMusic, TitleSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG,"ONCREATE");
        TapTextview = findViewById(R.id.tap_textview);
        TitleTextview = findViewById(R.id.game_title);
        SettingsButton = findViewById(R.id.settings_button);
        LikeButton = findViewById(R.id.like_button);

        Intent svc=new Intent(MainActivity.this, BackgroundSoundService.class);
        startService(svc);
    }

    public void open_fblike(View view) {
        YoYo.with(Techniques.BounceIn)
                .duration(1000)
                .playOn(LikeButton);
        String url = "https://www.facebook.com/profile.php?id=61552150174245";
        Uri webpage = Uri.parse(url);
        Intent intent  = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(LOG_TAG, "Can't handle this!");
        }
    }

    // Contains home animations
    public void home_animation() {
        TitleSound = MediaPlayer.create(MainActivity.this, R.raw.bgmusic1);
//            BGMusic.start();
        // Tap to Start animation
        YoYo.with(Techniques.Flash)
                .duration(5000)
                .repeat(YoYo.INFINITE)
                .playOn(TapTextview);

        // Title animation
        final Handler handler = new Handler();
        final Runnable animationRunnable = new Runnable() {
            @Override
            public void run() {
                // Start the shake animation
                YoYo.with(Techniques.RubberBand)
                        .duration(1000)
                        .playOn(TitleTextview);
                YoYo.with(Techniques.Shake)
                        .duration(500)
                        .playOn(TitleTextview);

                // Delay before repeating
                handler.postDelayed(this, 2000);
            }
        };
        handler.post(animationRunnable);
    }

    public void launchSettingsActivity(View view) {
        YoYo.with(Techniques.RotateIn)
                .duration(500)
                .playOn(SettingsButton);
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    // Tap to start onClick function
    public void launchPlayerSelectionActivity(View view) {
        YoYo.with(Techniques.FadeOut)
                .duration(700)
                .repeat(1)
                .playOn(TapTextview);
        Intent intent = new Intent(MainActivity.this, SelectionActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG,"ONSTART");
        home_animation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG,"ONSTOP");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG,"ONDESTROY");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG,"ONPAUSE");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG,"ONRESUME");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG,"ONRESTART");
    }
}