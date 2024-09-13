package org.java4me.spring.config;

import jakarta.annotation.PostConstruct;
import org.java4me.spring.config.condition.JpaCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;


@Conditional(JpaCondition.class)
@Configuration
public class JpaConfiguration {

    @PostConstruct
    void init() {
        System.out.println("Jpa configuration is enabled");
    }
}
