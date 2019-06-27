package com.mailapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsInfo implements Parcelable {
    private String title;
    private String link;
    private String author;
    private String image;
    private String pubDate;
    private String type;
    private String description;

    public NewsInfo() {

    }

    protected NewsInfo(Parcel in) {
        title = in.readString();
        link = in.readString();
        author = in.readString();
        image = in.readString();
        pubDate = in.readString();
        type = in.readString();
        description = in.readString();
    }

    public static final Creator<NewsInfo> CREATOR = new Creator<NewsInfo>() {
        @Override
        public NewsInfo createFromParcel(Parcel in) {
            return new NewsInfo(in);
        }

        @Override
        public NewsInfo[] newArray(int size) {
            return new NewsInfo[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(author);
        dest.writeString(image);
        dest.writeString(pubDate);
        dest.writeString(type);
        dest.writeString(description);
    }
}
