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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import proyect.toktick.base.usuarios.Usuario;
import proyect.toktick.base.usuarios.Video;
import proyect.toktick.service.SubirService;
import proyect.toktick.service.Tokent_ge;
import proyect.toktick.service.Videoservice;

@RestController
@RequestMapping("/media")
@CrossOrigin("*")
@Api(value = "Media Management System", description = "Operations pertaining to media management in the application")
public class MediaController {
    @Autowired
    private SubirService subirService;

    @Autowired
    private Tokent_ge tokent_ge;

    @Autowired
    Videoservice videoservice;

    @ApiOperation(value = "Delete a video by ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully deleted video"),
        @ApiResponse(code = 404, message = "The video was not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVideo(@ApiParam(value = "ID of the video to be deleted", required = true) @PathVariable Long id) {
        videoservice.eliminar(id);
        return ResponseEntity.ok("eliminado");
    }

    @ApiOperation(value = "Upload a video")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully uploaded video"),
        @ApiResponse(code = 401, message = "Unauthorized to upload video"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(
            @ApiParam(value = "Video file to upload", required = true) @RequestParam("file") MultipartFile multipartFile,
            HttpServletRequest request,
            @ApiParam(value = "Metadata JSON", required = true) @RequestParam("vid") String metadataJson,
            @ApiParam(value = "Authorization token", required = true) @RequestHeader(name = "Authorization") String token) throws IOException {
        
        if (token != null) {
            boolean validToken = tokent_ge.validarToken(token);
            if (!validToken) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        }

        JSONObject jsonvideo = new JSONObject(metadataJson);
        jsonvideo.put("archivo", subirService.video(multipartFile));

        Usuario usuario = new Usuario();
        usuario.setCorreo(tokent_ge.obtenerSujetoDesdeToken(token));
        
        Video video = new Video();
        video.setTitulo(jsonvideo.optString("titulo"));
        video.setDescription(jsonvideo.optString("description"));
        video.setArchivo(jsonvideo.optString("archivo"));
        video.setUsuario(usuario);

        videoservice.crear_diceo(video);

        return ResponseEntity.ok("archivo creado");
    }

    @ApiOperation(value = "Get a video by filename")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved video"),
        @ApiResponse(code = 404, message = "The video was not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/{filename}")
    public void getVideo(@ApiParam(value = "Filename of the video to retrieve", required = true) @PathVariable String filename, HttpServletResponse response) throws IOException {
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
