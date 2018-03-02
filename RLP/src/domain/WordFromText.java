/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Vadya
 */
@Entity
@Table(name="WordsFromText")
public class WordFromText implements Comparable<WordFromText> {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private long Id;
    private String word;
    //counter of words in text - amount
    private long amt;
 
    @ManyToMany(fetch = FetchType.EAGER)
    private List<WordDic> worddicList = new LinkedList<WordDic>();
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "fileId") 
    private FileStore fileText;
    
    
    public WordFromText() {
    }

    public WordFromText(String word, long amt) {
        this.word = word;
        this.amt = amt;
    }

    public WordFromText(String word, long amt, FileStore fileText) {
        this.word = word;
        this.amt = amt;
        this.fileText = fileText;
    }

    public WordFromText(long Id, String word, long amt, FileStore fileText) {
        this.Id = Id;
        this.word = word;
        this.amt = amt;
        this.fileText = fileText;
    }

    public WordFromText(long Id, String word, long amt) {
        this.Id = Id;
        this.word = word;
        this.amt = amt;
    }
    
    
    

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public long getAmt() {
        return amt;
    }

    public void setAmt(long amt) {
        this.amt = amt;
    }

    public List<WordDic> getWorddicList() {
        return worddicList;
    }

    public void setWorddicList(List<WordDic> worddicList) {
        this.worddicList = worddicList;
    }

    public FileStore getFileText() {
        return fileText;
    }

    public void setFileText(FileStore fileText) {
        this.fileText = fileText;
    }

    
    
    @Override
    public int compareTo(WordFromText o) {
        long compareamt = ((WordFromText) o).getAmt();
        if (this.amt > compareamt) {
            return -1;
        }
        if (this.amt < compareamt) {
            return 1;
        } else {
            return 0;
        }


    }
}
