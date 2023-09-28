package tn.luceor.demo99.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tn.luceor.demo99.entities.Role;


import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {

    private String secret = "ZpjsvgdjPdCFhlbz84jbg!QjztyMKJUD?12577";




    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Date extractExpiration(String token){
        return extractClaims(token,Claims::getExpiration);
    }

    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }


    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username, Role role, Long userId){
        Map<String,Object> claims = new HashMap<>();
        claims.put("role",role);
        claims.put("userId",userId);
        return createToken(claims,username);
    }

    private String createToken(Map<String,Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256,secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Long extractAdminIdFromToken(String token) {
        Claims claims = extractAllClaims(token);
        Object adminIdObject = claims.get("userId"); // Assuming "userId" is the key for the admin ID in your claims

        if (adminIdObject != null) {
            return Long.parseLong(adminIdObject.toString());
        }

        return null;
    }

    public Long extractUserIdFromToken(String token) {
        Claims claims = extractAllClaims(token);
        Object userIdObject = claims.get("userId"); // Assuming "userId" is the key for the user ID in your claims

        if (userIdObject != null) {
            return Long.parseLong(userIdObject.toString());
        }

        return null;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Extract the token without the "Bearer " prefix
        }

        return null;
    }

}
