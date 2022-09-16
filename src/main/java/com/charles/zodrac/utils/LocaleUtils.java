package com.charles.zodrac.utils;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class LocaleUtils {

    private LocaleUtils() {
    }

    public static Locale currentLocale() {
        return LocaleContextHolder.getLocale();
    }
}
