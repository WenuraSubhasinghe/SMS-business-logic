package com.sms.businesslogic.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.sms.businesslogic.entity.Permission.*;
import static com.sms.businesslogic.entity.Permission.CUSTOMER_DELETE;
import static com.sms.businesslogic.entity.Role.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**")
                    .permitAll()

                .requestMatchers("/api/v1/category/**").hasAnyRole(ADMIN.name(), CUSTOMER.name())
                .requestMatchers(GET,"/api/v1/category/**").hasAnyAuthority(ADMIN_READ.name(),CUSTOMER_READ.name())
                .requestMatchers(POST,"/api/v1/category/**").hasAnyAuthority(ADMIN_CREATE.name())
                .requestMatchers(PUT,"/api/v1/category/**").hasAnyAuthority(ADMIN_UPDATE.name())
                .requestMatchers(DELETE,"/api/v1/category/**").hasAnyAuthority(ADMIN_DELETE.name())

                .requestMatchers("/api/v1/delivery/**").hasAnyRole(ADMIN.name(), CUSTOMER.name(), DELIVERY_PERSON.name())
                .requestMatchers(GET,"/api/v1/delivery/**").hasAnyAuthority(ADMIN.name(), CUSTOMER.name(), DELIVERY_PERSON.name())
                .requestMatchers(POST,"/api/v1/delivery/**").hasAnyAuthority(ADMIN.name(), DELIVERY_PERSON.name())
                .requestMatchers(PUT,"/api/v1/delivery/**").hasAnyAuthority(ADMIN.name(), DELIVERY_PERSON.name())
                .requestMatchers(DELETE,"/api/v1/delivery/**").hasAnyAuthority(ADMIN_DELETE.name())

                .requestMatchers("/api/v1/order/**").hasAnyRole(ADMIN.name(), CUSTOMER.name(), DELIVERY_PERSON.name(), INVENTORY_KEEPER.name())
                .requestMatchers(GET,"/api/v1/order/**").hasAnyAuthority(ADMIN.name(), CUSTOMER.name(), DELIVERY_PERSON.name(), INVENTORY_KEEPER.name())
                .requestMatchers(POST,"/api/v1/order/**").hasAnyAuthority(ADMIN.name(), CUSTOMER.name())
                .requestMatchers(PUT,"/api/v1/order/**").hasAnyAuthority(ADMIN.name())
                .requestMatchers(DELETE,"/api/v1/order/**").hasAnyAuthority(ADMIN_DELETE.name())

                .requestMatchers("/api/v1/product/**").hasAnyRole(ADMIN.name(), CUSTOMER.name(), INVENTORY_KEEPER.name())
                .requestMatchers(GET,"/api/v1/product/**").hasAnyAuthority(ADMIN.name(), CUSTOMER.name(), INVENTORY_KEEPER.name())
                .requestMatchers(POST,"/api/v1/product/**").hasAnyAuthority(ADMIN.name(), INVENTORY_KEEPER.name())
                .requestMatchers(PUT,"/api/v1/product/**").hasAnyAuthority(ADMIN.name(),INVENTORY_KEEPER.name())
                .requestMatchers(DELETE,"/api/v1/product/**").hasAnyAuthority(ADMIN_DELETE.name())

                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}