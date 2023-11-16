package com.lamija.authenticationapp.authentication.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordChangeRequestBody {

    @NotBlank(message = "Old password is required.")
    private String oldPassword;

    @NotBlank(message = "New password is required.")
    @Size(min = 8, max = 100, message = "Password cannot be longer than a 100 characters.")
    @Pattern(
            regexp =
                    "(?=.*[A-Z])(?=.*[a-zA-Z].*[a-zA-Z])(?=.*[0-9])(?=.*[-!\"#$%&'()*+,./:;<=>?@\\[\\]^_`{|}~\\\\]).*",
            message =
                    "Password must contain at least 1 uppercase letter, 1 special character and 1 number. It cannot be less than 8 characters.")
    private String newPassword;
}
