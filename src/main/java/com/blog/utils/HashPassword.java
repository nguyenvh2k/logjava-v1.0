package com.blog.utils;

import java.security.MessageDigest;
import java.util.Base64;

public class HashPassword {
    public static String toSHA2(String value) {
        String salt = "23456@$rvhgyt6";
        String result = null;
        value += salt;
        try {
            byte[] dataByte = value.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            result = Base64.getEncoder().encodeToString(md.digest(dataByte));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}