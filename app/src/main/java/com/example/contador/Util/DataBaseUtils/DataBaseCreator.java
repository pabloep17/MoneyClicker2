package com.example.contador.Util.DataBaseUtils;

import android.content.Context;
import android.database.sqlite.*;
import android.util.Log;

public class DataBaseCreator extends SQLiteOpenHelper {
    private static final String USERS_TABLE_CREATE = "CREATE TABLE users(_id INTEGER PRIMARY KEY AUTOINCREMENT,  nick TEXT UNIQUE, password TEXT, coins TEXT, manualClickValue TEXT, autoClickValue TEXT, autoClickDelay TEXT, lastPlay TEXT)";
    private static final String GAMES_TABLE_CREATE = "CREATE TABLE games(_id INTEGER PRIMARY KEY AUTOINCREMENT, uid INTEGER, startCoins TEXT, finishCoins TEXT, startTime TEXT, finishTime TEXT)";
    private static final String DB_NAME = "moneyclicker.sqlite";
    private static final int DB_VERSION = 1;
    public DataBaseCreator(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERS_TABLE_CREATE);
        db.execSQL(GAMES_TABLE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
