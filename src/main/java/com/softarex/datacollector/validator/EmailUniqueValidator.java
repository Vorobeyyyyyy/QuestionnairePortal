package com.softarex.datacollector.validator;

import com.softarex.datacollector.model.repository.UserRepository;
import com.softarex.datacollector.validator.annotation.UniqueEmail;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailUniqueValidator implements ConstraintValidator<UniqueEmail, String> {
    private final UserRepository userRepository;

    public EmailUniqueValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (!(authentication instanceof AnonymousAuthenticationToken) && authentication.getName().equals(email)) ||
                (email != null && !userRepository.existsByEmail(email));
    }
}
