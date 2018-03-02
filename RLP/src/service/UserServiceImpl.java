/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.Dao;
import domain.Profile;
import domain.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author SasaV
 * 
 * 
 */


public class UserServiceImpl implements UserService, UserDetailsService{
    
    @Autowired
    private Dao daoI;
    
    @Override
    public void userAdd(User user) {
       daoI.saveOrUpdate(user);
    }

    @Override
    public void userAdd(Profile person) {
        daoI.saveOrUpdate(person);
    }

    @Override
    public User getUserById(String userName) {
       return (User)daoI.getById(User.class.getName(), userName);
    }

    @Override
    /**
     * load my user details
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
             
        final User user = getUserById(username);    
        if(user == null) {
            throw new UsernameNotFoundException("No user found for username '" + username +"'.");
        }
        return user;
    }
   
}
