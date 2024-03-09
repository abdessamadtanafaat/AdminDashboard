//package com.majorMedia.BackOfficeDashboard.service;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.majorMedia.BackOfficeDashboard.Security.SecurityConstants;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//
//@Service
//public class JwtService {
//
//    public String generateToken(String userEmail) {
//        return JWT.create()
//                .withSubject(userEmail)
//                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
//                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
//    }
//
//
///*
//@Service
//public class JwtService {
//
//    @Value("${jwt.secret-key}")
//    private String SECRET_KEY;
//    @Value("${jwt.expiration}")
//    private long jwtExpiration;
//
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims:: getSubject);
//    }
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
//        final Claims claims = extractAllClaims(token);
//        return  claimsResolver.apply(claims);
//    }
//    public String generateToken(UserDetails userDetails){
//        return generateToken(new HashMap<>(), userDetails);
//    }
//
//    public String generateToken(
//            Map<String, Object> extracClaims,
//            UserDetails userDetails
//            ){
//        return buildToken(extracClaims, userDetails, jwtExpiration);
//    }
//
//    private String buildToken(
//            Map<String, Object> extracClaims,
//            UserDetails userDetails,
//            long expiration
//    ){
//        System.out.println(expiration);
//        return  Jwts
//                .builder()
//                .setClaims(extracClaims)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                .signWith(getSignInkey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//    public boolean isTokenValid(String token, UserDetails userDetails){
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//    }
//
//    private boolean isTokenExpired(String token){
//
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    private Claims extractAllClaims(String token){
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSignInkey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//
//    }
//    private Key getSignInkey() {
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//*/
//
//
//
//}
