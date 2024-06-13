package proyect.toktick.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import proyect.toktick.base.usuarios.Video;
import proyect.toktick.repository.Videorepository;
import proyect.toktick.service.Tokent_ge;


@RestController
@CrossOrigin("*")
@RequestMapping("/videos")
public class ver_videos {

    @Autowired
    Videorepository videorepository;
@Autowired
Tokent_ge tokent_ge;
    private final Path videoLocation = Paths.get("mediafiles");

    @GetMapping("")
    public Iterable<Video> allvideos() {


        return videorepository.findAll();

    }
    @GetMapping("/usuario")
    public ResponseEntity<?> getMethodName( @RequestHeader(name = "Authorization") String token) {
       String user = tokent_ge.obtenerSujetoDesdeToken(token);
       if(user != null){
        
           return ResponseEntity.ok( videorepository.findByUsuarioCorreo(user));
       }else{
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("eror");

       }
    }
    
    @CrossOrigin("*")
    @GetMapping("/{id_file:.+}")
    public ResponseEntity<Resource> getVideo(@PathVariable String id_file ) {

        Optional<Video> vide =   videorepository.findById(Long.parseLong( id_file));

        if(!vide.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        Video archivo = vide.get();
        String filename = archivo.getArchivo();


        
        // if(token != null){
            
        //     boolean asd = tokent_ge.validarToken(token);
        //     if(!asd){
        //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
                
        //     }
            
        // }
            try {

               
                System.out.println(filename );
                
                Path file = videoLocation.resolve(filename);
                Resource resource = new UrlResource(file.toUri());
    
                if (resource.exists() || resource.isReadable()) {
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                            .body(resource);
    
                } else {
                    System.out.println("entro pero no enctorn");
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }


        


      
    }
}
