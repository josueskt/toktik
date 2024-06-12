package proyect.toktick.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import proyect.toktick.base.usuarios.Video;


public interface Videorepository extends CrudRepository<Video,Long>   {


 //List<Video> findByUsuarioCorreo(Long usuarioId);
    
    
}
