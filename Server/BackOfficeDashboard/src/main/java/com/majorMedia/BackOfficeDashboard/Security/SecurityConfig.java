package com.majorMedia.BackOfficeDashboard.Security;


import com.majorMedia.BackOfficeDashboard.Security.Manager.CustomAuthenticationManager;
import com.majorMedia.BackOfficeDashboard.Security.filter.AuthenticationFilter;
import com.majorMedia.BackOfficeDashboard.Security.filter.ExceptionHandlerFilter;
import com.majorMedia.BackOfficeDashboard.Security.filter.JwtAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAuthenticationManager customAuthenticationManager) throws Exception{
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager);

        authenticationFilter.setFilterProcessesUrl(SecurityConstants.AUTHENTICATE_PATH);
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->
                        auth

                                .requestMatchers(
                                        "api/v1/auth/**"
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
                                )
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore( new ExceptionHandlerFilter(),AuthenticationFilter.class )
                .addFilter(authenticationFilter)
                .addFilterAfter(new JwtAuthorizationFilter(), AuthenticationFilter.class)
                .sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                 return http.build();

    }
    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder();}


}
