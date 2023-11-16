package com.lamija.authenticationapp.account.services;

import com.lamija.authenticationapp.account.exceptions.AccountIdInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountIdMaskingService {

    private final TextEncryptor textEncryptor;

    public String maskId(Long id) {
        return textEncryptor.encrypt(id.toString());
    }

    public Long unmaskId(String id) {
        try {
            return Long.parseLong(textEncryptor.decrypt(id));
        } catch (Exception ex) {
            throw new AccountIdInvalidException();
        }
    }
}
