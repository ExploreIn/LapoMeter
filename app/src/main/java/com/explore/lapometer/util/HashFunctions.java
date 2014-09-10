package com.explore.lapometer.util;

import android.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashFunctions {

    private static String convertToHex(byte[] data) throws java.io.IOException
    {
        String hexValue = null;
        hexValue = Base64.encodeToString(data, 0, data.length, 0); //0 = no potions convert byt to string
        return hexValue;
    }

    private String convertToHash(String input) throws NoSuchAlgorithmException, java.io.IOException
    {
        MessageDigest message = MessageDigest.getInstance("SHA-256"); //Encryption standard is SHA2
        message.reset(); //initialise
        byte buffer[] = input.getBytes();
        message.update(buffer);
        byte digest[] = message.digest();
        return convertToHex(digest);


    }

    public String hashCredentials(String username, String password) throws NoSuchAlgorithmException, java.io.IOException
    {
        return convertToHash(username + password);
    }


}