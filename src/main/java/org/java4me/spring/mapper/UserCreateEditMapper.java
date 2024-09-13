package org.java4me.spring.mapper;

import lombok.RequiredArgsConstructor;
import org.java4me.spring.database.entity.Company;
import org.java4me.spring.database.entity.User;
import org.java4me.spring.database.repository.CompanyRepository;
import org.java4me.spring.dto.UserCreateEditDto;
import org.java4me.spring.dto.UserReadDto;
import org.java4me.spring.service.ImageService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public User map(UserCreateEditDto obj) {
        User user = new User();
        copy(obj, user);
        return user;
    }

    private void copy(UserCreateEditDto object, User user) {
        user.setUsername(object.getUsername());
        user.setFirstname(object.getFirstname());
        user.setLastname(object.getLastname());
        user.setBirthDate(object.getBirthDate());
        user.setRole(object.getRole());
        user.setCompany(getCompany(object.getCompanyId()));

        Optional.ofNullable(object.getRawPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);

        Optional.ofNullable(object.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> user.setImage(image.getOriginalFilename()));
    }

    private Company getCompany(Integer id) {
        return Optional.ofNullable(id)
                .flatMap(companyRepository::findById)
                .orElse(null);
    }
}
