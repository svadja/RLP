/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.Dao;
import domain.Profile;
import domain.WordFromText;
import domain.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.WordAndTextServiceImpl;
import java.util.Arrays;
import service.FileUtilImpl;
import service.UserService;

/**
 *
 * @author SasaV
 */
@Controller
public class WordController {

    @Autowired
    private WordAndTextServiceImpl wordAndTextServiceImpl;
    @Autowired
    private UserService userService;
    @Autowired
    private Dao daoI;
    @Autowired
    private FileUtilImpl fileUtilImpl;

    @RequestMapping(value = "/w/{file_name}", method = RequestMethod.GET)
    public String getListWords(@PathVariable("file_name") String fileId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        WordFromText[] words = wordAndTextServiceImpl.analyzeFromStore(Long.parseLong(fileId));
        Arrays.sort(words);
        model.addAttribute("words", words);
        model.addAttribute("myworddic", wordAndTextServiceImpl.loadAllMyWords(name));
        return "listWordsJs";
    }
    /*
     @RequestMapping(value = "/wl/guestwordlist/{file_name}", method = RequestMethod.GET)
     public String showGuestWords(@PathVariable("file_name") String file_name, Model model) {
   
     WordFromText[] words = wordAndTextServiceImpl.analyzeFromFile(new File("c:\\"+file_name));
     model.addAttribute("words", words);
     return "listWords";
     }
     */
    
    //change personal word

    @RequestMapping(value = "/myw", params = {"id_worddic", "statuss"}, method = RequestMethod.POST)
    public void addPWord(@RequestParam(value = "id_worddic") String id_worddic, @RequestParam(value = "statuss") String statuss) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        byte status = Byte.valueOf(statuss).byteValue();
        wordAndTextServiceImpl.savePersonalWord(id_worddic, name, status);
    }

    @RequestMapping(value = "/wl/listMyWords", method = RequestMethod.GET)
    public String showMyWords(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        model.addAttribute("words", wordAndTextServiceImpl.loadAllMyWords(name));
        return "listMyWordsJs";
    }
  
    @RequestMapping(value = "/wl/game", method = RequestMethod.GET)
    public String game(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User usr = userService.getUserById(name);
        Profile prf = usr.getProfile();
        model.addAttribute("cntWordTest", prf.getCntWordTest());
        model.addAttribute("cntWordTestStudy",prf.getCntWordTestStudy());
        model.addAttribute("cntTrueAnswer", prf.getCntTrueAnswer());
        model.addAttribute("words", wordAndTextServiceImpl.loadAllMyWords(name));
        return "game";
    }
}
