package com.cucund.project.admin.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JSONUtil {

    public static String toJSONString(Object data) {
        ObjectMapper o = new ObjectMapper();
        try {
            return o.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new JSONException(e.getMessage());
        }
    }

    public static <T> T get(String json , Class<T> clazz){
        ObjectMapper o = new ObjectMapper();
        try {
            return o.readValue(json,clazz);
        } catch (JsonProcessingException e) {
            throw new JSONException(e.getMessage());
        } catch (IOException e) {
            throw new JSONException(e.getMessage());
        }
    }

}
class JSONException extends RuntimeException {

    public JSONException(String message) {
        super(message);
    }
}
