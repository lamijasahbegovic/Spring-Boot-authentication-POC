package com.lamija.authenticationapp.security.services;

import com.lamija.authenticationapp.common.properties.AuthenticationProperties;
import java.util.Arrays;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieUtilityService {

    private final AuthenticationProperties authenticationProperties;

    public HttpCookie setAccessCookie(String token) {
        return ResponseCookie.from(authenticationProperties.getAccessName(), token)
                .maxAge(authenticationProperties.getAccessCookieExpiration())
                .secure(Boolean.parseBoolean(authenticationProperties.getAccessCookieSecure()))
                .httpOnly(Boolean.parseBoolean(authenticationProperties.getAccessCookieHttpOnly()))
                .path("/")
                .build();
    }

    public HttpCookie setRefreshCookie(String token) {
        return ResponseCookie.from(authenticationProperties.getRefreshName(), token)
                .maxAge(authenticationProperties.getRefreshCookieExpiration())
                .secure(Boolean.parseBoolean(authenticationProperties.getRefreshCookieSecure()))
                .httpOnly(Boolean.parseBoolean(authenticationProperties.getRefreshCookieHttpOnly()))
                .path("/")
                .build();
    }

    private HttpCookie clearCookie(String name) {
        return ResponseCookie.from(name, "")
                .maxAge(0)
                .secure(true)
                .httpOnly(true)
                .path("/")
                .build();
    }

    public HttpCookie clearAccessCookie() {
        return clearCookie(authenticationProperties.getAccessName());
    }

    public HttpCookie clearRefreshCookie() {
        return clearCookie(authenticationProperties.getRefreshName());
    }

    public String extractToken(HttpServletRequest request, String name) {
        Cookie cookie = null;
        if (request.getCookies() != null) {
            cookie =
                    Arrays.stream(request.getCookies())
                            .filter(c -> name.equals(c.getName()))
                            .findFirst()
                            .orElse(null);
        }
        return cookie != null ? cookie.getValue() : null;
    }
}
