package com.lamija.authenticationapp.authentication;

import com.lamija.authenticationapp.account.AccountMapper;
import com.lamija.authenticationapp.account.models.AccountResponseBody;
import com.lamija.authenticationapp.account.services.AccountIdMaskingService;
import com.lamija.authenticationapp.account.services.AccountService;
import com.lamija.authenticationapp.authentication.models.*;
import com.lamija.authenticationapp.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AccountService accountService;
    private final AuthenticationService authenticationService;
    private final AccountIdMaskingService accountIdMaskingService;
    private final AccountMapper accountMapper = AccountMapper.INSTANCE;
    public static final String refreshTokenRoutePath = "/auth/refresh";

    @PostMapping(path = "/register")
    @Operation(
            summary = "Register account",
            description = "Registers a new account and provides them with authentication.")
    @ApiResponse(
            responseCode = "201",
            description = "Success",
            headers = {
                @Header(
                        name = HttpHeaders.SET_COOKIE,
                        description = "Cookie with an authentication JWT token: \"Access-Token\"",
                        schema = @Schema(type = "string")),
                @Header(
                        name = HttpHeaders.SET_COOKIE + " ",
                        description = "Cookie with a refresh JWT token: \"Refresh-Token\"",
                        schema = @Schema(type = "string"))
            })
    public ResponseEntity<AccountResponseBody> register(
            @Valid @RequestBody RegisterRequestBody requestBody) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(authenticationService.setAuthenticationHeaders(requestBody.getEmail()))
                .body(
                        accountMapper.accountDtoToAccountResponseBody(
                                accountService.createAndSaveAccount(
                                        accountMapper.registerRequestBodyToAccountDto(requestBody)),
                                accountIdMaskingService));
    }

    @PostMapping(path = "/login")
    @Operation(
            summary = "Login account",
            description = "Logs in an account and provides them with authentication.")
    @ApiResponse(
            responseCode = "200",
            description = "Success",
            headers = {
                @Header(
                        name = HttpHeaders.SET_COOKIE,
                        description = "Cookie with an authentication JWT token: \"Access-Token\"",
                        schema = @Schema(type = "string")),
                @Header(
                        name = HttpHeaders.SET_COOKIE + " ",
                        description = "Cookie with a refresh JWT token: \"Refresh-Token\"",
                        schema = @Schema(type = "string"))
            })
    public ResponseEntity<AccountResponseBody> login(
            @Valid @RequestBody LoginRequestBody requestBody) {
        return ResponseEntity.ok()
                .headers(authenticationService.setAuthenticationHeaders(requestBody.getEmail()))
                .body(
                        accountMapper.accountDtoToAccountResponseBody(
                                authenticationService.authenticateAccount(
                                        requestBody.getEmail(), requestBody.getPassword()),
                                accountIdMaskingService));
    }

    @PostMapping(path = "/refresh")
    @Operation(
            summary = "Get new access token",
            description = "Gets a new access token using a non-expired refresh token.")
    @Parameter(name = "userDetails", hidden = true)
    @ApiResponse(
            responseCode = "200",
            description = "Success",
            headers = {
                @Header(
                        name = HttpHeaders.SET_COOKIE,
                        description = "Cookie with an authentication JWT token: \"Access-Token\"",
                        schema = @Schema(type = "string")),
                @Header(
                        name = HttpHeaders.SET_COOKIE + " ",
                        description = "Cookie with a refresh JWT token: \"Refresh-Token\"",
                        schema = @Schema(type = "string"))
            })
    public ResponseEntity<Object> refresh(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok()
                .headers(authenticationService.setAuthenticationHeaders(userDetails.getUsername()))
                .body(null);
    }

    @PostMapping(path = "/logout")
    @Operation(summary = "Logout", description = "Revokes account access and authentication.")
    public ResponseEntity<Object> logout() {
        return ResponseEntity.ok()
                .headers(authenticationService.clearAuthenticationHeaders())
                .body(null);
    }
}
