package proyect.toktick.repository;

import org.springframework.data.repository.CrudRepository;

import proyect.toktick.base.usuarios.Usuario;

public interface Usuaruirepository extends CrudRepository<Usuario,String> {
    

    Usuario findByCorreo(String correo);
}
