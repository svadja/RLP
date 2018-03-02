/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service.Auth;

import domain.Profile;
import domain.security.Authority;
import domain.security.User;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import service.UserService;

/**
 *
 * @author Vadya
 */
public class OpenidUserService implements UserDetailsService,
        AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    private static Logger log = Logger.getLogger(OpenidUserService.class.getName());
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new UsernameNotFoundException(id);
        }
        return user;
    }

    @Override
    public UserDetails loadUserDetails(OpenIDAuthenticationToken t) throws UsernameNotFoundException {
        String id = t.getIdentityUrl();
        String email = null;
        String firstname = null;
        String lastname = null;
        String fullname = null;
        List<OpenIDAttribute> attributes = t.getAttributes();
        for (OpenIDAttribute attribute : attributes) {
            if (attribute.getName().equals("email")) {
                email = attribute.getValues().get(0);
            }

            if (attribute.getName().equals("firstname")) {
                firstname = attribute.getValues().get(0);
            }
            if (attribute.getName().equals("lastname")) {
                lastname = attribute.getValues().get(0);
            }
            if (attribute.getName().equals("fullname")) {
                fullname = attribute.getValues().get(0);
            }
        }

        if (fullname == null) {
            StringBuilder fullNameSrB = new StringBuilder();
            if (firstname != null) {
                fullNameSrB.append(firstname + " ");
            }
            if (lastname != null) {
                fullNameSrB.append(lastname);
            }
            fullname = fullNameSrB.toString();

        }
        User user = userService.getUserById(email);
        if (user != null) {
            return user;
        }
        user = new User();
        user.setUsername(email);
        user.setPassword("P09ASzxNJkkk123---");
        user.setAuthority(new Authority("ROLE_USER"));
        Profile profile = new Profile();
        profile.setFirstName(firstname);
        profile.setEmail(email);
        profile.setLastName(lastname);
        user.setProfile(profile);
        userService.userAdd(user);
        return user;
    }
}
