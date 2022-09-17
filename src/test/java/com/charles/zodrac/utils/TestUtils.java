package com.charles.zodrac.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class TestUtils {

    private TestUtils() {
    }

    public static String generateRandomEmail() {
        return RandomStringUtils.randomAlphabetic(10).concat("@example.com");
    }

    public static String generateRandomString() {
        return RandomStringUtils.randomAlphabetic(10);
    }
}