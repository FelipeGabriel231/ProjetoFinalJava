package com.example.crud_jogador;


import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrudJogadorApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrudJogadorApplication.class, args);

        // Inicia a aplicação JavaFX
        Application.launch(LoginApp.class, args); // Tela de Login
        Application.launch(JavaFXApp.class, args); //Tela de Jogadores





        System.out.println("Api rest funcionando  http://localhost:8080/api/jogadores  ");
        System.out.println("Crud Funcionando");

    }
}