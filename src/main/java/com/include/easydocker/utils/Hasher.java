package com.include.easydocker.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

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


}
