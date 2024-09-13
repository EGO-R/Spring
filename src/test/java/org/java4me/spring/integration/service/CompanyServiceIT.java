package org.java4me.spring.integration.service;

import lombok.RequiredArgsConstructor;
import org.java4me.spring.dto.CompanyReadDto;
import org.java4me.spring.integration.annotation.IT;
import org.java4me.spring.service.CompanyService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@IT
@RequiredArgsConstructor
public class CompanyServiceIT {
    private static final int COMPANY_ID = 1;

    private final CompanyService companyService;

//    private final DatabaseProperties databaseProperties;

    @Test
    @Disabled
    void findById() {
        var actRes = companyService.findById(COMPANY_ID);

        assertTrue(actRes.isPresent());

        var expRes = new CompanyReadDto(COMPANY_ID, null);
        actRes.ifPresent(act -> assertEquals(act, expRes));
    }
}
