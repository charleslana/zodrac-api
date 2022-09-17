package com.charles.zodrac.security;

import com.charles.zodrac.model.dto.UserDetailsDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    private SecurityUtils() {
    }

    public static Boolean existsCharacterId() {
        return getUserDetails().getCharacterId() != null;
    }

    public static Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getAuthEmail() {
        return getAuth().getName();
    }

    public static UserDetailsDTO getUserDetails() {
        return (UserDetailsDTO) getAuth().getPrincipal();
    }

    public static void removeCharacterId() {
        setCharacterId(null);
    }

    public static void setCharacterId(Long id) {
        getUserDetails().setCharacterId(id);
    }
}