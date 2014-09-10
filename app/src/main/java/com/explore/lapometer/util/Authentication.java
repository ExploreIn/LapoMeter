package com.explore.lapometer.util;

import com.explore.lapometer.activities.LoginActivity;
import com.explore.lapometer.interfaces.LoginInterface;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by I311849 on 9/10/2014.
 */
public class Authentication {
    public LoginInterface.LoginResult login( String username, String password, String currentHash ) {
        if ( encryptCredentials(username, password).equals(currentHash) ) {
            return LoginInterface.LoginResult.LOGIN_SUCCESS;
        } else {
            return LoginInterface.LoginResult.LOGIN_FAILED;
        }
    }

    public String encryptCredentials(String username, String password) {
        try {
            return new HashFunctions().hashCredentials(username, password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
