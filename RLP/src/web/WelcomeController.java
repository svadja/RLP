/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import service.WordAndTextServiceImpl;
import service.FileUtilImpl;
import service.UserService;


/**
 *
 * @author SasaV
 */
@Controller
public class WelcomeController {

    private static Logger log = Logger.getLogger(WelcomeController.class.getName());
    @Autowired
    private UserService userService;
    @Autowired
    private FileUtilImpl fileUtilImpl;
    @Autowired
    private WordAndTextServiceImpl wordAndTextServiceImpl;
  
    
    @RequestMapping(value="/errors/404.html")
    public String handle404() {
    	return "errorPage";
    }
    
    @RequestMapping(value = "/mainpage", method = RequestMethod.GET)
    public ModelAndView mainpage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getName().equals("anonymousUser")) {


            //тут нада взяти домашню сторінку людини
            return new ModelAndView("redirect:helppage");
        } else {
            return new ModelAndView("redirect:helppage");
        }
    }

    @RequestMapping(value = "/helppage", method = RequestMethod.GET)
    public String helppage() {
        return "helppage";
    }
    /*
    @RequestMapping(value = "/welcomeuser", method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcomeuser";
    }
    




//    @RequestMapping(value = "/testttt", method = RequestMethod.GET)
//    public String testControler(Model model) {
//
//        return "helppage";
//        //     wordAndTextServiceImpl.loadDic(new File("D:\\dic.txt"), "Eng", "Rus");
//
//    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void home(@ModelAttribute("status") String status, @ModelAttribute("wordid") String wordid, BindingResult result) {
        System.err.println(wordid+ " " +status+" Hello!!!!!!!!!!!!!!!!!!!");
    }*/
}
