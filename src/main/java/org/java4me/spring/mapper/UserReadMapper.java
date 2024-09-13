package org.java4me.spring.mapper;

import lombok.RequiredArgsConstructor;
import org.java4me.spring.database.entity.User;
import org.java4me.spring.dto.CompanyReadDto;
import org.java4me.spring.dto.UserReadDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {
    private final CompanyReadMapper companyReadMapper;
    @Override
    public UserReadDto map(User obj) {
        var company = Optional.ofNullable(obj.getCompany())
                .map(companyReadMapper::map)
                .orElse(null);

        return new UserReadDto(
                obj.getId(),
                obj.getUsername(),
                obj.getBirthDate(),
                obj.getFirstname(),
                obj.getLastname(),
                obj.getRole(),
                company,
                obj.getImage()
        );
    }
}
