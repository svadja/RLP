/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.FileStore;
import domain.PersonalWord;
import domain.WordDic;
import domain.WordFromText;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Vadya
 */
public interface WordAndTextService {

    public Map<String, Long> coutWords(File file);

    public Map<String, WordFromText> countWordsFromSavedText(long id);
    
    public Map<String, WordFromText> countWordsFromText(String text);
     
    public WordFromText[] analyzeFromStore(long id);
      
    public WordFromText[] analyzeFromText(String text);

    public WordFromText[] getListFromSavedFile(long id);

    //load dictionary from file to DB
    public void loadDic(File file, String fromL, String toL);

    public void saveWordsOfText(Map<String, Long> wordMap, FileStore fs);

    public HashSet<String> loadAllDicWord();
    
    public List<WordDic> searchFromDic(String searchWord);

    public String verifyWord(HashSet<String> allWordDic, String word);

    public boolean savePersonalWord(String id_worddic, String name, byte status);
    
    public ArrayList<PersonalWord> loadAllMyWords(String name);
    
    
}
