package com.lamija.authenticationapp.account;

import com.lamija.authenticationapp.account.models.AccountDTO;
import com.lamija.authenticationapp.account.models.AccountEntity;
import com.lamija.authenticationapp.account.models.AccountResponseBody;
import com.lamija.authenticationapp.account.models.AccountUpdateRequestBody;
import com.lamija.authenticationapp.account.services.AccountIdMaskingService;
import com.lamija.authenticationapp.authentication.models.RegisterRequestBody;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDTO accountEntityToAccountDto(AccountEntity accountEntity);

    AccountEntity accountDtoToAccountEntity(AccountDTO accountDTO);

    AccountDTO registerRequestBodyToAccountDto(RegisterRequestBody registerRequestBody);
    //  todo: optimize this mapping
    @Mapping(target = "verified", ignore = true)
    AccountResponseBody accountDtoToAccountResponseBody(
            AccountDTO accountDTO, @Context AccountIdMaskingService accountIdMaskingService);
    //  todo: optimize this mapping
    @Mapping(target = "password", constant = "Password123!")
    @Mapping(target = "id", constant = "1L")
    @Mapping(target = "email", constant = "tmp@tmp.com")
    AccountDTO accountUpdateRequestBodyToAccountDto(
            AccountUpdateRequestBody accountUpdateRequestBody);

    void copyAccountDto(AccountDTO source, @MappingTarget AccountDTO target);

    @AfterMapping
    default void maskAccountId(
            AccountDTO accountDTO,
            @MappingTarget AccountResponseBody accountResponseBody,
            @Context AccountIdMaskingService accountIdMaskingService) {
        accountResponseBody.setId(accountIdMaskingService.maskId(accountDTO.getId()));
    }
}
