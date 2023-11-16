package com.lamija.authenticationapp.authentication.models;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequestBody {
    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;

    @NotBlank(message = "Username is required.")
    private String username;

    private LocalDate dateOfBirth;
    private String role;
}
