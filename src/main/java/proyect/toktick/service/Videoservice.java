package proyect.toktick.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyect.toktick.base.usuarios.Video;
import proyect.toktick.base.usuarios.Vista;
import proyect.toktick.repository.Videorepository;
import proyect.toktick.repository.Vistareposiroty;

@Service
public class Videoservice {
    
@Autowired
Vistareposiroty vistareposiroty;
@Autowired
Videorepository videorepository;

public String crear_diceo(Video video ){
    videorepository.save(video);

    return "creado";
}

void visita_video(Vista vista){

vistareposiroty.save(vista);


}

public void eliminar(Long id ){

    videorepository.deleteById(id);
}




}
