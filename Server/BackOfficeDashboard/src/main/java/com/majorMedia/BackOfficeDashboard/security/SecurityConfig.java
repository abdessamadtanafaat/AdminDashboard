package com.majorMedia.BackOfficeDashboard.security;

import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.security.BlacklistToken.BlacklistRepository;
import com.majorMedia.BackOfficeDashboard.security.filter.AuthenticationFilter;
import com.majorMedia.BackOfficeDashboard.security.filter.ExceptionHandlerFilter;
import com.majorMedia.BackOfficeDashboard.security.filter.JwtAuthorizationFilter;
import com.majorMedia.BackOfficeDashboard.security.manager.CustomAuthenticationManager;
import com.majorMedia.BackOfficeDashboard.util.ServiceUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig {
    private final CustomAuthenticationManager customAuthenticationManager;
    private final AdminRepository adminRepository;
    private final BlacklistRepository blacklistRepository;


    /*    private static  final Long MAX_AGE = 3600L;
    private static final  int CORS_FILTER_ORDER =-102;*/
    public SecurityConfig(@Lazy CustomAuthenticationManager authenticationManager,
                          @Lazy AdminRepository adminRepository,
                          BlacklistRepository blacklistRepository,
                          ServiceUtils adminService

    ) {
        this.customAuthenticationManager=authenticationManager;
        this.adminRepository = adminRepository ;
        this.blacklistRepository = blacklistRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager , adminRepository );

        authenticationFilter.setFilterProcessesUrl(SecurityConstants.AUTHENTICATE_PATH);
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->
                        auth
                                .requestMatchers(SecurityConstants.WHITE_LIST)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .addFilterBefore( new ExceptionHandlerFilter(),AuthenticationFilter.class )
                .addFilter(authenticationFilter)
                .addFilterAfter(new JwtAuthorizationFilter(blacklistRepository), AuthenticationFilter.class)
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
                HttpMethod.DELETE.name(),
                HttpMethod.PATCH.name()));
        configuration.setMaxAge(SecurityConstants.MAX_AGE);
        source.registerCorsConfiguration("/**", configuration);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));

        bean.setOrder(SecurityConstants.CORS_FILTER_ORDER);
        return bean;


    }



}
