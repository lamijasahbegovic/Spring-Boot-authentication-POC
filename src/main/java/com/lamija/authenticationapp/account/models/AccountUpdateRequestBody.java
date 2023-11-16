package com.lamija.authenticationapp.account.models;

import com.lamija.authenticationapp.common.constants.MessageConstants;
import java.time.LocalDate;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountUpdateRequestBody {
    @Size(min = 1, message = MessageConstants.EMPTY_STRINGS_NOT_ALLOWED)
    private String username;

    private LocalDate dateOfBirth;

    @Size(min = 1, message = MessageConstants.EMPTY_STRINGS_NOT_ALLOWED)
    private String role;
}
