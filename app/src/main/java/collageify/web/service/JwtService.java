package collageify.web.service;

import collageify.web.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class JwtService {
    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims,username);
    }
    private String createToken(Map<String, Object> claims, String username){
        Date now = new Date(System.currentTimeMillis());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(JwtUtils.getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(JwtUtils.getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = extractExpirationDate(token);
        return expirationDate.before(new Date());
    }

    private Date extractExpirationDate(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(JwtUtils.getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }
}
