package proyect.toktick.controller;

import org.springframework.web.bind.annotation.RestController;

import proyect.toktick.base.usuarios.Usuario;
import proyect.toktick.service.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class Register {
@Autowired
RegisterService registerService;

   @PostMapping("/register")
   public String Register_user(@RequestBody Usuario entity) {
       
      return  registerService.register_user(entity);
     
   }
    

}
