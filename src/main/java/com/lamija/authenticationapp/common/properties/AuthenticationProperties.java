package com.lamija.authenticationapp.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "authentication")
@Component
@Getter
@Setter
public class AuthenticationProperties {
    private String accessSecret;
    private String refreshSecret;
    private String accessName;
    private String refreshName;
    private long accessExpiration;
    private long refreshExpiration;
    private String passwordResetSecret;
    private long passwordResetExpiration;
    private int accessCookieExpiration;
    private int refreshCookieExpiration;
    private String accessCookieSecure;
    private String refreshCookieSecure;
    private String accessCookieHttpOnly;
    private String refreshCookieHttpOnly;
}
