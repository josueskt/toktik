package proyect.toktick.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "Video Management System", description = "Operations pertaining to video management in the application")
public class ver_videos {

    @Autowired
    Videorepository videorepository;
    
    @Autowired
    Tokent_ge tokent_ge;
    
    private final Path videoLocation = Paths.get("mediafiles");

    @ApiOperation(value = "View a list of available videos", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("")
    public Iterable<Video> allvideos() {
        return videorepository.findAll();
    }

    @ApiOperation(value = "Get videos for a specific user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user videos"),
            @ApiResponse(code = 400, message = "Invalid token provided")
    })
    @GetMapping("/usuario")
    public ResponseEntity<?> getMethodName(@ApiParam(value = "Authorization token", required = true) @RequestHeader(name = "Authorization") String token) {
        String user = tokent_ge.obtenerSujetoDesdeToken(token);
        if (user != null) {
            return ResponseEntity.ok(videorepository.findByUsuarioCorreo(user));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
        }
    }

    @ApiOperation(value = "Get a video by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved video"),
            @ApiResponse(code = 404, message = "The video was not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @CrossOrigin("*")
    @GetMapping("/{id_file:.+}")
    public ResponseEntity<Resource> getVideo(@ApiParam(value = "ID of the video file", required = true) @PathVariable String id_file) {
        Optional<Video> vide = videorepository.findById(Long.parseLong(id_file));

        if (!vide.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Video archivo = vide.get();
        String filename = archivo.getArchivo();

        try {
            Path file = videoLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
