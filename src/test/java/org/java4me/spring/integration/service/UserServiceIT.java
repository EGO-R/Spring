package org.java4me.spring.integration.service;

import lombok.RequiredArgsConstructor;
import org.java4me.spring.database.entity.Role;
import org.java4me.spring.dto.UserCreateEditDto;
import org.java4me.spring.integration.IntegrationTestBase;
import org.java4me.spring.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class UserServiceIT extends IntegrationTestBase {
    private static final Long USER_1 = 1L;
    private static final Integer COMPANY_1 = 1;

    private final UserService userService;

    @Test
    void findAll() {
        var result = userService.findAll();
        assertThat(result).hasSize(5);
    }

    @Test
    void findById() {
        var maybeUser = userService.findById(USER_1);
        assertThat(maybeUser).isPresent();
        var user = maybeUser.get();
        assertThat(user.getUsername()).isEqualTo("ivan@gmail.com");
    }

    @Test
    void create() {
        var userDto = new UserCreateEditDto(
                "test@gmail.com",
                "test",
                LocalDate.now(),
                "Test",
                "Test",
                Role.USER,
                COMPANY_1,
                new MockMultipartFile("test", new byte[0])
        );

        var createdUser = userService.create(userDto);

        assertThat(createdUser.getId()).isNotNull();
    }

    @Test
    void update() {
        var userDto = new UserCreateEditDto(
                "test@gmail.com",
                "",
                LocalDate.now(),
                "Test",
                "Test",
                Role.USER,
                COMPANY_1,
                new MockMultipartFile("test", new byte[0])
        );

        var maybeUpdatedUser = userService.update(USER_1, userDto);

        assertThat(maybeUpdatedUser).isPresent();

        var updatedUser = maybeUpdatedUser.get();

        assertThat(updatedUser.getUsername()).isEqualTo(userDto.getUsername());
        assertEquals(userDto.getUsername(), updatedUser.getUsername());
        assertEquals(userDto.getFirstname(), updatedUser.getFirstname());
        assertEquals(userDto.getLastname(), updatedUser.getLastname());
    }

    @Test
    void delete() {
        var isDelete = userService.delete(USER_1);

        assertTrue(isDelete);

        assertThat(userService.findById(USER_1)).isEmpty();
    }
}
