package com.example.altimetrikproject.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.altimetrikproject.database.InformationHelper;
import com.example.altimetrikproject.interfaces.IinformationActivity;
import com.example.altimetrikproject.interfaces.IinformationModel;
import com.example.altimetrikproject.pojo.Details;

import java.util.ArrayList;
import java.util.List;

public class InformationModel implements IinformationModel {

    private SQLiteDatabase database;

    public InformationModel(IinformationActivity iView) {
        database = new InformationHelper((Context) iView).getReadableDatabase();
    }

    @Override
    public List<Details> getListFromDatabase() {
        List<Details> detailsArrayList = new ArrayList<>();

        Cursor cursor = database.rawQuery("Select * from " + InformationHelper.TABLE_NAME +" Order By title;", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Details detailsArray = new Details();
            String title = cursor.getString(cursor.getColumnIndex(InformationHelper.TITLE));
            detailsArray.setmTitle(title);

            String blurb = cursor.getString(cursor.getColumnIndex(InformationHelper.BLURP));
            detailsArray.setmBlurp(blurb);

            String pleadge = cursor.getString(cursor.getColumnIndex(InformationHelper.PLEADE));
            detailsArray.setmPleadge(pleadge);

            String country = cursor.getString(cursor.getColumnIndex(InformationHelper.COUNTRY));
            detailsArray.setmCountry(country);

            String location = cursor.getString(cursor.getColumnIndex(InformationHelper.LOCATION));
            detailsArray.setmLocation(location);

            String by = cursor.getString(cursor.getColumnIndex(InformationHelper.BY));
            detailsArray.setmBy(by);

            detailsArrayList.add(detailsArray);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return detailsArrayList;

    }
}
