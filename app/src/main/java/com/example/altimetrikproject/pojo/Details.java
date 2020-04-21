package com.example.altimetrikproject.pojo;

import java.util.ArrayList;
import java.util.List;

public class Details {

    private String mTitle;

    private String mBlurp;

    private String mPleadge;

    private String mBacker;

    private String mBy;

    private String mCountry;

    private String mLocation;

    public Details(String mTitle, String mBlurp, String mPleadge, String mCountry, String mLocation, String mBy){
        this.mTitle = mTitle;
        this.mBlurp = mBlurp;
        this.mPleadge = mPleadge;
        this.mCountry = mCountry;
        this.mLocation = mLocation;
        this.mBy = mBy;
    }

    public Details(){

    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmBlurp() {
        return mBlurp;
    }

    public void setmBlurp(String mBlurp) {
        this.mBlurp = mBlurp;
    }

    public String getmPleadge() {
        return mPleadge;
    }

    public void setmPleadge(String mPleadge) {
        this.mPleadge = mPleadge;
    }

    public String getmBacker() {
        return mBacker;
    }

    public void setmBacker(String mBacker) {
        this.mBacker = mBacker;
    }

    public String getmBy() {
        return mBy;
    }

    public void setmBy(String mBy) {
        this.mBy = mBy;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    private static int lastDetailsFetched = 0;

   /* public static List<Details> createContactsList(int numContacts, int offset) {
        List<Details> contacts = new ArrayList<Details>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Details("Person " + ++lastDetailsFetched + " offset: " + offset, i <= numContacts / 2));
        }

        return contacts;
    }*/
}
