package com.explore.lapometer.stubs;

import com.explore.lapometer.interfaces.LoginInterface;

public class LoginStub implements LoginInterface {
    @Override
    public LoginResult login(String username, String password) {
        return LoginResult.LOGIN_SUCCESS;
    }

    public String encryptCredentials(String username, String password) { return "nothing"; }
}
