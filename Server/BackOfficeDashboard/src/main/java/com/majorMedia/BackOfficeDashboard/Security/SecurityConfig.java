package com.majorMedia.BackOfficeDashboard.Security;


import com.majorMedia.BackOfficeDashboard.Security.Manager.CustomAuthenticationManager;
import com.majorMedia.BackOfficeDashboard.Security.filter.AuthenticationFilter;
import com.majorMedia.BackOfficeDashboard.Security.filter.ExceptionHandlerFilter;
import com.majorMedia.BackOfficeDashboard.Security.filter.JwtAuthorizationFilter;
import com.majorMedia.BackOfficeDashboard.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    private final CustomAuthenticationManager customAuthenticationManager;
    private final AdminService  adminService;

    private static  final Long MAX_AGE = 3600L;
    private static final  int CORS_FILTER_ORDER =-102;
    public SecurityConfig(@Lazy CustomAuthenticationManager authenticationManager , @Lazy AdminService adminService) {
        this.customAuthenticationManager=authenticationManager;
        this.adminService = adminService ;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager , adminService);

        authenticationFilter.setFilterProcessesUrl(SecurityConstants.AUTHENTICATE_PATH);
        http.csrf(AbstractHttpConfigurer::disable)
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
    @Bean
    public FilterRegistrationBean corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT));
        configuration.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name()));
        configuration.setMaxAge(MAX_AGE);
        source.registerCorsConfiguration("/**", configuration);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));

        bean.setOrder(CORS_FILTER_ORDER);
        return bean;


    }



}
