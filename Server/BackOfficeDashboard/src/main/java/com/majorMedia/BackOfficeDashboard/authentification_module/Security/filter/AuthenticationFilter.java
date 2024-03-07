package com.majorMedia.BackOfficeDashboard.authentification_module.Security.filter;

import com.majorMedia.BackOfficeDashboard.authentification_module.Security.Manager.CustomAuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private CustomAuthenticationManager authenticationManager;

    @Override
    public Aut

}
