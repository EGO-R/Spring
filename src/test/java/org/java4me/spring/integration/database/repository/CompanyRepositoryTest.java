package org.java4me.spring.integration.database.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.java4me.spring.database.entity.Company;
import org.java4me.spring.database.repository.CompanyRepository;
import org.java4me.spring.integration.IntegrationTestBase;
import org.java4me.spring.integration.annotation.IT;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@RequiredArgsConstructor
class CompanyRepositoryTest extends IntegrationTestBase {

    private final EntityManager entityManager;
    private final CompanyRepository companyRepository;

    @Test
    void checkFindByQueries() {
        companyRepository.findByName("google");
    }

    @Test
    void findById() {
        var actRes = entityManager.find(Company.class, 1);

        assertNotNull(actRes);

        assertThat(actRes.getLocales()).hasSize(2);
    }
}