/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpException;
import org.springframework.http.HttpMethod;

/**
 *
 * @author Vadya
 */
public class HttpUtil {

    private final String USER_AGENT = "Mozilla/5.0";

    /**
     * @param url "
     */
    public static String sendGetRequest(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        //  con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public static Map<String, String> parseURLQuery(String query) {
        Map<String, String> result = new HashMap<String, String>();
        String params[] = query.split("&");
        for (String param : params) {
            String temp[] = param.split("=");
       
                result.put(temp[0], temp[1]);
        }
        return result;
    }
}
