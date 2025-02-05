package com.luv2code.cruddemo.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/employees").hasRole(UserRoles.EMPLOYEE.getValue())
                        .requestMatchers(HttpMethod.GET, "/api/employees/**").hasRole(UserRoles.EMPLOYEE.getValue())
                        .requestMatchers(HttpMethod.POST, "/api/employees").hasRole(UserRoles.MANAGER.getValue())
                        .requestMatchers(HttpMethod.PUT, "/api/employees/**").hasRole(UserRoles.MANAGER.getValue())
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole(UserRoles.ADMIN.getValue()));

        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.csrf(csrf -> csrf.disable());
        return httpSecurity.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails john = User.builder()
                .username("john")
                .password("{noop}johnpsw")
                .roles(UserRoles.EMPLOYEE.getValue())
                .build();

        UserDetails mary = User.builder()
                .username("mery")
                .password("{noop}merypsw")
                .roles(
                        UserRoles.EMPLOYEE.getValue(),
                        UserRoles.MANAGER.getValue()
                )
                .build();

        UserDetails susan = User.builder()
                .username("susan")
                .password("{noop}susanpsw")
                .roles(
                        UserRoles.EMPLOYEE.getValue(),
                        UserRoles.MANAGER.getValue(),
                        UserRoles.ADMIN.getValue()
                )
                .build();

        return new InMemoryUserDetailsManager(john, mary, susan);
    }
}
