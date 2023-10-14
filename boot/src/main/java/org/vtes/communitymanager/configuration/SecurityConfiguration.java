package org.vtes.communitymanager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain calendarSecurityFilterChain(
        final HttpSecurity httpSecurity
    ) throws Exception {
        return httpSecurity.csrf().disable().authorizeHttpRequests(
                (authorizeHttpRequests) -> authorizeHttpRequests.anyRequest().permitAll())
            .build();
    }
}
