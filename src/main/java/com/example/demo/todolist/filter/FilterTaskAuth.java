package com.example.demo.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.todolist.user.IUserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();
        // verifica se a rota é igual a '/tasks/' 
        if (servletPath.startsWith("/tasks/")) {
            // Obter a altenticação (usuário e senha)
            var authorization = request.getHeader("Authorization");

            // obter o tamanho do texto basic no retorno da authorization e o remove
            var user_password = authorization.substring("Basic".length()).trim();

            // convertendo em bytes
            byte[] authDecode = Base64.getDecoder().decode(user_password);

            // convertendo de bytes para string
            var authString = new String(authDecode);

            // quebrando a string authString e guadando em um array com user name na posição
            // 0 e password na possição 1
            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            System.out.println("Usuário: " + username);
            System.out.println("Senha: " + password);

            // validando o usuario
            var user = this.userRepository.findByUsername(username);

            //verifica se o campo de usuario está vazio, caso esteja retorna erro 401
            if (user == null) {
                response.sendError(401);
                System.out.println("Preencha o usuário corretamente!");
            } else {
                //verificando se a senha fornecida pelo usuário é a mesma cadastrada 
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                
                //caso a resposta da verificação seja verdadeira o codigo seguirá seu fluxo
                if (passwordVerify.verified) {
                    // setando o id do usuário
                    request.setAttribute("idUser", user.getId());
                   
                    filterChain.doFilter(request, response);
                    System.out.println("Tarefa salva com sucesso");
                } //caso seja falso mostrará uma mensagem de erro
                    else {
                    response.sendError(401);
                    System.out.println("Senha incorreta");
                }

            }
            // fora da rota '/tasks/' segur o fluxo da aplicação
        } else {
           filterChain.doFilter(request, response);
        }

    }

}
