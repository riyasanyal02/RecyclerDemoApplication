package com.example.altimetrikproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InformationHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "InformationArray";

    // Table columns
    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String BLURP = "blurp";
    public static final String PLEADE = "pleadge";
    public static final String COUNTRY = "country";
    public static final String LOCATION = "location";
    public static final String BY = "by";

    //Database Name
    static final String DB_NAME = "information_db";

    // database version
    static final int DB_VERSION = 1;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + TITLE + " TEXT,"
                    + BLURP + " TEXT,"
                    + PLEADE + " TEXT,"
                    + COUNTRY + " TEXT,"
                    + LOCATION + " TEXT,"
                    + BY + " TEXT"
                    + ")";

    public InformationHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
