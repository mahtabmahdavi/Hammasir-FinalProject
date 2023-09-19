package com.hammasir.routingreport.security;

import com.hammasir.routingreport.model.enumuration.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/authentication/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/reports/most-accidental").permitAll()
                                .requestMatchers(HttpMethod.GET, "/reports/active").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                                .requestMatchers(HttpMethod.POST, "/reports/create").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                                .requestMatchers(HttpMethod.PUT, "/reports/like").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                                .requestMatchers(HttpMethod.PUT, "/reports/approve").hasAnyAuthority(Role.ADMIN.name())
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
