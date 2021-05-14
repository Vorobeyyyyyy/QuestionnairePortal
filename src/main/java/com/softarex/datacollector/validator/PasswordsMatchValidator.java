package com.softarex.datacollector.validator;

import com.softarex.datacollector.model.dto.UserDto;
import com.softarex.datacollector.validator.annotation.PasswordsMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, UserDto> {
    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        String password = userDto.getPassword();
        return password != null && password.equals(userDto.getRepeatedPassword());
    }
}
