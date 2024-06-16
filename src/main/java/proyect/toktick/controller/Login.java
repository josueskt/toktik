package proyect.toktick.controller;

import org.springframework.web.bind.annotation.RestController;

import proyect.toktick.base.usuarios.Usuario;
import proyect.toktick.service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin("*")
@Api(value = "User Login System", description = "Operations pertaining to user login in the application")
public class Login {

    @Autowired
    LoginService loginService;

    @ApiOperation(value = "Login a user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully logged in"),
        @ApiResponse(code = 401, message = "Invalid email or password"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping("/login")
    public ResponseEntity<?> loggin_usuario(
        @ApiParam(value = "User login details", required = true) @RequestBody Usuario user){

        String correo = user.getCorreo();
        String pas = user.getPassword();
        return loginService.logear(correo, pas);
    }
}
