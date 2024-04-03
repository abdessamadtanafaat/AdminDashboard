package com.majorMedia.BackOfficeDashboard.util;

import com.majorMedia.BackOfficeDashboard.security.BlacklistToken.BlacklistRepository;
import com.majorMedia.BackOfficeDashboard.security.BlacklistToken.BlacklistToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;

import java.util.Date;

import static com.majorMedia.BackOfficeDashboard.security.SecurityConstants.SECRET_KEY;

@AllArgsConstructor
public class JwtUtils {
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
            e.printStackTrace();
            return null; // Or handle the exception as needed
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

}
