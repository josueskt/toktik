package proyect.toktick.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyect.toktick.base.usuarios.Usuario;
import proyect.toktick.repository.Usuaruirepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class RegisterService {

    @Autowired
    Usuaruirepository usuaruirepository;

    public String register_user(Usuario entity) {
        try {
            String password = entity.getPassword();

            
            String hashedPassword = hashPassword(password);

           
            entity.setPassword(hashedPassword);

            usuaruirepository.save(entity);

            return "Usuario creado correctamente";
        } catch (Exception e) {
            return "Error al crear el usuario: " + e.getMessage();
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
