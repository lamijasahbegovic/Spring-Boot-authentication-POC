package com.lamija.authenticationapp.security;

import com.lamija.authenticationapp.account.exceptions.AccountNotFoundException;
import com.lamija.authenticationapp.authentication.AuthenticationController;
import com.lamija.authenticationapp.authentication.exceptions.AuthenticationInvalidException;
import com.lamija.authenticationapp.common.exceptions.AuthenticationExceptionHandler;
import com.lamija.authenticationapp.security.services.TokenAndCookieService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final TokenAndCookieService tokenAndCookieService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationExceptionHandler authenticationExceptionHandler;

    private boolean isRequestRouteEqualTo(HttpServletRequest request, String route) {
        return request.getRequestURI().equals(route);
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        boolean isTokenRefresh =
                isRequestRouteEqualTo(request, AuthenticationController.refreshTokenRoutePath);

        String token =
                !isTokenRefresh
                        ? tokenAndCookieService.extractAccessToken(request)
                        : tokenAndCookieService.extractRefreshToken(request);

        if ((token == null)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            UserDetails userDetails;
            if (isTokenRefresh) {
                userDetails =
                        userDetailsService.loadUserByUsername(
                                tokenAndCookieService.extractRefreshTokenSubject(token));
            } else {
                userDetails =
                        userDetailsService.loadUserByUsername(
                                tokenAndCookieService.extractAccessTokenSubject(token));
            }
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (AccountNotFoundException exception) {
            authenticationExceptionHandler.handleTokenExceptions(
                    response, new AuthenticationInvalidException());
            return;
        } catch (AuthenticationException exception) {
            authenticationExceptionHandler.handleTokenExceptions(response, exception);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
