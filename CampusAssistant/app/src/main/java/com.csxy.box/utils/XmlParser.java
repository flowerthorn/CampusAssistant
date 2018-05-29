package com.csxy.box.utils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class XmlParser {

    public static Map<String, String> parseCity(InputStream is) throws IOException {
        Map<String, String> cityMap = new HashMap<String, String>();

        try {
            //1，构建pull解析器工厂
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            //2，从工厂中构建一个解析器
            XmlPullParser parser = factory.newPullParser();
            //3，设置一个输入流
            parser.setInput(is, "UTF-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_TAG){
                    String tag = parser.getName();
                    if (tag.equals("key")){
                        String key = parser.nextText();
                        parser.next();
                        parser.next();
                        String value = parser.nextText();
                        cityMap.put(key, value);
                    }
                }
                parser.next();
                eventType = parser.getEventType();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return cityMap;
    }

}