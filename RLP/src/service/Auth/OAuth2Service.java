/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service.Auth;

import java.util.Map;

/**
 *
 * @author Vadya
 */
public interface OAuth2Service {

    public String createRequestCodeUrl(Byte socialtype);

    public String createRequestTokenUrl(Byte socialtype, String code);

    public String createRequestInfoUrl(Byte socialtype, Map<String, String> parsedCR);
//    public String getToken(Byte socialtype, String httpTokenResponce);

    public Map<String, String> parseCodeResponse(Byte socialtype, String codeStrResponce);

    public Map<String, String> parseTokenResponse(Byte socialtype, String tokenStrResponse);

    public void authSocialUser(Byte socialtype, Map<String, String> tokenResponse, Map<String, String> codeResponse);
}
