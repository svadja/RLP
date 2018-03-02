/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service.Auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 *
 * @author Vadya
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        return a;
    }

    @Override
    public boolean supports(Class<?> type) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(type);
    }

    public Authentication trust(UserDetails user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Authentication trustedAuthentication = new CustomUserAuthentication(user, authentication.getDetails());
        authentication = authenticate(trustedAuthentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
