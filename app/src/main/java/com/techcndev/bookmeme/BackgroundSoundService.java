package com.techcndev.bookmeme;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class BackgroundSoundService extends Service {
    private static final String TAG = "BackgroundSoundService"; // Corrected log tag
    MediaPlayer player;
    public String soundState;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Sound onCreate >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        sharedPreferences = getSharedPreferences("PREFS_DATA", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String musicName = sharedPreferences.getString("key_musicname", null);
        soundState = sharedPreferences.getString("key_soundstate1", null);

        if (musicName == null) {
            editor.putString("key_musicname", "arcade_background_music");
            editor.commit();
        }

        musicName = sharedPreferences.getString("key_musicname", null);
        int resourceId = getResources().getIdentifier(musicName, "raw", getPackageName());

        player = MediaPlayer.create(this, resourceId);
        player.setLooping(true);
        player.setVolume(1.0f, 1.0f);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Sound onStartCommand Outside >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        if (soundState == null || (soundState.equals("on")) && (!player.isPlaying()))  {
            editor.putString("key_soundstate1", "on");
            soundState = "on";
            editor.commit();
            player.start();
            Log.d(TAG, "Sound onStartCommand Inside >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Sound onDestroy>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        player.stop();
        player.release();
    }
}