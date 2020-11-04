package com.cinema.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserEmailValidator implements ConstraintValidator<UserEmailValidation, String> {
    private static final String REGEX_EMAIL = "(.+)@(.+)\\.(.+)$";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email != null && email.matches(REGEX_EMAIL);
    }
}
