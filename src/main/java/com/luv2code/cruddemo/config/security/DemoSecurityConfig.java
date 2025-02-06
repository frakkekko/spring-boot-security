package com.luv2code.cruddemo.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

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
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery("select user_id, pw, active from members where user_id=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select user_id, role from roles where user_id=?");

        return jdbcUserDetailsManager;
    }

}
