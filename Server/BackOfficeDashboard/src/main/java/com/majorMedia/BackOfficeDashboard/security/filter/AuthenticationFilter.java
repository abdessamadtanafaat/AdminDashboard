package com.majorMedia.BackOfficeDashboard.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.majorMedia.BackOfficeDashboard.security.SecurityConstants;
import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Privilege;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.model.requests.AuthenticationRequest;
import com.majorMedia.BackOfficeDashboard.model.responses.AuthenticationResponse;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.util.ImageUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import  com.majorMedia.BackOfficeDashboard.security.manager.CustomAuthenticationManager;
import org.springframework.stereotype.Component;


@AllArgsConstructor

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final CustomAuthenticationManager authenticationManager ;
    private final AdminRepository adminRepository;

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
    public String generateToken(Admin admin) {
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
            .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY.getEncoded()));
}

    private void sendAuthenticationResponse(HttpServletResponse response, String token, Admin admin) throws IOException {


        AuthenticationResponse authenticationResponse = new AuthenticationResponse(token, admin);
        String jsonResponse = new ObjectMapper().writeValueAsString(authenticationResponse);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }


}
