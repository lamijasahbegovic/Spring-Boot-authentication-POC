package com.lamija.authenticationapp.security.configuration;

import com.lamija.authenticationapp.common.exceptions.AuthenticationExceptionHandler;
import com.lamija.authenticationapp.security.CustomAuthenticationFilter;
import com.lamija.authenticationapp.security.services.CookieUtilityService;
import com.lamija.authenticationapp.security.services.TokenAndCookieService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationExceptionHandler authenticationExceptionHandler;
    private final UserDetailsService userDetailsService;
    private final TokenAndCookieService tokenAndCookieService;
    private final CookieUtilityService cookieUtilityService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) ->
                web.ignoring()
                        .antMatchers(
                                "/actuator/health",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/auth/register",
                                "/auth/login",
                                "/enums/role");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors().and().csrf().disable().authorizeHttpRequests().anyRequest().authenticated();
        http.authenticationProvider(authenticationProvider)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationExceptionHandler);
        http.addFilterBefore(
                new CustomAuthenticationFilter(
                        tokenAndCookieService, userDetailsService, authenticationExceptionHandler),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
