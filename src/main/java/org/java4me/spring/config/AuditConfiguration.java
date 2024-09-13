package org.java4me.spring.config;

import org.java4me.spring.Application;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Optional;

@Configuration
@EnableJpaAuditing
//@EnableEnversRepositories(basePackageClasses = Application.class)
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        // SecurityContext.getCurrentUser.getUsername()
        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(authentication -> (UserDetails) authentication.getPrincipal())
                .map(UserDetails::getUsername);
    }
}
