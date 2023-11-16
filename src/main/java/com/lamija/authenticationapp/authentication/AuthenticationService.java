package com.lamija.authenticationapp.authentication;

import com.lamija.authenticationapp.account.exceptions.AccountUnauthorizedException;
import com.lamija.authenticationapp.account.models.AccountDTO;
import com.lamija.authenticationapp.security.UserDetailsImpl;
import com.lamija.authenticationapp.security.services.TokenAndCookieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class AuthenticationService {

    protected final AuthenticationManager authenticationManager;
    protected final TokenAndCookieService tokenAndCookieService;

    public AccountDTO authenticateAccount(String email, String password) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(email, password, null));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ((UserDetailsImpl) authentication.getPrincipal()).getAccount();
        } catch (AuthenticationException authenticationException) {
            throw new AccountUnauthorizedException();
        }
    }

    public HttpHeaders setAuthenticationHeaders(String email) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addAll(HttpHeaders.SET_COOKIE, tokenAndCookieService.setCookies(email));
        return httpHeaders;
    }

    public HttpHeaders clearAuthenticationHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addAll(HttpHeaders.SET_COOKIE, tokenAndCookieService.clearCookies());
        return httpHeaders;
    }
}
