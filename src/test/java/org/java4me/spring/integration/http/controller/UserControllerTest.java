package org.java4me.spring.integration.http.controller;

import lombok.RequiredArgsConstructor;
import org.hamcrest.collection.IsCollectionWithSize;
import org.java4me.spring.database.entity.Role;
import org.java4me.spring.dto.UserCreateEditDto;
import org.java4me.spring.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.java4me.spring.dto.UserCreateEditDto.Fields.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerTest extends IntegrationTestBase {
    private final MockMvc mockMvc;

    @Test
    @WithMockUser(username = "test@gmail.com",
            password = "test",
            authorities = {"ADMIN", "USER"})
    void findAll() throws Exception {
        mockMvc.perform(get("/users")
                        .with(user("test@gmail.com")
                                .authorities(Role.ADMIN, Role.USER)
                                .password("test")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("users/users"))
                .andExpect(model().attributeExists("page"));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                        .param(username, "test@gmail.com")
                        .param(firstname, "Test")
                        .param(lastname, "Test")
                        .param(role, "ADMIN")
                        .param(companyId, "1")
                        .param(birthDate, "2000-01-01"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}")
                );
    }
}