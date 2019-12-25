package com.tmri.rfid.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joey on 2015/9/24.
 */
public class GsonHelper {

    private final static Gson gson = new Gson();

    public static Gson getGson() {
        return gson;
    }
    
    public static Gson getGson2DateFormat(String dateFomat) {
		Gson gson = new GsonBuilder().setDateFormat(dateFomat).create();
        return gson;
    }
    
    public static String getBaseResultGson(String resultId, String resultMsg) {
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("resultId", resultId);
        resultMap.put("resultMsg", resultMsg);
        return gson.toJson(resultMap);

    }

}
