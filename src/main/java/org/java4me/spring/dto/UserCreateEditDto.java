package org.java4me.spring.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.java4me.spring.database.entity.Company;
import org.java4me.spring.database.entity.Role;
import org.java4me.spring.validation.UserInfo;
import org.java4me.spring.validation.group.CreateAction;
import org.java4me.spring.validation.group.UpdateAction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Value
@FieldNameConstants
@UserInfo(groups = {CreateAction.class, UpdateAction.class})
public class UserCreateEditDto {

    @Email
    String username;

    @NotBlank(groups = CreateAction.class)
    String rawPassword;

    @DateTimeFormat(pattern = "yyy-MM-dd")
    LocalDate birthDate;

    @Size(min = 3, max = 64)
    String firstname;

    String lastname;

    @NotNull
    Role role;

    @NotNull
    Integer companyId;

    MultipartFile image;
}
