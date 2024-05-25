package proyect.toktick.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import proyect.toktick.service.SubirService;

@RestController
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private SubirService subirService;

@PostMapping("/upload")
public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws IOException {
    String filename = subirService.video(multipartFile);
    String url =  filename;
    return ResponseEntity.ok(url + "archivo creado");
}


@GetMapping("/{filename}")
public void getVideo(@PathVariable String filename, HttpServletResponse response) throws IOException {
    Resource resource = subirService.LoadAsResource(filename);

    if (resource.exists() || resource.isReadable()) {
        response.setContentType(MediaType.valueOf("video/mp4").toString()); 

        try (InputStream inputStream = resource.getInputStream()) { 
            IOUtils.copy(inputStream, response.getOutputStream());
        }
    } else {
        response.sendError(HttpStatus.NOT_FOUND.value(), "Video not found");
    }
}

}

