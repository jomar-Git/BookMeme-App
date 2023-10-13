package com.techcndev.bookmeme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
    }

    public void launchSettingsActivityFromAbout(View view) {
        Intent intent = new Intent(AboutUsActivity.this,SettingsActivity.class);
        startActivity(intent);
    }
}