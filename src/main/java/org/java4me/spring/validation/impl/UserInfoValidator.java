package org.java4me.spring.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.java4me.spring.dto.UserCreateEditDto;
import org.java4me.spring.validation.UserInfo;
import org.springframework.util.StringUtils;

public class UserInfoValidator implements ConstraintValidator<UserInfo, UserCreateEditDto> {
    @Override
    public boolean isValid(UserCreateEditDto userCreateEditDto, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.hasText(userCreateEditDto.getFirstname()) ||
                StringUtils.hasText(userCreateEditDto.getLastname());
    }
}
