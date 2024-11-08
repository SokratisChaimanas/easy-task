package gr.myprojects.easytask.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String jwtSecret = "3621ddd2af7a2850b397b87b9d985bbf3af7180556abc3a16bec7f2366c22974aaf770cd25c76085661bbc13cc68933c2131af5102bc51372325d78c6f2ddac5d8f5e7e01416dcf8f77dc5c0474095c38fdf9cbfaea86104b35891f17820ce459592014e7d2d2c386899de89f3abc4cf2644ca991a9419ee8185987a61d00ede06f15cfd8bd86a7aae6143e92e125412d579b45c43fb4b61b80a5c1591ceff931b8a9ab60d45f414ccb8b42eb13e1cb92364c0bcfc1ae69debaae3f46e3905a35476d21aed0885b5aa62a31393261f907087cb3dc9bc72c3dab1f81891c257a133da4f51527dd52218b0c2cbad232255450b62f5a2f2d585cba646ba85300242";  // You should store this in a secure place

    // Generate token after successful login
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        // Token validity (e.g., 1 hour)
        long jwtExpirationInMs = 36000000000L;
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // Extract username from the token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Validate the token (signature and expiration)
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT Signature");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT Token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT Token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT Token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty");
        }
        return false;
    }

}
