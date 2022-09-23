package com.charles.zodrac.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class SerializationUtils {

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        return createObjectMapper().writeValueAsBytes(object);
    }

    public static String convertObjectToJsonString(Object object) throws IOException {
        return createObjectMapper().writeValueAsString(object);
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    private static <T> T readMapper(String data, Class<T> clazz) throws IOException {
        return createObjectMapper().readValue(data, clazz);
    }
}