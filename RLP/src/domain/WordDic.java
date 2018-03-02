/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Vadya
 */
@Entity
@Table(name = "Dictionary")
public class WordDic implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private long id;
    private String wordOrig;
    private String langW;
    private String translate;
    private String langT;

    public WordDic() {
    }

    public WordDic(String wordOrig, String langW, String translate, String langT) {
        this.wordOrig = wordOrig;
        this.langW = langW;
        this.translate = translate;
        this.langT = langT;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWordOrig() {
        return wordOrig;
    }

    public void setWordOrig(String wordOrig) {
        this.wordOrig = wordOrig;
    }

    public String getLangW() {
        return langW;
    }

    public void setLangW(String langW) {
        this.langW = langW;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getLangT() {
        return langT;
    }

    public void setLangT(String langT) {
        this.langT = langT;
    }
}
