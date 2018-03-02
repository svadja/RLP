/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;


import domain.security.User;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;



/**
 *
 * @author SasaV
 */
@Entity
@Table(name="Profiles")
public class Profile implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int Id;
    
    private String firstName;
    
    private String lastName;
    
    private String email;
    
    @OneToOne(mappedBy = "profile")
    @Cascade(CascadeType.ALL)
    private User user;
    
    //for game
    
    private int cntTrueAnswer = 3;
    private int cntWordTest = 20;
    private int cntWordTestStudy = 15;
    
    public Profile(String firstName, String lastName, String email, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.user = user;
    }

    public Profile() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCntTrueAnswer() {
        return cntTrueAnswer;
    }

    public void setCntTrueAnswer(int cntTrueAnswer) {
        this.cntTrueAnswer = cntTrueAnswer;
    }

    public int getCntWordTest() {
        return cntWordTest;
    }

    public void setCntWordTest(int cntWordTest) {
        this.cntWordTest = cntWordTest;
    }

    public int getCntWordTestStudy() {
        return cntWordTestStudy;
    }

    public void setCntWordTestStudy(int cntWordTestStudy) {
        this.cntWordTestStudy = cntWordTestStudy;
    }
    
    

}
