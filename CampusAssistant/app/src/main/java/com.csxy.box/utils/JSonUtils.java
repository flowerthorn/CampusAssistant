package com.csxy.box.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

public class JSonUtils {

    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }


    public static <T> T fromJson(String jsonString, Class<T> tClass) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        Gson gson = gsonBuilder.create();
        return gson.fromJson(jsonString, tClass);
    }
}
