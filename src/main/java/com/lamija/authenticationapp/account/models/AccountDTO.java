package com.lamija.authenticationapp.account.models;

import com.lamija.authenticationapp.common.enums.RolesEnum;
import com.lamija.authenticationapp.common.validators.ageLimit.AgeLimit;
import com.lamija.authenticationapp.common.validators.enumClass.EnumClass;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDTO {
    private Long id;

    @NotBlank(message = "Email is required.")
    @Size(min = 4, max = 100, message = "Email cannot be longer than a 100 characters.")
    @Email(
            regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Email is in an invalid format.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 100, message = "Password cannot be longer than a 100 characters.")
    @Pattern(
            regexp =
                    "(?=.*[A-Z])(?=.*[a-zA-Z].*[a-zA-Z])(?=.*[0-9])(?=.*[-!\"#$%&'()*+,./:;<=>?@\\[\\]^_`{|}~\\\\]).*",
            message =
                    "Password must contain at least 1 uppercase letter, 1 special character and 1 number. It cannot be less than 8 characters.")
    private String password;

    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters.")
    private String username;

    @Past(message = "Date of birth cannot be in the past.")
    @AgeLimit
    private LocalDate dateOfBirth;

    @EnumClass(enumClass = RolesEnum.class, message = "The provided role is an invalid enum value.")
    private String role;
}
