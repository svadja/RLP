/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.security.User;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author SasaV
 */
@Entity
@Table(name = "PersonalWord")
public class PersonalWord {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private long id;
//    if status 0 deleted word
//    1 - studying
//    2 - known
    private byte status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User owner;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "worddicId")
    private WordDic wordP;

    public PersonalWord() {
    }

    public PersonalWord( WordDic wordP , User owner ,byte status) {
        this.status = status;
        this.owner = owner;
        this.wordP = wordP;
    }

    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public WordDic getWordP() {
        return wordP;
    }

    public void setWordP(WordDic wordP) {
        this.wordP = wordP;
    }
    

}
