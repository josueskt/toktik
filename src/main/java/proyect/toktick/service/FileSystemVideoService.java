package proyect.toktick.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@Service
public class FileSystemVideoService implements SubirService {

    @Value("${media.location}")
    private String mediaLocation;

    private Path rootLocation;

    @Override
    @PostConstruct
    public void init() throws IOException {
        rootLocation= Paths.get(mediaLocation);
        Files.createDirectories(rootLocation);


    }


    @Override
    public String video(MultipartFile file) {
        try{
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to video empty file");
                
            }
            String filename = file.getOriginalFilename();
            String extension = filename != null ? filename.substring(filename.lastIndexOf(".")) : "";
            String uniqueFilename = UUID.randomUUID().toString() + extension;




            Path destinatinationFile = rootLocation.resolve(Paths.get(uniqueFilename)).normalize().toAbsolutePath();

            try(InputStream inputStream= file.getInputStream()){
                Files.copy(inputStream, destinatinationFile, StandardCopyOption.REPLACE_EXISTING);
    
            }
            return uniqueFilename;
        }catch(IOException e){
            throw new RuntimeException("Failed error ",e);
        }
        

        
    }

    @Override
    public UrlResource LoadAsResource(String filename) {
       try{
        Path file = rootLocation.resolve(filename);
        UrlResource resource = new UrlResource((file.toUri()));

        if (resource.exists()||resource.isReadable()) {
            return resource;
        }else{
            throw new RuntimeException("Could not read file"+filename);
        }
       }catch (MalformedURLException e){
        throw new RuntimeException("Could not read file"+filename);

       }
    }


    @Override
    public String getVideoFilename(String videoId) {
        throw new UnsupportedOperationException("Unimplemented method 'getVideoFilename'");
    }

    
}
