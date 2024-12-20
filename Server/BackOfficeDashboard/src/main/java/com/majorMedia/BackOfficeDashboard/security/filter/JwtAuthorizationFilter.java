package com.majorMedia.BackOfficeDashboard.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.majorMedia.BackOfficeDashboard.security.BlacklistToken.BlacklistRepository;
import com.majorMedia.BackOfficeDashboard.security.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final BlacklistRepository blacklistRepository;

    public JwtAuthorizationFilter(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
            String header = request.getHeader("Authorization");
            if (header == null || !header.startsWith(SecurityConstants.BEARER)) {
                //throw new RuntimeException("Unauthorized");
                if (Arrays.stream(SecurityConstants.WHITE_LIST)
                        .anyMatch(requestURI ->
                                requestURI.matches("/swagger-ui/\\*\\*"))
                ) {
                    filterChain.doFilter(request, response);
                    return;
                } else {
                    throw new RuntimeException("Unauthorized");
                }
            }

            String token = header.replace("Bearer ", "");
            if (blacklistRepository.existsByToken(token)) {
                throw new JWTVerificationException("Token blacklisted");
            }

            //String token = header.replace("Bearer ", "");
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY.getEncoded()))
                    .build()
                    .verify(token);
            String user = decodedJWT.getSubject();

            List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
            List<GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);


        }

}
