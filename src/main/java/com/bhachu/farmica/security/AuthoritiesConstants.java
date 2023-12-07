package com.bhachu.farmica.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String DIRECTOR = "ROLE_DIRECTOR";

    public static final String MANAGER = "ROLE_MANAGER";

    public static final String EMPLOYEE = "ROLE_EMPLOYEE";

    private AuthoritiesConstants() {}
}
