package com.charles.zodrac.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class TestUtils {

    private TestUtils() {
    }

    public static String generateEmail() {
        return RandomStringUtils.randomAlphabetic(10).concat("@example.com");
    }

    public static String generateString() {
        return RandomStringUtils.randomAlphabetic(10);
    }
}
