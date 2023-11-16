package com.lamija.authenticationapp.account;

import com.lamija.authenticationapp.account.models.AccountResponseBody;
import com.lamija.authenticationapp.account.models.AccountUpdateRequestBody;
import com.lamija.authenticationapp.account.services.AccountIdMaskingService;
import com.lamija.authenticationapp.account.services.AccountService;
import com.lamija.authenticationapp.authentication.AuthenticationService;
import com.lamija.authenticationapp.authentication.models.PasswordChangeRequestBody;
import com.lamija.authenticationapp.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/account", headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AccountController {

    private final AuthenticationService authenticationService;
    private final AccountService accountService;
    private final AccountIdMaskingService accountIdMaskingService;
    private final AccountMapper accountMapper = AccountMapper.INSTANCE;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get account",
            description = "Gets account info for the account fetched from the access token.")
    @Parameter(name = "userDetails", hidden = true)
    public AccountResponseBody getCurrentAccount(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return accountMapper.accountDtoToAccountResponseBody(
                this.accountService.getAccount(userDetails.getUsername()), accountIdMaskingService);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Update account",
            description = "Updates info for the account fetched from the access token.")
    @Parameter(name = "userDetails", hidden = true)
    public AccountResponseBody updateCurrentAccount(
            @Valid @RequestBody AccountUpdateRequestBody accountUpdateRequestBody,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return accountMapper.accountDtoToAccountResponseBody(
                this.accountService.updateAccount(
                        accountMapper.accountUpdateRequestBodyToAccountDto(
                                accountUpdateRequestBody),
                        userDetails.getAccount()),
                accountIdMaskingService);
    }

    @DeleteMapping
    @Operation(
            summary = "Delete account",
            description = "Deletes the account fetched from the access token.")
    @Parameter(name = "userDetails", hidden = true)
    public ResponseEntity<Object> deleteCurrentAccount(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        this.accountService.deleteAccount(userDetails.getId());
        HttpHeaders httpHeaders = authenticationService.clearAuthenticationHeaders();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(httpHeaders).body(null);
    }

    @PutMapping(path = "/password/reset")
    @Operation(
            summary = "Account password reset",
            description = "Changes current account's password and revokes their authentication.")
    @Parameter(name = "userDetails", hidden = true)
    public ResponseEntity<Object> passwordReset(
            @Valid @RequestBody PasswordChangeRequestBody passwordChangeRequestBody,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        accountService.changePassword(
                passwordChangeRequestBody.getOldPassword(),
                passwordChangeRequestBody.getNewPassword(),
                userDetails.getAccount());
        return ResponseEntity.status(HttpStatus.OK)
                .headers(authenticationService.clearAuthenticationHeaders())
                .body(null);
    }
}
