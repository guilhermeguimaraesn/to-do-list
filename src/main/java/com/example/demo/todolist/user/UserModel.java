package com.example.demo.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data //marcador para realizar o getter e setters das variaveis de instancia da classe abaixo
@Entity(name = "tb_users") // Entity para transformar a classe marcada em uma entidade, onde as instancias dessa classe serão armazenadas em uma tabela com o nome de 'tb_users'
public class UserModel {

    @Id // anotação para indicar que a instancia abaixo é um id
    @GeneratedValue(generator = "UUID") // anotação para automatizar a geração do id
    private UUID id;

    @Column(unique = true) //atribui que na colina 'username' cada username deverá ser unico
    private String username;
    private String name;
    private String password;

    @CreationTimestamp // anotação para gravar a data e o horário que o usuário foi criado
    private LocalDateTime createAt;



    // metodo para atribuir uma valor a variável username através do SET
    // public void setUsername(String username) {
    //     this.username = username; // 'this.username' se refere a variável de instancia username e o 'username' se refere ao username passado no parametro do metodo
    // }

    // metodo para obter o valor que JA ESTA na variável através do GET
    // public String getUsername() {
    //     return username; 
    // }

    // public void setName(String name) {
    //     this.name = name;
    // }
    
    // public String getName() {
    //     return name;
    // }

    // public void setPassword(String password) {
    //     this.password = password;
    // }
    
    // public String getPassword() {
    //     return password;
    // }

    
}
