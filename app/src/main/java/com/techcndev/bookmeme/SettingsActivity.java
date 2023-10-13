package com.techcndev.bookmeme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

     private final String LOG_TAG = "SettingsActivity";
    Spinner spinner;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Intent svc;
    ImageButton soundbutton;
    String soundstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        spinner = findViewById(R.id.bgmusic);
        soundbutton = findViewById(R.id.soundButton);
        String[] mp3Files = getFilenames().toArray(new String[0]);
        sharedPreferences = getSharedPreferences("PREFS_DATA", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        svc = new Intent(SettingsActivity.this, BackgroundSoundService.class);

        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }

        if (mp3Files != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mp3Files);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            String val = sharedPreferences.getString("key_musicname", null);

            if (val != null) {
                int initialSelectionPosition = adapter.getPosition(val);
                if (spinner != null) {
                    spinner.setAdapter(adapter);
                    spinner.setSelection(initialSelectionPosition);
                    Log.d(LOG_TAG, "GUMANAAAAA >>>>>>>>>>>>>>>>>>>>>>>>");
                } else {
                    Log.d(LOG_TAG, "spinner is null >>>>>>>>>>>>>>>>>>>>>>>>");
                }
            } else {
                Log.d(LOG_TAG, "\"key_musicname\" is not found in SharedPreferences or is null >>>>>>>>>>>>>>>>>>>>>>>>");
            }
        } else {
            Log.d(LOG_TAG, "No .mp3 files found >>>>>>>>>>>>>>>>>>>>>>>>");
        }
        changeSoundIcon();
    }

    private void changeSoundIcon() {
        soundstate = sharedPreferences.getString("key_soundstate1", null);
        if (soundstate.equals("on")) {
            Drawable newBackground = getResources().getDrawable(R.drawable.baseline_volume_up_24);
            soundbutton.setBackground(newBackground);
        } else {
            Drawable newBackground = getResources().getDrawable(R.drawable.baseline_volume_off_24);
            soundbutton.setBackground(newBackground);
        }
    }

    public void modifySoundState(View view) {
            soundstate = sharedPreferences.getString("key_soundstate1", null);
        if (soundstate.equals("on")) {
            editor.putString("key_soundstate1", "off");
            editor.commit();
            changeSoundIcon();
            Log.d(LOG_TAG, "modifySoundState OFF >>>>>>>>>>>>>>>>>>>>>>>>");
            stopService(svc);
        } else {
            editor.putString("key_soundstate1", "on");
            editor.commit();
            Log.d(LOG_TAG, "modifySoundState ON >>>>>>>>>>>>>>>>>>>>>>>>");
            changeSoundIcon();
            stopService(svc);
            startService(svc);
        }
        Log.d(LOG_TAG, "modifySoundState >>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String val = sharedPreferences.getString("key_musicname", null);
        String selectedItem = parent.getItemAtPosition(position).toString();

        Log.d(LOG_TAG, "val: " + val + ">>>>>>>>>>>>>>>>>>>>>>>");
        Log.d(LOG_TAG, "SelectedItem: " + selectedItem + ">>>>>>>>>>>>>>>>>>>>>>>");

        val = sharedPreferences.getString("key_musicname", null);
        if (val == null || !val.equals(selectedItem)) {
            try {
                editor.putString("key_musicname", selectedItem);
                editor.commit();
                stopService(svc);
                startService(svc);

                Snackbar.make(view, selectedItem + " set as background music...", Snackbar.LENGTH_SHORT)
                        .setAction("Ok", null).show();
                Log.d(LOG_TAG, "Preferred bgmusic changed to:" + selectedItem + ">>>>>>>>>>>>>>>>>>>>>>>>>>>");
            } catch (Exception e) {
                Log.d(LOG_TAG, "Preferred bgmusic not changed >>>>>>>>>>>>>>>>>>>>>>>>>>>\n Error: " + e);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing here
    }

    public ArrayList<String> getFilenames() {
        ArrayList<String> list = new ArrayList<String>();
        Field[] fields = R.raw.class.getFields();
        for(Field f : fields)
            try {
                list.add(f.getName());
            } catch (IllegalArgumentException e) {
            }
        return list;
    }


    public void launchHomeActivityFromSettings(View view) {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void launchAboutUs(View view) {
        Intent intent = new Intent(SettingsActivity.this,AboutUsActivity.class);
        startActivity(intent);
    }

}