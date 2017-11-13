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
    private String mComplaintId;
    private String mImage;

    public Complaint(String title, String content, String status, String roll, String date, String complaintId,String image){
        mTitle = title;
        mContent = content;
        mStatus = status;
        mRoll = roll;
        mDate = date;
        mComplaintId = complaintId;
        mImage = image;
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
