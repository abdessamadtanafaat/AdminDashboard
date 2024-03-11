package com.majorMedia.BackOfficeDashboard.Security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.majorMedia.BackOfficeDashboard.Exception.NotFoundEmailException;
import com.majorMedia.BackOfficeDashboard.Security.SecurityConstants;
import com.majorMedia.BackOfficeDashboard.entity.Admin;
import com.majorMedia.BackOfficeDashboard.entity.Privilege;
import com.majorMedia.BackOfficeDashboard.entity.Role;
import com.majorMedia.BackOfficeDashboard.model.AuthenticationRequest;
import com.majorMedia.BackOfficeDashboard.model.AuthenticationResponse;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.service.AdminService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import  com.majorMedia.BackOfficeDashboard.Security.Manager.CustomAuthenticationManager;
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final CustomAuthenticationManager authenticationManager ;
    //private final AdminService adminService;
    private final AdminRepository adminRepository;

    public AuthenticationFilter(CustomAuthenticationManager customAuthenticationManager, AdminRepository adminRepository) {
        this.adminRepository = adminRepository ;
        this.authenticationManager = customAuthenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try{
            AuthenticationRequest admin = new ObjectMapper().readValue(request.getInputStream() , AuthenticationRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(admin.getEmail() ,admin.getPassword());
            return authenticationManager.authenticate(authentication);
        }
        catch(IOException ex){
            throw new RuntimeException();

        }

    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(failed.getMessage());
        response.getWriter().flush();
    }

/*    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String token = JWT.create()
                .withSubject(authResult.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))

                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
//        response.addHeader("Authorization", SecurityConstants.BEARER + token);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(token , adminRepository.findByEmail(authResult.getName()).get());
        String jsonResponse =objectMapper.writeValueAsString(authenticationResponse);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }*/

/*    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        try {
            String username = authResult.getName();
            Admin admin = adminRepository.findByEmail(username).orElse(null);
            Integer adminId = admin.getId();
            String firstname = admin.getFirstname();
            String lastname = admin.getLastname();
            String email = admin.getEmail();

            List <String> roles = admin.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toList());

            List<String> privileges = admin.getRoles().stream()
                    .flatMap(role -> role.getPrivileges().stream())
                    .map(Privilege::getName)
                    .collect(Collectors.toList());

            String token = JWT.create()
                    .withSubject(authResult.getName())
                    //.withClaim("roles", (Map<String, ?>) roles)
                    .withClaim("id", adminId)
                    .withClaim("firstname",firstname)
                    .withClaim("lastname",lastname)
                    .withClaim("email",email)
                    .withClaim("roles",roles)
                    .withClaim("privileges", privileges)
                    .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
                    .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
//        response.addHeader("Authorization", SecurityConstants.BEARER + token);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            AuthenticationResponse authenticationResponse = new AuthenticationResponse(token , adminRepository.findByEmail(authResult.getName()).get());
            String jsonResponse =objectMapper.writeValueAsString(authenticationResponse);
            response.setContentType("application/json");
            response.getWriter().write(jsonResponse);
        }catch(Exception e){
            e.printStackTrace();

        }

    }*/
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    try {
        String username = authResult.getName();
        Admin admin = adminRepository.findByEmail(username).orElse(null);

        if (admin != null) {
            String token = generateToken(admin);
            sendAuthenticationResponse(response, token, admin);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    } catch (Exception e) {
        e.printStackTrace();
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
    private String generateToken(Admin admin) {
    List<String> roles = admin.getRoles().stream()
            .map(Role::getName)
            .collect(Collectors.toList());

    List<String> privileges = admin.getRoles().stream()
            .flatMap(role -> role.getPrivileges().stream())
            .map(Privilege::getName)
            .collect(Collectors.toList());

    return JWT.create()
            .withSubject(admin.getEmail())
            .withClaim("id", admin.getId())
            .withClaim("firstname", admin.getFirstname())
            .withClaim("lastname", admin.getLastname())
            .withClaim("email", admin.getEmail())
            .withClaim("roles", roles)
            .withClaim("privileges", privileges)
            .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
            .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
}

    private void sendAuthenticationResponse(HttpServletResponse response, String token, Admin admin) throws IOException {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(token, admin);
        String jsonResponse = new ObjectMapper().writeValueAsString(authenticationResponse);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }


}
