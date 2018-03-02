/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * @author Vadya
 */
public class SecurityUtil {

    public static String generatePassword(int i) {
        return RandomStringUtils.randomAlphabetic(i);
    }
}
