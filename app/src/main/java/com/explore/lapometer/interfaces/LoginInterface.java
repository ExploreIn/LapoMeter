package com.explore.lapometer.interfaces;

/**
 * Interface to be extended which has to be used for logging into the system.
 */
public interface LoginInterface {

    /**
     * Returns the login result according to the provided credentials. The return type will be LoginResult.
     */
    public LoginResult login( String username, String password );

    /**
     * Gives the result of a login attempt. LOGIN_SUCCESS if login was successful, LOGIN_FAILED in case of failed login attempt
     */
    public enum LoginResult{
        LOGIN_SUCCESS,
        LOGIN_FAILED,
        ACCESS_BLOCKED
    }
}
