/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service.Auth;

import domain.Profile;
import domain.security.Authority;
import domain.security.User;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mjson.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import service.UserService;

/**
 *
 * @author Vadya
 */
public class OAuth2ServiceImpl implements OAuth2Service {

    @Autowired
    private UserService userService;
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
    /*
     * properties for social
     */
    //facebook
    @Value("${facebook.URL}")
    private String facebook_URL;
    @Value("${facebook.API_KEY}")
    private String facebook_API_KEY;
    @Value("${facebook.API_SECRET}")
    private String facebook_API_SECRET;
    @Value("${facebook.URL_CALLBACK_REGISTRATION}")
    private String facebook_URL_CALLBACK_REGISTRATION;
    @Value("${facebook.SCOPE}")
    private String facebook_SCOPE;
    @Value("${facebook.URL_ACCESS_TOKEN}")
    private String facebook_URL_ACCESS_TOKEN;
    @Value("${facebook.URL_ME}")
    private String facebook_URL_ME;
    //vk
    @Value("${vk.URL}")
    private String vk_URL;
    @Value("${vk.API_KEY}")
    private String vk_API_KEY;
    @Value("${vk.API_SECRET}")
    private String vk_API_SECRET;
    @Value("${vk.URL_CALLBACK_REGISTRATION}")
    private String vk_URL_CALLBACK_REGISTRATION;
    @Value("${vk.SCOPE}")
    private String vk_SCOPE;
    @Value("${vk.URL_ACCESS_TOKEN}")
    private String vk_URL_ACCESS_TOKEN;
    @Value("${vk.URL_ME}")
    private String vk_URL_ME;

    /**
     *
     * @param socialtype 1-Facebook 2-vk
     * @return
     * @throws Exception
     */
    @Override
    public String createRequestCodeUrl(Byte socialtype) {
        switch (socialtype) {
            case 1: {
                return facebook_URL + "client_id=" + facebook_API_KEY + "&redirect_uri=" + facebook_URL_CALLBACK_REGISTRATION + "&scope=" + facebook_SCOPE;
            }
            case 2: {

                return vk_URL + "client_id=" + vk_API_KEY + "&redirect_uri=" + vk_URL_CALLBACK_REGISTRATION + "&scope=" + vk_SCOPE;
            }
            default: {
                return "login.jsp";
            }
        }

    }

    @Override
    public String createRequestTokenUrl(Byte socialtype, String code) {

        switch (socialtype) {
            case 1: {
                return facebook_URL_ACCESS_TOKEN + "client_id=" + facebook_API_KEY + "&client_secret=" + facebook_API_SECRET + "&redirect_uri=" + facebook_URL_CALLBACK_REGISTRATION + "&code=" + code;
            }

            case 2: {
                return vk_URL_ACCESS_TOKEN + "client_id=" + vk_API_KEY + "&client_secret=" + vk_API_SECRET + "&redirect_uri=" + vk_URL_CALLBACK_REGISTRATION + "&code=" + code;
            }
            default: {
                return "";
            }

        }


    }

    @Override
    public String createRequestInfoUrl(Byte socialtype, Map<String, String> parsedCR) {

        switch (socialtype) {
            case 1: {
                return facebook_URL_ME + "access_token=" + parsedCR.get("access_token");


            }

            case 2: {
                return vk_URL_ME + "uid=" + parsedCR.get("uid") + "&access_token=" + parsedCR.get("access_token");
            }
            default: {
                return "";
            }

        }
    }

    @Override
    public Map<String, String> parseCodeResponse(Byte socialtype, String codeStrResponce) {
        Map<String, String> result = new HashMap<String, String>();
        switch (socialtype) {
            case 1: {
                String params[] = codeStrResponce.split("&");
                for (String param : params) {
                    String temp[] = param.split("=");

                    if (temp[0].equals("access_token")) {
                        result.put("access_token", temp[1]);
                        System.err.println("TOKEN " + temp[1]);
                    }

                }
                break;
            }

            case 2: {
                Map<String, Json> tokenResponse = Json.read(codeStrResponce).asJsonMap();
                result.put("access_token", tokenResponse.get("access_token").asString());
                result.put("uid", tokenResponse.get("user_id").asString());
                break;
                // return tokenResponse.get("access_token").asString();
            }
        }
        return result;

    }

    @Override
    public Map<String, String> parseTokenResponse(Byte socialtype, String tokenStrResponse) {
        Map<String, String> result = new HashMap<String, String>();
        Map<String, Json> tokenResponse = Json.read(tokenStrResponse).asJsonMap();
        switch (socialtype) {
            case 1: {
                result.put("email", tokenResponse.get("email").asString());
                result.put("first_name", tokenResponse.get("first_name").asString());
                result.put("last_name", tokenResponse.get("last_name").asString());
                break;

            }

            case 2: {

                List<Json> response = tokenResponse.get("response").asJsonList();
                Map<String, Json> vkResponse = Json.read(response.get(0).toString()).asJsonMap();

                result.put("first_name", vkResponse.get("first_name").asString());
                result.put("last_name", vkResponse.get("last_name").asString());

                break;
            }
        }
        return result;
    }

    @Override
    public void authSocialUser(Byte socialtype, Map<String, String> tokenResponse, Map<String, String> codeResponse) {

        String usrStr = "";
        switch (socialtype) {
            case 1: {
                usrStr = tokenResponse.get("email").toLowerCase();
                break;
            }

            case 2: {
                usrStr = codeResponse.get("uid").toLowerCase();
                break;
            }
        }


        User user = userService.getUserById(usrStr);
        if (user != null) {
            user.setPassword(codeResponse.get("access_token"));

        } else {
            user = new User();
            user.setUsername(usrStr);
            user.setPassword(codeResponse.get("access_token"));
            user.setAuthority(new Authority("ROLE_USER"));
            Profile profile = new Profile();
            profile.setFirstName(tokenResponse.get("first_name"));
            profile.setEmail(tokenResponse.get("email"));
            profile.setLastName(tokenResponse.get("last_name"));
            user.setProfile(profile);

        }
        userService.userAdd(user);
        Authentication authentication = customAuthenticationProvider.trust(user);
    }
}
