/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.Profile;
import domain.security.User;

/**
 *
 * @author SasaV
 */
public interface UserService {
    
    public void userAdd(User user);
    
    public void userAdd(Profile person);
    
    public User getUserById(String userName);
            
    
}
