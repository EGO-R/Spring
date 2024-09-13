package org.java4me.spring.config;

import lombok.RequiredArgsConstructor;
import org.java4me.spring.database.entity.Role;
import org.java4me.spring.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

import static org.java4me.spring.database.entity.Role.ADMIN;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
//                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasAuthority(ADMIN.getAuthority())
                        .requestMatchers("/login", "/users/registration", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .requestMatchers("/users/{id}/delete").hasAuthority(ADMIN.getAuthority())
                        .anyRequest().authenticated()

                )
//                .httpBasic(Customizer.withDefaults())
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/users"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID"))
                .oauth2Login(config -> config
                        .loginPage("/login")
                        .defaultSuccessUrl("/users")
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService())))
                .build();
    }

    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        return userRequest -> {
            String email = userRequest.getIdToken().getClaim("email");
            // TODO: 08.09.2024 create new user userService.create
            var userDetails = userService.loadUserByUsername(email);
//            new OidcUserService().loadUser()
            var oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());

            var userDetailsMethods = Set.of(UserDetails.class.getMethods());

            return (OidcUser) Proxy.newProxyInstance(SecurityConfiguration.class.getClassLoader(),
                    new Class[]{UserDetails.class, OidcUser.class},
                    (proxy, method, args) -> userDetailsMethods.contains(method)
                            ? method.invoke(userDetails, args)
                            : method.invoke(oidcUser, args));
        };
    }
}
