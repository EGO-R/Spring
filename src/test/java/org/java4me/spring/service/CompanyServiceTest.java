package org.java4me.spring.service;

import org.java4me.spring.database.entity.Company;
import org.java4me.spring.database.repository.CompanyRepository;
import org.java4me.spring.dto.CompanyReadDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {
    private static final int COMPANY_ID = 1;
    @Mock
    private CompanyRepository companyRepository;
    @InjectMocks
    private CompanyService companyService;


    @Test
    void findById() {
        doReturn(Optional.of(new Company(COMPANY_ID, "", null)))
                .when(companyRepository).findById(COMPANY_ID);

        var actRes = companyService.findById(COMPANY_ID);

        assertTrue(actRes.isPresent());

        var expRes = new CompanyReadDto(COMPANY_ID, null);
        actRes.ifPresent(act -> assertEquals(act, expRes));

        verify(companyRepository).findById(COMPANY_ID);
        verifyNoMoreInteractions(companyRepository);
    }
}