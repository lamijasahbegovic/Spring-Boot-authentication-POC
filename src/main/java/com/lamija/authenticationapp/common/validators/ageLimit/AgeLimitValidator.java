package com.lamija.authenticationapp.common.validators.ageLimit;

import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AgeLimitValidator implements ConstraintValidator<AgeLimit, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null || value.isBefore(LocalDate.now().minusYears(100))) {
            return false;
        }
        return value.isBefore(LocalDate.now().minusYears(12));
    }
}
