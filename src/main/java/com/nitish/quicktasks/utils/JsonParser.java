package com.nitish.quicktasks.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonParser {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String stringify(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parse(String jsonString, Class<T> cls) {
        try {
            return objectMapper.readValue(jsonString, cls);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?> transform(Object object, Class<?> classz) {
        try {
            return (Class<?>) objectMapper.readValue(stringify(object), classz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> parseToList(String str, Class<T> classz) {
        try {
            return objectMapper.readValue(str, objectMapper.getTypeFactory().constructCollectionType(List.class, classz));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> parseToList(File str, Class<T> classz) {
        try {
            return objectMapper.readValue(str, objectMapper.getTypeFactory().constructCollectionType(List.class, classz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS.mappedFeature(),true);
    }
}
