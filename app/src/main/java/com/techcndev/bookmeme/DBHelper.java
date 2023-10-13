package com.techcndev.bookmeme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "UserNotesDB.db", null, 1);
    }

    String LOG_TAG = SelectionActivity.class.getSimpleName();

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table GameDB(username TEXT primary key, stage_level TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists GameDB");
    }

    public Boolean insertuserdata(String username, String stage_level)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("stage_level", stage_level);
        long result=DB.insert("GameDB", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean updateuserdata(String username, String stage_level) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("stage_level", stage_level);
        Cursor cursor = DB.rawQuery("Select * from GameDB where username = ?", new String[]{username});
        //if has match, update data
        if (cursor.getCount() > 0) {
            long result = DB.update("UserNotes", contentValues, "title=?", new String[]{username});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean deletedata (String username) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from GameDB where username = ?", new String[]{username});
        if (cursor.getCount() > 0) {
            long result = DB.delete("GameDB", "username=?", new String[]{username});
            if (result == -1) {
                Log.d(LOG_TAG,"Delete Error...");
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getdata () {
            SQLiteDatabase DB = this.getWritableDatabase();
            Cursor cursor = DB.rawQuery("Select * from GameDB", null);
            return cursor;
    }

    //checks if the data is existing using given string
    public Boolean checkdata(String id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from GameDB where username = ?", new String[]{id});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}