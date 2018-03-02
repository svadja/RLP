/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.Dao;
import domain.FileStore;
import domain.PersonalWord;
import domain.WordDic;
import domain.WordFromText;
import domain.security.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Vadya
 */
public class WordAndTextServiceImpl implements WordAndTextService {

    private static Logger log = Logger.getLogger(WordAndTextServiceImpl.class.getName());
    @Autowired
    private Dao daoI;
    @Autowired
    private FileUtilImpl fileUtilImpl;
    static final String notWord = "\",.-;:!|{}?)(\\/";
    //query of load all word from text
    static final String queryWFTS = "from WordFromText where fileId=?";
    //query of load from word from dic 
    static final String queryWFDS = "from WordDic where wordOrig=?";
    //query for load personalword
    static final String queryPW = "from PersonalWord where wordP=? and owner =?";

    @Override
    public Map<String, WordFromText> countWordsFromSavedText(long id) {

        //Map<String, Long> resMap = new HashMap<String, Long>();
        Map<String, WordFromText> resMap = new HashMap<String, WordFromText>();
        HashSet<String> alldic = loadAllDicWord();
        String line;
        BufferedReader in = null;
        WordFromText wd_temp;
        FileStore fs = (FileStore) daoI.getById(FileStore.class.getName(), id);

        try {
            in = new BufferedReader(new FileReader(fileUtilImpl.getFileFromStore(id)));
            StringTokenizer stLine;
            String word;
            boolean fgo;
            try {
                while ((line = in.readLine()) != null) {
                    stLine = new StringTokenizer(line);
                    while (stLine.hasMoreElements()) {
                        word = (String) stLine.nextElement();
                        fgo = true;
                        while ((!(word.isEmpty())) && fgo) {
                            if (notWord.contains(word.substring(word.length() - 1))) {
                                word = word.substring(0, word.length() - 1);

                            } else {
                                fgo = false;
                            }
                        }
                        fgo = true;
                        while ((!(word.isEmpty())) && fgo) {
                            if (notWord.contains(word.substring(0, 1))) {
                                word = word.substring(1, word.length());
                            } else {
                                fgo = false;
                            }
                        }


                        word = verifyWord(alldic, word);
                        if (!(word.isEmpty())) {
                            //count

                            if (resMap.get(word) != null) {
                                resMap.get(word).setAmt(resMap.get(word).getAmt() + 1);

                            } else {
                                wd_temp = new WordFromText(word, 1, fs);
                                wd_temp.setWorddicList(searchFromDic(word));
                                resMap.put(word, wd_temp);
                            }
                        }
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(WordAndTextServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }


        } catch (FileNotFoundException ex) {
            Logger.getLogger(WordAndTextServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(WordAndTextServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resMap;
    }

    @Override
    public Map<String, WordFromText> countWordsFromText(String text) {

        //Map<String, Long> resMap = new HashMap<String, Long>();
        Map<String, WordFromText> resMap = new HashMap<String, WordFromText>();
        HashSet<String> alldic = loadAllDicWord();
        //BufferedReader in = null;
        WordFromText wd_temp;
        // in = new BufferedReader(new FileReader(file));
        StringTokenizer stLine;
        String word;
        boolean fgo;

        stLine = new StringTokenizer(text);
        while (stLine.hasMoreElements()) {
            word = (String) stLine.nextElement();
            fgo = true;
            while ((!(word.isEmpty())) && fgo) {
                if (notWord.contains(word.substring(word.length() - 1))) {
                    word = word.substring(0, word.length() - 1);

                } else {
                    fgo = false;
                }
            }
            fgo = true;
            while ((!(word.isEmpty())) && fgo) {
                if (notWord.contains(word.substring(0, 1))) {
                    word = word.substring(1, word.length());
                } else {
                    fgo = false;
                }
            }
            word = verifyWord(alldic, word);
            if (!(word.isEmpty())) {
                //count

                if (resMap.get(word) != null) {
                    resMap.get(word).setAmt(resMap.get(word).getAmt() + 1);

                } else {
                    wd_temp = new WordFromText(word, 1);
                    wd_temp.setWorddicList(searchFromDic(word));
                    resMap.put(word, wd_temp);
                }
            }
        }
        return resMap;
    }

    @Override
    public Map<String, Long> coutWords(File file) {

        Map<String, Long> resMap = new HashMap<String, Long>();
        HashSet<String> alldic = loadAllDicWord();
        String line;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            StringTokenizer stLine;
            String word;
            boolean fgo;
            try {
                while ((line = in.readLine()) != null) {
                    stLine = new StringTokenizer(line);
                    while (stLine.hasMoreElements()) {
                        word = (String) stLine.nextElement();
                        fgo = true;
                        while ((!(word.isEmpty())) && fgo) {
                            if (notWord.contains(word.substring(word.length() - 1))) {
                                word = word.substring(0, word.length() - 1);

                            } else {
                                fgo = false;
                            }
                        }
                        fgo = true;
                        while ((!(word.isEmpty())) && fgo) {
                            if (notWord.contains(word.substring(0, 1))) {
                                word = word.substring(1, word.length());
                            } else {
                                fgo = false;
                            }
                        }


                        word = verifyWord(alldic, word);
                        if (!(word.isEmpty())) {
                            //count

                            if (resMap.get(word) != null) {
                                resMap.put(word, resMap.get(word) + 1);

                            } else {

                                resMap.put(word, new Long(1));
                            }
                        }
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(WordAndTextServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }


        } catch (FileNotFoundException ex) {
            Logger.getLogger(WordAndTextServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(WordAndTextServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        WordFromText[] arrWord = new WordFromText[resMap.size()];
        int index = 0;
        for (Map.Entry<String, Long> mapEntry : resMap.entrySet()) {
            arrWord[index] = new WordFromText(mapEntry.getKey(), mapEntry.getValue());
            index++;
        }
        Arrays.sort(arrWord);
        System.err.println("**************************");
        for (WordFromText w : arrWord) {
            System.err.println(w.getWord() + " : " + w.getAmt());
        }
        return resMap;
    }

    @Override
    public void loadDic(File file, String fromL, String toL) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            StringTokenizer stLine;
            String line;
            String word = new String();
            String translate = new String();
            while ((line = in.readLine()) != null) {
                stLine = new StringTokenizer(line, "|");
                if (stLine.hasMoreElements()) {
                    word = (String) stLine.nextElement();
                }
                if (stLine.hasMoreElements()) {
                    translate = (String) stLine.nextElement();
                    // System.err.println(word + " - " + translate);
                    daoI.saveOrUpdate(new WordDic(word, fromL, translate, toL));
                }


            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(WordAndTextServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WordAndTextServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void saveWordsOfText(Map<String, Long> wordMap, FileStore fs) {
        for (Map.Entry<String, Long> mapEntry : wordMap.entrySet()) {
            WordFromText wft = new WordFromText(mapEntry.getKey(), mapEntry.getValue(), fs);
            daoI.saveOrUpdate(wft);
        }
    }

    public HashSet<String> loadAllDicWord() {
        List<String> queryL = new ArrayList<String>();
        String queryS;
        queryS = "SELECT wordorig FROM dictionary";
        queryL = daoI.getByQuery(queryS);
        return new HashSet<String>(queryL);
    }

    @Override
    public String verifyWord(HashSet<String> allWordDic, String wordP) {
        String word = wordP.toLowerCase();
        if (allWordDic.contains(word)) {
            return word;
        } else {
            //-ed
            if ((word.length() > 2) && (word.substring(word.length() - 2).equals("ed"))) {
                //-1
                if ((word.length() > 3) && (allWordDic.contains(word.substring(0, word.length() - 1)))) {
                    return word.substring(0, word.length() - 1);
                }
                //-2
                if ((word.length() > 4) && (allWordDic.contains(word.substring(0, word.length() - 2)))) {
                    return word.substring(0, word.length() - 2);
                }
                //-3
                if ((word.length() > 4) && (allWordDic.contains(word.substring(0, word.length() - 3)))) {
                    return word.substring(0, word.length() - 3);
                }
                //-2 i->y
                if ((word.length() > 4) && (word.substring(word.length() - 3).equals("ied")) && (allWordDic.contains(word.substring(0, word.length() - 3) + "y"))) {
                    return word.substring(0, word.length() - 3) + "y";
                }
            }
            //-ing
            if ((word.length() > 3) && (word.substring(word.length() - 3).equals("ing"))) {
                //-3
                if ((word.length() > 4) && (allWordDic.contains(word.substring(0, word.length() - 3)))) {
                    return word.substring(0, word.length() - 3);
                }

                //-3 +e
                if ((word.length() > 4) && (allWordDic.contains(word.substring(0, word.length() - 3) + "e"))) {
                    return word.substring(0, word.length() - 3) + "e";
                }
                //-4
                if ((word.length() > 5) && (allWordDic.contains(word.substring(0, word.length() - 4)))) {
                    return word.substring(0, word.length() - 4);
                }

                //-3 y->ie
                if ((word.length() > 4) && (word.substring(word.length() - 4).equals("ying")) && (allWordDic.contains(word.substring(0, word.length() - 4) + "ie"))) {
                    return word.substring(0, word.length() - 4) + "ie";
                }
            }
            //-es
            if ((word.length() > 2) && (word.substring(word.length() - 1).equals("s"))) {
                //-1
                if ((word.length() > 2) && (allWordDic.contains(word.substring(0, word.length() - 1)))) {
                    return word.substring(0, word.length() - 1);
                }
                //-2
                if ((word.length() > 3) && (word.substring(word.length() - 2).equals("es")) && (allWordDic.contains(word.substring(0, word.length() - 2)))) {
                    return word.substring(0, word.length() - 2);
                }

                //-2 i->y
                if ((word.length() > 4) && (word.substring(word.length() - 3).equals("ies")) && (allWordDic.contains(word.substring(0, word.length() - 3) + "y"))) {
                    return word.substring(0, word.length() - 3) + "y";
                }
                //-2 v->f
                if ((word.length() > 4) && (word.substring(word.length() - 3).equals("ves")) && (allWordDic.contains(word.substring(0, word.length() - 3) + "f"))) {
                    return word.substring(0, word.length() - 3) + "f";
                }

                //-2 v->fe
                if ((word.length() > 4) && (word.substring(word.length() - 3).equals("ves")) && (allWordDic.contains(word.substring(0, word.length() - 3) + "fe"))) {
                    return word.substring(0, word.length() - 3) + "fe";
                }
            }
        }
        return "";

    }

    @Override
    public WordFromText[] analyzeFromStore(long id) {
        Map<String, WordFromText> resMap = new HashMap<String, WordFromText>();
        FileStore fs = (FileStore) daoI.getById(FileStore.class.getName(), id);
        if (fs.isAnalyzed()) {
            return getListFromSavedFile(id);
        } else {
            resMap = countWordsFromSavedText(id);
            fs.setAnalyzed(true);
            daoI.savaOrUpdateAll(resMap.values());
            daoI.saveOrUpdate(fs);

            //Запрос не нада робить
            return resMap.values().toArray(new WordFromText[resMap.values().size()]);
        }
    }

    @Override
    public WordFromText[] analyzeFromText(String text) {
        Map<String, WordFromText> resMap = new HashMap<String, WordFromText>();
        Long nullId = null;
        resMap = countWordsFromText(text);
        return resMap.values().toArray(new WordFromText[resMap.values().size()]);
    }

    @Override
    public WordFromText[] getListFromSavedFile(long id) {
        Long[] values = {Long.valueOf(id)};
        List<WordFromText> rezList = (List<WordFromText>) daoI.getByQuery(queryWFTS, values);
        WordFromText[] arrWord = rezList.toArray(new WordFromText[rezList.size()]);
        return arrWord;
    }

    @Override
    public boolean savePersonalWord(String id_worddic, String name, byte status) {
        WordDic worddic = (WordDic) daoI.getById(WordDic.class.getName(), Long.valueOf(id_worddic).longValue());
        User user = (User) daoI.getById(User.class.getName(), name);
        Object[] values = {worddic, user};
        List<PersonalWord> rezQue = (List<PersonalWord>) daoI.getByQuery(queryPW, values);
        PersonalWord pword;
        if (status == 0) {
            if (rezQue.size() > 0) {
                pword = rezQue.get(0);
                daoI.delete(pword);
            }
        } else {
            if (rezQue.size() > 0) {
                pword = rezQue.get(0);
                pword.setStatus(status);
            } else {
                pword = new PersonalWord(worddic, user, status);
            }
            daoI.saveOrUpdate(pword);
        }
        return true;
    }

    @Override
    public List<WordDic> searchFromDic(String searchWord) {
        String[] values = {searchWord};
        return (List<WordDic>) daoI.getByQuery(queryWFDS, values);
    }

    @Override
    public ArrayList<PersonalWord> loadAllMyWords(String name) {
        String[] values = {name};
        return (ArrayList<PersonalWord>) daoI.getByQuery("from PersonalWord where userId=?", values);
    }
}
