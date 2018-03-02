/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import domain.Profile;
import domain.security.User;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import service.Auth.OAuth2ServiceImpl;
import service.UserService;
import util.HttpUtil;
import util.MailUtil;

/**
 *
 * @author SasaV
 */
@Controller
public class UserController {

    private static Logger log = Logger.getLogger(UserController.class.getName());
    @Autowired
    private UserService userService;
    @Autowired
    private MailUtil mailMail;
    @Autowired
    private OAuth2ServiceImpl oAuth2ServiceImpl;
/*	
    @RequestMapping(value = "/userAdd", method = RequestMethod.GET)
    public String addUserForm(Model model) {
        User new_user = new User();
        model.addAttribute("user", new_user);
        return "userAdd";
    }

    @RequestMapping(value = "/userAdd", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user, BindingResult result) {
        if ((result.hasErrors()) || (user.getUsername().isEmpty()) || (user.getPassword().isEmpty())) {
            return "userAdd";
        } else {
            user.setAuthority(new Authority("ROLE_ADMIN"));
            userService.userAdd(user);
            return "mainpage";
        }
    }

    @RequestMapping(value = "/personAdd", method = RequestMethod.GET)
    public String addPersonForm(Model model) {

        User user = new User();
        user.setUsername("Va");
        user.setPassword("11");
        user.setAuthority(new Authority("ROLE_ADMIN"));


        Profile profile = new Profile();
        profile.setFirstName("vv");
        user.setProfile(profile);
        userService.userAdd(user);
        return "mainpage";
    }

    @RequestMapping(value = "/regUser", method = RequestMethod.GET)
    public String regUserForm(Model model) {
        NewEmail nEmail = new NewEmail();
        model.addAttribute("email", nEmail);
        return "regUser";
    }

    @RequestMapping(value = "/regUser", method = RequestMethod.POST)
    public String regUser(@ModelAttribute("email") NewEmail nEmail, BindingResult result) {
        String pass = SecurityUtil.generatePassword(6);
        mailMail.sendMail("vadim.sasa@gmail.com",
                nEmail.getEmail(),
                "Password",
                "Your password: " + pass);

        User user = new User();
        user.setUsername(nEmail.getEmail());
        user.setPassword(pass);
        user.setAuthority(new Authority("ROLE_USER"));

        Profile profile = new Profile();
        profile.setEmail(nEmail.getEmail());
        user.setProfile(profile);
        userService.userAdd(user);
        return "regUser";
    }
    */
    @RequestMapping(value = "/usersettings", method = RequestMethod.GET)
    public String userSettings(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User usr = userService.getUserById(name);
        Profile prf = usr.getProfile();
        model.addAttribute("cntWordTest", prf.getCntWordTest());
        model.addAttribute("cntWordTestStudy",prf.getCntWordTestStudy());
        model.addAttribute("cntTrueAnswer", prf.getCntTrueAnswer());
        return "usersettings";
    }
    
    @RequestMapping(value = "/usersettings", method = RequestMethod.POST)
    public void userSettingsPost(@ModelAttribute("cntWordTest") int cntWordTest, @ModelAttribute("cntWordTestStudy") int cntWordTestStudy, @ModelAttribute("cntTrueAnswer") int cntTrueAnswer, BindingResult result) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User usr = userService.getUserById(name);
        Profile prf = usr.getProfile();
        prf.setCntTrueAnswer(cntTrueAnswer);
        prf.setCntWordTest(cntWordTest);
        prf.setCntWordTestStudy(cntWordTestStudy);
        userService.userAdd(usr);
    }
    

    @RequestMapping(value = "/socialsignin", params = "socialtype", method = RequestMethod.GET)
    public ModelAndView socialSignIn(@RequestParam("socialtype") Byte socialtype) throws Exception {
        return new ModelAndView(new RedirectView(oAuth2ServiceImpl.createRequestCodeUrl(socialtype), true, true, true));
    }

    @RequestMapping(value = "/socialsignin/{socialtype}", params = "code")
    public ModelAndView socialAccessCode(@RequestParam("code") String code, @PathVariable("socialtype") Byte socialtype, HttpServletRequest request) throws Exception {
        Map<String, String> codeResponse = new HashMap<String, String>();
        Map<String, String> tokenResponse = new HashMap<String, String>();
        String authRequest = HttpUtil.sendGetRequest(oAuth2ServiceImpl.createRequestTokenUrl(socialtype, code));
        codeResponse = oAuth2ServiceImpl.parseCodeResponse(socialtype, authRequest);
        String tokenStrResponse = HttpUtil.sendGetRequest(oAuth2ServiceImpl.createRequestInfoUrl(socialtype, codeResponse));
        tokenResponse = oAuth2ServiceImpl.parseTokenResponse(socialtype, tokenStrResponse);
        oAuth2ServiceImpl.authSocialUser(socialtype, tokenResponse, codeResponse);
        return new ModelAndView(new RedirectView("/mainpage", true, true, false));
    }
}
