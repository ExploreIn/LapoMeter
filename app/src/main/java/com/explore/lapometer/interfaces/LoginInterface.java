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
     * Returns the timeout value in milliseconds if the LoginResult is ACCESS_BLOCKED.
     */
    public long getAccessTimeout();

    /**
     * Returns the count of failed login attempts.
     */
    public int getLoginFailCount();

    /**
     * Gives the result of a login attempt. LOGIN_SUCCESS if login was successful, LOGIN_FAILED in case of failed login attempt and
     * ACCESS_BLOCKED if login attempts fails more than 3 times.
     */
    public enum LoginResult{
        LOGIN_SUCCESS,
        LOGIN_FAILED,
        ACCESS_BLOCKED
    }
}
