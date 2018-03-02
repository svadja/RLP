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
import javax.persistence.Table;

/**
 *
 * @author Vadya
 */
//txt file
@Entity
@Table(name = "FileStore")
public class FileStore implements Comparable<FileStore> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    private String file;
    private boolean analyzed;
    private long dateCr;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    public FileStore() {
        analyzed = false;
    }

    public FileStore(String name, String file, long dateCr, User user) {
        this.name = name;
        this.file = file;
        this.analyzed = false;
        this.user = user;
        this.dateCr = dateCr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isAnalyzed() {
        return analyzed;
    }

    public void setAnalyzed(boolean analyzed) {
        this.analyzed = analyzed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getDateCr() {
        return dateCr;
    }

    public void setDateCr(long dateCr) {
        this.dateCr = dateCr;
    }

    @Override
    public int compareTo(FileStore o) {
        long comparedate = o.getDateCr();
        if (this.dateCr > comparedate) {
            return -1;
        }
        if (this.dateCr < comparedate) {
            return 1;
        } else {
            return 0;
        }



    }
}
