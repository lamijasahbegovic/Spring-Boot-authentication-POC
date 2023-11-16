package com.lamija.authenticationapp.account.services;

import com.lamija.authenticationapp.account.AccountMapper;
import com.lamija.authenticationapp.account.AccountRepository;
import com.lamija.authenticationapp.account.exceptions.AccountEmailTakenException;
import com.lamija.authenticationapp.account.exceptions.AccountUnauthorizedException;
import com.lamija.authenticationapp.account.models.AccountDTO;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class AccountUtilityService {

    private final AccountRepository accountRepository;
    protected final AccountMapper accountMapper = AccountMapper.INSTANCE;

    public AccountDTO saveAccount(@Valid AccountDTO accountDTO) {
        return accountMapper.accountEntityToAccountDto(
                accountRepository.save(accountMapper.accountDtoToAccountEntity(accountDTO)));
    }

    public void throwExceptionIfEmailTaken(String email) {
        if (accountRepository.existsByEmail(email)) {
            throw new AccountEmailTakenException();
        }
    }

    public AccountDTO findAccountByEmail(String email) {
        return accountMapper.accountEntityToAccountDto(
                this.accountRepository
                        .findByEmail(email)
                        .orElseThrow(AccountUnauthorizedException::new));
    }

    public void deleteAccountById(Long accountId) {
        this.accountRepository.deleteById(accountId);
    }
}
