package com.aromero.validator;

import com.aromero.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (user.getPassword() != null && user.getPassword().length() < 6) {
            errors.rejectValue("password", "Length", "Password must be at least 6 characters.");
        }

        if (user.getPassword() != null && !user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "Match", "Password must match.");
        }
    }
}
