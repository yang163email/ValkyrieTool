package com.yan.valkyrietool.v2.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

/**
 * @author : yan
 * @date : 2018/7/5 12:53
 * @desc : todo
 */
public class GsonUtil {

    private static Gson sGson = new Gson();

    public static <T> ArrayList<T> getObjList(String json, Class<T> clazz) {
        ArrayList<T> list = new ArrayList<T>();
        try {
            JsonArray arry = new JsonParser().parse(json).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(sGson.fromJson(jsonElement, clazz));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
