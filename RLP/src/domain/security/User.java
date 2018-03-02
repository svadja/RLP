/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.security;


import domain.Profile;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.security.core.userdetails.UserDetails;




/**
 *
 * @author SasaV
 */

@Entity
@Table(name = "Users")
public class User implements Serializable, UserDetails{

    @Id
    //@GeneratedValue
   // private int id;

    private String username;
    
    private String password;
    
    private boolean enabled;
        
    @OneToOne(targetEntity = Profile.class, fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private Profile profile;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({CascadeType.SAVE_UPDATE})
    private List<Authority> authorities = new LinkedList<Authority>();
            
    public User(String username, String password, boolean enabled, Profile profile) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.profile = profile;
    }


    public User(){
        this.enabled = true;
    }

    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
    
    public void setAuthority(Authority authority){
        this.authorities.add(authority);
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public boolean isAccountNonExpired() {
      return true;  
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


            
            
}
