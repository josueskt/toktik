package proyect.toktick.repository;

import org.springframework.data.repository.CrudRepository;

import proyect.toktick.base.usuarios.File;

public interface Filerepository extends  CrudRepository<File,Long>  {
    
}
