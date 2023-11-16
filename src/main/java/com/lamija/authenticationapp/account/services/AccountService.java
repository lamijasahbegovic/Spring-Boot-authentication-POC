package com.lamija.authenticationapp.account.services;

import com.lamija.authenticationapp.account.AccountMapper;
import com.lamija.authenticationapp.account.exceptions.AccountPasswordIncorrectException;
import com.lamija.authenticationapp.account.models.AccountDTO;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class AccountService {

    private final AccountUtilityService accountUtilityService;
    private final PasswordEncoder passwordEncoder;

    public AccountDTO getAccount(String email) {
        return accountUtilityService.findAccountByEmail(email);
    }

    @Transactional
    public AccountDTO createAndSaveAccount(@Valid AccountDTO accountDTO) {
        accountUtilityService.throwExceptionIfEmailTaken(accountDTO.getEmail());
        accountDTO.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        return accountUtilityService.saveAccount(accountDTO);
    }

    @Transactional
    public void changePassword(String oldPassword, String newPassword, AccountDTO accountDTO) {
        if (passwordEncoder.matches(oldPassword, accountDTO.getPassword())) {
            accountDTO.setPassword(passwordEncoder.encode(newPassword));
            accountUtilityService.saveAccount(accountDTO);
        } else {
            throw new AccountPasswordIncorrectException();
        }
    }

    @Transactional
    public void deleteAccount(Long accountId) {
        accountUtilityService.deleteAccountById(accountId);
    }

    @Transactional
    public AccountDTO updateAccount(
            @Valid AccountDTO updatedAccountDTO, AccountDTO currentAccountDTO) {
        updatedAccountDTO.setId(currentAccountDTO.getId());
        updatedAccountDTO.setEmail(currentAccountDTO.getEmail());
        updatedAccountDTO.setPassword(currentAccountDTO.getPassword());

        AccountMapper.INSTANCE.copyAccountDto(updatedAccountDTO, currentAccountDTO);
        return accountUtilityService.saveAccount(currentAccountDTO);
    }
}
