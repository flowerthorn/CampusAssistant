package com.lib.mylibrary.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lihongxin on 2018/5/21.
 */
public class MapUtils {
    public static Map<String, String> getOperationParams(String... args) {
        Map<String, String> map = new HashMap<>(10);
        List<String> argsList = Arrays.asList(args);
        Iterator<String> iterator = argsList.iterator();

        if (args.length % 2 == 0) {
            while (iterator.hasNext()) {
                map.put(iterator.next(), iterator.next());
            }
        }
        return map;
    }
}
