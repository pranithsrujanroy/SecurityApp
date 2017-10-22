package com.example.android.securityapp;

/**
 * Created by ramana on 10/6/2017.
 */

public class Complaint {
    private String mTitle;
    private String mContent;
    private String mStatus;
    private String mRoll;
    private String mDate;

    public Complaint(String title, String content, String status, String roll, String date){
        mTitle = title;
        mContent = content;
        mStatus = status;
        mRoll = roll;
        mDate = date;
    }

    public String getmContent() {
        return mContent;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmRoll() {
        return mRoll;
    }

    public String getmStatus() {
        return mStatus;
    }

    public String getmTitle() {
        return mTitle;
    }
}
