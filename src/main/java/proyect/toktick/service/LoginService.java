package proyect.toktick.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyect.toktick.base.usuarios.Usuario;
import proyect.toktick.repository.Usuaruirepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class LoginService {

    @Autowired
    Usuaruirepository usuaruirepository;
    @Autowired
    Tokent_ge tookent_ge;

    public String logear(String correo, String pass) {
        // Buscamos al usuario por su correo electrónico en la base de datos
        Usuario usuario = usuaruirepository.findByCorreo(correo);

        if (usuario != null) {
            try {
                // hasheo
                String hashedPassword = hashPassword(pass);

                
                if (hashedPassword.equals(usuario.getPassword())) {

                    return tookent_ge.generarToken(usuario.getCorreo(),3000,"YjNjNzY5NTJhMjNlZDA2NjhiMmUzNDMyY2I2M2UwMDJlMDA0MmU2Yw" );
                } else {
                    return "contraseña incorrecta ";
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return "ususario no existente ";
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
