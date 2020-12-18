package com.org.api.vietin.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Utils
 *
 *
 * @since 2020/11/14
 */
public class EncryptUtils {

    /**
     * Encrypt MD5
     * @param text
     * @return String
     * @throws Exception
     */
    public static String encryptMD5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bResult = md.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bResult)
                sb.append(String.format("%02x", b));

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

}
