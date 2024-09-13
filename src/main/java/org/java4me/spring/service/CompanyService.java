package org.java4me.spring.service;

import lombok.RequiredArgsConstructor;
import org.java4me.spring.database.repository.CompanyRepository;
import org.java4me.spring.dto.CompanyReadDto;
import org.java4me.spring.mapper.CompanyReadMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyReadMapper companyReadMapper;

    public Optional<CompanyReadDto> findById(Integer id) {
        return companyRepository.findById(id)
                .map(companyReadMapper::map);
    }

    public List<CompanyReadDto> findAll() {
        return companyRepository.findAll().stream()
                .map(companyReadMapper::map)
                .toList();
    }
}
