package org.java4me.spring.dto;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public interface PersonalInfoIT {
    String getFirstname();
    String getLastname();
    LocalDate getBirthDate();

    @Value("#{target.firstname + ' ' + target.lastname}")
    String getFullName();
}
