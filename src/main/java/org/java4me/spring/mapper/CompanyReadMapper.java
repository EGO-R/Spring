package org.java4me.spring.mapper;

import org.java4me.spring.database.entity.Company;
import org.java4me.spring.dto.CompanyReadDto;
import org.springframework.stereotype.Component;

@Component
public class CompanyReadMapper implements Mapper<Company, CompanyReadDto> {
    @Override
    public CompanyReadDto map(Company obj) {
        return new CompanyReadDto(
                obj.getId(),
                obj.getName()
        );
    }
}
