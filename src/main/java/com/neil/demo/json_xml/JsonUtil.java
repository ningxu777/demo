package com.neil.demo.json_xml;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neil on 2018/4/19.
 */
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String,Object> toMap(String json){
        if(json != null){
            try {
                Map<String,Object> map = objectMapper.readValue(json,HashMap.class);
                return map;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new HashMap<String,Object>();
    }

    public static String toJson(Object o){
        try {
            String json = objectMapper.writeValueAsString(o);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
