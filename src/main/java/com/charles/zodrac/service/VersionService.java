package com.charles.zodrac.service;

import org.springframework.stereotype.Service;

@Service
public class VersionService {

    private VersionService() {
    }

    public static final String VERSION = "1.0.0";
}
