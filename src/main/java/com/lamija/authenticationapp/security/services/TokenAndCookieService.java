package com.lamija.authenticationapp.security.services;

import com.lamija.authenticationapp.common.properties.AuthenticationProperties;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenAndCookieService {

    private final AuthenticationProperties authenticationProperties;
    private final TokenUtilityService tokenUtilityService;
    private final CookieUtilityService cookieUtilityService;

    public String createAccessToken(String email) {
        return tokenUtilityService.create(
                email,
                authenticationProperties.getAccessExpiration(),
                authenticationProperties.getAccessSecret());
    }

    public String createRefreshToken(String email) {
        return tokenUtilityService.create(
                email,
                authenticationProperties.getRefreshExpiration(),
                authenticationProperties.getRefreshSecret());
    }

    public String extractAccessTokenSubject(String token) {
        return tokenUtilityService.extractSubject(
                token, authenticationProperties.getAccessSecret());
    }

    public String extractRefreshTokenSubject(String token) {
        return tokenUtilityService.extractSubject(
                token, authenticationProperties.getRefreshSecret());
    }

    public List<String> setCookies(String email) {
        return Arrays.asList(
                cookieUtilityService.setAccessCookie(this.createAccessToken(email)).toString(),
                cookieUtilityService.setRefreshCookie(this.createRefreshToken(email)).toString());
    }

    public List<String> clearCookies() {
        return Arrays.asList(
                cookieUtilityService.clearAccessCookie().toString(),
                cookieUtilityService.clearRefreshCookie().toString());
    }

    public String extractRefreshToken(HttpServletRequest request) {
        return cookieUtilityService.extractToken(
                request, authenticationProperties.getRefreshName());
    }

    public String extractAccessToken(HttpServletRequest request) {
        return cookieUtilityService.extractToken(request, authenticationProperties.getAccessName());
    }
}
