package com.mailapp.utils;

import android.util.Xml;


import com.mailapp.bean.NewsInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class NewsXmlParser {


    public static List<NewsInfo> parse(InputStream in) {
        List<NewsInfo> newsList = new ArrayList<>();
        NewsInfo newsInfo = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(in, "UTF-8");
            int type = parser.getEventType();
            while (type != XmlPullParser.END_DOCUMENT) {
                switch (type) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("item")) {
                            newsInfo = new NewsInfo();
                        } else if (parser.getName().equals("title")) {
                            newsInfo.setTitle(parser.nextText());
                        } else if (parser.getName().equals("link")) {
                            newsInfo.setLink(parser.nextText());
                        } else if (parser.getName().equals("author")) {
                            newsInfo.setAuthor(parser.nextText());
                        } else if (parser.getName().equals("image")) {
                            newsInfo.setImage(parser.nextText());
                        } else if (parser.getName().equals("pubDate")) {
                            newsInfo.setPubDate(parser.nextText());
                        } else if (parser.getName().equals("type")) {
                            newsInfo.setType(parser.nextText());
                        } else if (parser.getName().equals("description")) {
                            newsInfo.setDescription(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item"))
                            newsList.add(newsInfo);
                        break;
                }
                type = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newsList;
    }
}
