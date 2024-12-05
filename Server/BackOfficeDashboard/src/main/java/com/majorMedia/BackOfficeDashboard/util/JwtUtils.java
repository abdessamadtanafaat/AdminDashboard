package com.majorMedia.BackOfficeDashboard.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.majorMedia.BackOfficeDashboard.security.BlacklistToken.BlacklistRepository;
import com.majorMedia.BackOfficeDashboard.security.BlacklistToken.BlacklistToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;

import static com.majorMedia.BackOfficeDashboard.security.SecurityConstants.SECRET_KEY;

@AllArgsConstructor
public class JwtUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String extractEmailFromToken(String jwtToken) {
        try {
            // Parse the JWT token
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(jwtToken);

            // Extract email claim from the token
            return claims.getBody().get("email", String.class);
        } catch (MalformedJwtException | SignatureException e) {
            return null;
        }
    }
    public static boolean isValidJwt(String jwtToken) {
        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();

            long currentTimeMillis = System.currentTimeMillis();
            return  !claims.getExpiration().before(new Date(currentTimeMillis));

        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e ){
            return false;
        }
    }
    public static List<String> extractRolesFromToken(String jwtToken) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET_KEY.getEncoded()))
                    .build()
                    .verify(jwtToken);

            String rolesJson = decodedJWT.getClaim("roles").asString();
            return objectMapper.readValue(rolesJson, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            return null;
        }
    }

}
