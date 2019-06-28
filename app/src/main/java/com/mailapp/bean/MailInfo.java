package com.mailapp.bean;


import android.os.Parcel;
import android.os.Parcelable;

public class MailInfo implements Parcelable {
    private int mailId;
    private String mailSendUserName;
    private String mailGetUserName;
    private String mailTitle;
    private String mailContext;
    private String mailImgUrl;
    private String mailSendTime;
    private int isDrafts;

    public MailInfo() {
    }

    public MailInfo(String mailSendUserName, String mailGetUserName, String mailTitle,
                    String mailContext, String mailImgUrl, String mailSendTime, int isDrafts) {
        this.mailSendUserName = mailSendUserName;
        this.mailGetUserName = mailGetUserName;
        this.mailTitle = mailTitle;
        this.mailContext = mailContext;
        this.mailImgUrl = mailImgUrl;
        this.mailSendTime = mailSendTime;
        this.isDrafts = isDrafts;
    }

    private MailInfo(Parcel in) {
        mailSendUserName = in.readString();
        mailGetUserName = in.readString();
        mailSendTime = in.readString();
        mailTitle = in.readString();
        mailContext = in.readString();
        mailImgUrl = in.readString();
    }

    public int getMailId() {
        return mailId;
    }

    public static final Creator<MailInfo> CREATOR = new Creator<MailInfo>() {
        @Override
        public MailInfo createFromParcel(Parcel in) {
            return new MailInfo(in);
        }

        @Override
        public MailInfo[] newArray(int size) {
            return new MailInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mailSendUserName);
        dest.writeString(mailGetUserName);
        dest.writeString(mailSendTime);
        dest.writeString(mailTitle);
        dest.writeString(mailContext);
        dest.writeString(mailImgUrl);
    }

    public void setMailId(int mailId) {
        this.mailId = mailId;
    }

    public String getMailSendUserName() {
        return mailSendUserName;
    }

    public void setMailSendUserName(String mailSendUserName) {
        this.mailSendUserName = mailSendUserName;
    }

    public String getMailGetUserName() {
        return mailGetUserName;
    }

    public void setMailGetUserName(String mailGetUserName) {
        this.mailGetUserName = mailGetUserName;
    }

    public String getMailTitle() {
        return mailTitle;
    }

    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }

    public String getMailContext() {
        return mailContext;
    }

    public void setMailContext(String mailContext) {
        this.mailContext = mailContext;
    }

    public String getMailImgUrl() {
        return mailImgUrl;
    }

    public void setMailImgUrl(String mailImgUrl) {
        this.mailImgUrl = mailImgUrl;
    }

    public String getMailSendTime() {
        return mailSendTime;
    }

    public void setMailSendTime(String mailSendTime) {
        this.mailSendTime = mailSendTime;
    }

    public int getIsDrafts() {
        return isDrafts;
    }

    public void setIsDrafts(int isDrafts) {
        this.isDrafts = isDrafts;
    }
}
