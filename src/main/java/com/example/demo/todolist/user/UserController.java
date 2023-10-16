package com.example.demo.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users") /* Indica que quando houver uma busca com o /user a classe abaixo será lida */
public class UserController {
    @Autowired // anotação para realizar o gerenicamento
    private IUserRepository userRepository;
    
    @PostMapping("/") // O metodo post vai adicionar o que retornar a função abaixo
    public ResponseEntity create(@RequestBody UserModel UserModel) {
        var user = this.userRepository.findByUsername(UserModel.getUsername());

        if (user != null) {
            System.out.println("Usuário já existe!");
            return ResponseEntity.status(400).body("Usuário já existe");
        }
        var passwordHashed = BCrypt.withDefaults().hashToString(12, UserModel.getPassword().toCharArray());

        UserModel.setPassword(passwordHashed);

        var userCreated = this.userRepository.save(UserModel);
        return ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }
}
