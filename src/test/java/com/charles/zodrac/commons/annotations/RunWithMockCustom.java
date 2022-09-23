package com.charles.zodrac.commons.annotations;

import com.charles.zodrac.model.dto.UserDetailsDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

public interface RunWithMockCustom {

    default SecurityContext renderSecurityContext(String characterId, String userName, String password, String[] authorities) {
        UserDetailsDTO user = createUser(characterId, userName, password, authorities);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        user.getPassword(),
                        user.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }

    default UserDetailsDTO createUser(String characterId, String userName, String password, String[] authorities) {
        List<SimpleGrantedAuthority> roles = Arrays.stream(authorities)
                .map(SimpleGrantedAuthority::new).toList();
        UserDetailsDTO user = new UserDetailsDTO(roles, password, userName);
        user.setCharacterId(Long.parseLong(characterId));
        return user;
    }
}