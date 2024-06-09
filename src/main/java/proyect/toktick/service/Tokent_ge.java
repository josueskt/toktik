package proyect.toktick.service;

import java.util.Date; // Importar la clase Date correcta
import java.security.Key;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class Tokent_ge {
    private static final String SECRETO = "YjNjNzY5NTJhMjNlZDA2NjhiMmUzNDMyY2I2M2UwMDJlMDA0MmU2Yw"; // 256-bit base64 string

    private Key getSigningKey() {
        byte[] keyBytes = SECRETO.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String obtenerSujetoDesdeToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRETO.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println( claims);
            return claims.getSubject();
        } catch (Exception e) {
            System.err.println("Error al obtener el sujeto del token: " + e.getMessage());
            return null;
        }
    }





    public String generarToken(String sujeto,long duracionMilisegundos) {
        long ahoraMillis = System.currentTimeMillis();
        Date ahora = new Date(ahoraMillis);
        Date fechaExpiracion = new Date(ahoraMillis + duracionMilisegundos);
    
        return Jwts.builder()
                .setSubject(sujeto)
                .setIssuedAt(ahora)
                .setExpiration(fechaExpiracion)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public boolean validarToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            System.out.println("Claims: " + claims);
            return true;
        } catch (Exception e) {
            System.err.println("Token validation error: " + e.getMessage());
            return false;
        }
    }
}
