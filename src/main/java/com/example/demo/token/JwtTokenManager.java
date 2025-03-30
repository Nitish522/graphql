package com.example.demo.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTokenManager {

    //    @Value("${security.jwt.secret-key}")
    private String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E+";

    //    @Value("${security.jwt.expiration-time}")
    private final long jwtExpiration = 3600000;//in milliSec

//    public static String generateToke(){
//        String jws = Jwts.builder()
//                .setIssuer("Stormpath")
//                .setSubject("msilverman")
//                .claim("name", "Micah Silverman")
//                .claim("scope", "admins")
//                // Fri Jun 24 2016 15:33:42 GMT-0400 (EDT)
//                .setIssuedAt(Date.from(Instant.ofEpochSecond(1466796822L)))
//                // Sat Jun 24 2116 15:33:42 GMT-0400 (EDT)
//                .setExpiration(Date.from(Instant.ofEpochSecond(4622470422L)))
//                .signWith(
//                        SignatureAlgorithm.HS256,
//                        TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=")
//                )
//                .compact();
//        return jws;
//    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractIssueAt(String token) {
        return extractClaim(token, Claims::getIssuedAt);
    }

    public Date extractExpiry(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public long getExpirationTime() {
        return jwtExpiration;
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUserName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUserName())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

//    public static void main(String[] args) {
//        JwtTokenManager tokenMgr = new JwtTokenManager();
////        var token = tokenMgr.generateToken(new UserDetails("Ntish"));
////        System.out.println(token);
//        var token ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJOdGlzaCIsImlhdCI6MTc0MzA3ODIzNCwiZXhwIjoxNzQzMDgxODM0fQ.i3GjgRlL9L2Pa4JJWBWmG5HZWCUWQlcA_9UG5RYZkzs";
//        var userNmae = tokenMgr.extractUsername(token);
//        System.out.println(userNmae);
//        var issueAt = tokenMgr.extractIssueAt(token);
//        System.out.println(issueAt);
//        var expiry = tokenMgr.extractExpiry(token);
//        System.out.println(expiry);
//        System.out.println(tokenMgr.isTokenExpired(token));
//    }
}
