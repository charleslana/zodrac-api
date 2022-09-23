package com.charles.zodrac.commons.annotations.user;

import com.charles.zodrac.commons.annotations.RunWithMockCustom;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class RunWithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<RunWithMockCustomUser>, RunWithMockCustom {

    @Override
    public SecurityContext createSecurityContext(RunWithMockCustomUser customUser) {
        return renderSecurityContext(customUser.characterId(), customUser.userName, customUser.password, customUser.authorities());
    }
}
