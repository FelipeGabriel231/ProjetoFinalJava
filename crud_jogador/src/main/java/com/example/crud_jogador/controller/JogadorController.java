package com.example.crud_jogador.controller;

import com.example.crud_jogador.model.Jogador;
import com.example.crud_jogador.repository.JogadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jogadores")
public class JogadorController {

    @Autowired
    private JogadorRepository jogadorRepository;

    // Adicionar um novo jogador
    @PostMapping
    public ResponseEntity<Jogador> adicionarJogador(@RequestBody Jogador jogador) {
        if (jogador.getNome() == null || jogador.getPosicao() == null || jogador.getTime() == null) {
            return ResponseEntity.badRequest().build();
        }
        Jogador novoJogador = jogadorRepository.save(jogador);
        return ResponseEntity.status(201).body(novoJogador); // Retorna 201 para criação bem-sucedida
    }

    // Listar todos os jogadores
    @GetMapping
    public ResponseEntity<List<Jogador>> listarTodosJogadores() {
        List<Jogador> jogadores = jogadorRepository.findAll();
        return ResponseEntity.ok(jogadores); // Retorna a lista de jogadores
    }

    // Buscar jogador por ID
    @GetMapping("/{id}")
    public ResponseEntity<Jogador> buscarJogadorPorId(@PathVariable Long id) {
        return jogadorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar jogador existente
    @PutMapping("/{id}")
    public ResponseEntity<Jogador> atualizarJogador(@PathVariable Long id, @RequestBody Jogador detalhesJogador) {
        return jogadorRepository.findById(id).map(jogador -> {
            jogador.setNome(detalhesJogador.getNome());
            jogador.setPosicao(detalhesJogador.getPosicao());
            jogador.setTime(detalhesJogador.getTime());
            Jogador atualizado = jogadorRepository.save(jogador);
            return ResponseEntity.ok(atualizado); // Retorna 200 com o jogador atualizado
        }).orElse(ResponseEntity.notFound().build()); // Retorna 404 se o jogador não for encontrado
    }

    // Remover jogador pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removerJogador(@PathVariable Long id) {
        return jogadorRepository.findById(id).map(jogador -> {
            jogadorRepository.delete(jogador); // Exclui o jogador
            return ResponseEntity.<Void>noContent().build(); // Retorna 204 para a exclusão bem-sucedida
        }).orElse(ResponseEntity.notFound().build()); // Retorna 404 se o jogador não for encontrado
    }
}
