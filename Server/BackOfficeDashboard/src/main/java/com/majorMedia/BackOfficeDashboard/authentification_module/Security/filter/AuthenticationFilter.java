package com.majorMedia.BackOfficeDashboard.authentification_module.Security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.majorMedia.BackOfficeDashboard.authentification_module.Security.Manager.CustomAuthenticationManager;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.AuthenticationRequest;
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
@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final CustomAuthenticationManager authenticationManager ;

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

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

    }


}
