package proyect.toktick.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import proyect.toktick.base.usuarios.Secion;
import proyect.toktick.base.usuarios.Usuario;
import proyect.toktick.repository.Secionrepository;
import proyect.toktick.repository.Usuaruirepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    Usuaruirepository usuaruirepository;
    @Autowired
    Tokent_ge tookent_ge;
    @Autowired
    Secionrepository secionrepository;

    public ResponseEntity<?> logear(String correo, String pass) {
        // Buscamos al usuario por su correo electrónico en la base de datos
        Usuario usuario = usuaruirepository.findByCorreo(correo);

        if (usuario != null) {
            try {
                // hasheo
                String hashedPassword = hashPassword(pass);

                
                if (hashedPassword.equals(usuario.getPassword())) {

                    Secion secion = new Secion();
                    secion.setUsuario(usuario);
                    secionrepository.save(secion);
                   
                    return ResponseEntity.ok( tookent_ge.generarToken(usuario.getCorreo(),30000000 ));
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "contraseña incorrecta"));

                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "usuario no existente"));

        }
    }
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        // Creamos una instancia de MessageDigest con el algoritmo SHA-256
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // Convertimos la contraseña a un arreglo de bytes
        byte[] encodedHash = digest.digest(password.getBytes());

        // Convertimos el arreglo de bytes a una representación en hexadecimal
        StringBuilder hexString = new StringBuilder();
        for (byte b : encodedHash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
 
}
