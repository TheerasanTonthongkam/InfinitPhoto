package com.playtech.infinitphoto.service;


import com.playtech.infinitphoto.cookie.PersistentCookieStore;

import java.net.HttpCookie;

public class ServiceProvider {
    private static AlbumServiceImp alumServiceInstance;
    private static AuthenticationServiceImp authenticationServiceInstance;

    private ServiceProvider() {}

    public static AlbumServiceImp getAlumServiceInstance(HttpCookie cookie) {
        if (alumServiceInstance == null) {
            alumServiceInstance = new AlbumServiceImp(cookie);
        }
        return alumServiceInstance;
    }

    public static AuthenticationServiceImp getAuthenticationServiceInstance(PersistentCookieStore store) {
        if (authenticationServiceInstance == null) {
            authenticationServiceInstance = new AuthenticationServiceImp(store);
        }
        return authenticationServiceInstance;
    }
}
