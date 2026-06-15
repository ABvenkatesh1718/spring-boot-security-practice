package com.venkatesh.main.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JWTUtility {

    private final String SECRET_KEY = "venkatesh-secret-key-locked-12312234567865433567576453421345";

    // Modern JJWT uses javax.crypto.SecretKey instead of java.security.Key
    private final SecretKey SIGNING_KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // 1. Generate Token
    public String generateToken(String username) {
        long currentTimeMillis = System.currentTimeMillis();
        long oneHourInMillis = 3600000; // 1 hour

        Date now = new Date(currentTimeMillis);
        Date expirationDate = new Date(currentTimeMillis + oneHourInMillis);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(SIGNING_KEY)
                .compact();
    }

    // 2. Extract Username (Modern Syntax)
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 3. Extract Expiration Date (You will need this for validation!)
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 4. Functional helper to extract specific claims cleanly
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 5. Parse and validate the token signature completely
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(SIGNING_KEY) // Replacing deprecated setSigningKey()
                .build()
                .parseSignedClaims(token) // Replacing deprecated parseClaimsJws()
                .getPayload();            // Replacing deprecated getBody()
    }

    // 6. Check if the token is still usable
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 7. Validate token against incoming UserDetails
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

}


















//package com.venkatesh.main.util;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//
//@Component
//public class JWTUtility {
//    private final String SECRET_KEY = "venkatesh-secret-key-locked-12312234567865433567576453421345";
//    private final Key SIGNING_KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
//    public String generateToken(String username) {
//        long currentTimeMillis = System.currentTimeMillis();
//        long oneHourInMillis = 3600000; // 60 * 60 * 1000
//
//        Date now = new Date(currentTimeMillis);
//        Date expirationDate = new Date(currentTimeMillis + oneHourInMillis);
//
//        return Jwts.builder()
//                .subject(username)
//                .issuedAt(now)
//                .expiration(expirationDate)
//                .signWith(SIGNING_KEY) // 2. Securely sign the token
//                .compact();            // 3. Compiles everything into a single String
//    }
//
//    public String exactUsername(String token){
//        final Claims body = Jwts.parser()
//                .setSigningKey(SIGNING_KEY)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//        return body.getSubject();
//    }
//
//}
