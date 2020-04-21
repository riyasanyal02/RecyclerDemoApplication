package com.example.altimetrikproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class InformationDBManager {

    private InformationHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public InformationDBManager(Context c) {
        context = c;
    }

    public InformationDBManager open() throws SQLException {
        dbHelper = new InformationHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String title,String blurb, String pleadge, String country, String location, String by) {
        ContentValues contentValue = new ContentValues();

        contentValue.put(InformationHelper.TITLE, title);
        contentValue.put(InformationHelper.BLURP, blurb);
        contentValue.put(InformationHelper.PLEADE, pleadge);
        contentValue.put(InformationHelper.COUNTRY, country);
        contentValue.put(InformationHelper.LOCATION, location);
        contentValue.put(InformationHelper.BY, by);

        database.insert(InformationHelper.TABLE_NAME, null, contentValue);
    }

    public void deleteAllRecords() {
        database.delete(InformationHelper.TABLE_NAME, null , null);
    }

}
