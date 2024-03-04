package com.majorMedia.BackOfficeDashboard.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf->csrf.disable())
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->
                    auth
                    .requestMatchers(
                                    "/api/v1/auth/**"
                                    ,"/v3/api-docs"
                                    ,"/v2/api-docs"
                                    ,"/v3/api-docs/**"
                                    ,"/swagger-resources"
                                    ,"/swagger-resources/**"
                                    ,"/configuration/ui"
                                    ,"/configuration/security"
                                    ,"/swagger-ui/**"
                                    ,"/webjars/**"
                                    ,"/swagger-ui.html"
                                    ,"/api/v1/auth/register"
                                    ,"/api/v1/auth1/password-request"
                    ).permitAll()
                    .anyRequest().authenticated()
                )
                .sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }



}
