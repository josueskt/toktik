package proyect.toktick.controller;

import org.springframework.web.bind.annotation.RestController;

import proyect.toktick.base.usuarios.Usuario;
import proyect.toktick.service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//asdsadasdsa

@RestController
public class Login {
    @Autowired
    LoginService loginService;
@PostMapping("/login")
    public String  loggin_usuario(@RequestBody Usuario user){

        String correo = user.getCorreo();
        String pas = user.getPassword();
        return loginService.logear(correo, pas);
    }
    
}
