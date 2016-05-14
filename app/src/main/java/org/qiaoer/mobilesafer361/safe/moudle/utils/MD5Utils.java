package org.qiaoer.mobilesafer361.safe.moudle.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by qiaoer on 16/5/14.
 */
public class MD5Utils {
    /**
     * md5加密算法
     *
     * @param text
     * @return
     */
    public static String encode(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(text.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : result) {
                int number = b & 0xfff;
                String hex = Integer.toHexString(number);
                if (hex.length() == 1) {
                    stringBuilder.append("0" + hex);
                } else {
                    stringBuilder.append(hex);
                }
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
