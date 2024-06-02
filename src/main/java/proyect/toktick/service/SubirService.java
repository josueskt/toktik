package proyect.toktick.service;

import java.io.IOException;

import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public interface SubirService {



    
    
    void init() throws IOException;

    String video(MultipartFile file);




    
    
    UrlResource LoadAsResource(String filename);

    String getVideoFilename(String videoId);

    
}
