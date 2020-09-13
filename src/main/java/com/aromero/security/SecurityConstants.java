package com.aromero.security;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/users/**";
    public static final String H2_URL = "/h2-console/**";
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Baerer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long TOKEN_EXPIRATION_TIME = 180_000; //30 seconds
}
