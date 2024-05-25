package proyect.toktick.service;

import java.sql.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Service
public class Tokent_ge {


    public String generarToken(String sujeto, long duracionMilisegundos, String secreto) {
    // Obtenemos la fecha y hora actual
    Date ahora = new Date(duracionMilisegundos);
    
    // Calculamos la fecha y hora de expiración sumando la duración proporcionada
    Date fechaExpiracion = new Date(ahora.getTime() + duracionMilisegundos);
    
    // Generamos el token JWT utilizando el builder de jjwt
    @SuppressWarnings("deprecation")
    String token = Jwts.builder()
            .setSubject(sujeto)  // Establecemos el sujeto del token (por ejemplo, el ID del usuario)
            .setIssuedAt(ahora)  // Establecemos la fecha y hora de emisión del token
            .setExpiration(fechaExpiracion)  // Establecemos la fecha y hora de expiración del token
            .signWith(SignatureAlgorithm.HS256, secreto)  // Firmamos el token con el algoritmo HS256 y un secreto
            .compact();  // Compactamos el token en una cadena JWT
    
    return token;
}

    
}
