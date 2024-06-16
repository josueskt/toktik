package proyect.toktick.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import proyect.toktick.base.usuarios.Usuario;
import proyect.toktick.service.RegisterService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@Api(value = "User Registration System", description = "Operations pertaining to user registration in the application")
public class Register {
    
    @Autowired
    RegisterService registerService;
    
    @ApiOperation(value = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered user"),
            @ApiResponse(code = 400, message = "Invalid user details provided")
    })
    @PostMapping("/register")
    public String Register_user(@ApiParam(value = "User entity to be registered", required = true) @RequestBody Usuario entity) {
        return registerService.register_user(entity);
    }
}
