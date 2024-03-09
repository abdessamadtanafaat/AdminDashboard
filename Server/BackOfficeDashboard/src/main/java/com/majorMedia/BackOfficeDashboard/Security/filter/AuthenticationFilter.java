package com.majorMedia.BackOfficeDashboard.Security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.majorMedia.BackOfficeDashboard.Security.SecurityConstants;
import com.majorMedia.BackOfficeDashboard.model.AuthenticationRequest;
import com.majorMedia.BackOfficeDashboard.model.AuthenticationResponse;
import com.majorMedia.BackOfficeDashboard.service.AdminService;
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
import  com.majorMedia.BackOfficeDashboard.Security.Manager.CustomAuthenticationManager;
@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final CustomAuthenticationManager authenticationManager ;
    private final AdminService adminService;


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

        String token = JWT.create()
                .withSubject(authResult.getName())

                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
//        response.addHeader("Authorization", SecurityConstants.BEARER + token);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(token , adminService.findByEmail(authResult.getName()));
        String jsonResponse =objectMapper.writeValueAsString(authenticationResponse);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }


}
