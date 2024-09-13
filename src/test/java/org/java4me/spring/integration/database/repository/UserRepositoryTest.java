package org.java4me.spring.integration.database.repository;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.java4me.spring.database.entity.Role;
import org.java4me.spring.database.entity.User;
import org.java4me.spring.database.repository.UserRepository;
import org.java4me.spring.dto.PersonalInfo;
import org.java4me.spring.dto.UserFilter;
import org.java4me.spring.integration.IntegrationTestBase;
import org.java4me.spring.integration.annotation.IT;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class UserRepositoryTest extends IntegrationTestBase {

    private final UserRepository userRepository;

    @Test
    void checkEntityModification() {
        var ivan = userRepository.findById(1L).get();
        ivan.setBirthDate(ivan.getBirthDate().plusYears(1));
        userRepository.flush();
        System.out.println();
    }

    @Test
    void checkFilter() {
        var filter = new UserFilter(null, "ov", LocalDate.now());
        var users = userRepository.findAllByFilter(filter);
        assertThat(users).hasSize(4);
    }

    @Test
    void checkProjections() {
        var users = userRepository.findAllByCompanyId(1);
        System.out.println(users);
    }

    @Test
    void checkPageable() {
        var pageable = PageRequest.of(1, 2, Sort.by("id"));
        var page = userRepository.findAllBy(pageable);
        page.forEach(user -> System.out.println(user.getCompany().getName()));

        while (page.hasNext()) {
            page = userRepository.findAllBy(page.nextPageable());
            page.forEach(user -> System.out.println(user.getCompany().getName()));
        }
    }

    @Test
    void checkSort() {
        var typedSort = Sort.sort(User.class);
        var sort = typedSort.by(User::getId).descending()
                .and(typedSort.by(User::getFirstname));

        var allUsers = userRepository.findTop3ByBirthDateBefore(LocalDate.now(), sort);
        assertThat(allUsers).hasSize(3);
    }

    @Test
    void checkUpdate() {
        var resCount = userRepository.updateRole(Role.USER, 1L, 5L);
        assertEquals(2, resCount);
    }

    @Test
    void findAllBy() {
        var users = userRepository.findAllBy("a", "ov");
//        System.out.println(users);
    }

}