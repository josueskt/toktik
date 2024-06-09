package proyect.toktick.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import proyect.toktick.base.usuarios.Usuario;
import proyect.toktick.base.usuarios.Video;
import proyect.toktick.service.SubirService;
import proyect.toktick.service.Tokent_ge;
import proyect.toktick.service.Videoservice;

@RestController
@RequestMapping("/media")
public class MediaController {
    @Autowired
    private SubirService subirService;
    @Autowired
    private Tokent_ge tokent_ge;
    @Autowired
    Videoservice videoservice;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(
            @RequestParam("file") MultipartFile multipartFile,
            HttpServletRequest request,
            @RequestParam("vid") String metadataJson,
            @RequestHeader(name = "Authorization") String token) throws IOException {

        if (token != null) {

            boolean asd = tokent_ge.validarToken(token);

            if (!asd) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

            }

        }
        JSONObject jsonvideo = new JSONObject(metadataJson);
        jsonvideo.put("archivo", subirService.video(multipartFile));

        Usuario usuario = new Usuario();
        usuario.setCorreo(tokent_ge.obtenerSujetoDesdeToken(token));
       System.out.println("usuario"+ tokent_ge.obtenerSujetoDesdeToken(token));
        System.out.println(usuario);
        // Crear un objeto Video a partir de los datos del JSON
        Video video = new Video();
        video.setTitulo(jsonvideo.optString("titulo")); // Ajusta este método según la estructura de tu JSON
        video.setDescription(jsonvideo.optString("description")); // Ajusta este método según la estructura de tu JSON
        video.setArchivo(jsonvideo.optString("archivo")); // Ajusta este método según la estructura de tu JSON

        video.setUsuario(usuario);

        videoservice.crear_diceo(video);

        return ResponseEntity.ok("archivo creado");
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
