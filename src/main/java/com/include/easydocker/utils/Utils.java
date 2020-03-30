package com.include.easydocker.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static String hash(String text) {

        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        assert messageDigest != null;
        messageDigest.update(text.getBytes());

        return new String(messageDigest.digest());
    }

    public static String redirectTo(String url) {
        return "redirect:" + url;
    }

    public static String toCompose(String name, String properties){
        StringBuilder sb = new StringBuilder();
        sb.append("  ").append(name).append(":\n");
        if(properties == null)
                return sb.toString();
        String[] lines = properties.split("\\r?\\n");
        for(String line : lines)
            sb.append("    ").append(line).append("\n");
        return sb.toString();
    }
}
