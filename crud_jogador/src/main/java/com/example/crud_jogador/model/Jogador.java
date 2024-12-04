package com.example.crud_jogador.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Map;

@Entity
public class Jogador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String posicao;
    private String time;
    private int golsMarcados;
    private int partidasJogadas;
    private int assistencias;

    // Construtor vazio necessário para JPA
    public Jogador() {}

    // Construtor que aceita um Map
    public Jogador(Map<String, Object> map) {
        this.nome = (String) map.get("nome");
        this.posicao = (String) map.get("posicao");
        this.time = (String) map.get("time");
        this.golsMarcados = ((Number) map.get("golsMarcados")).intValue();
        this.partidasJogadas = ((Number) map.get("partidasJogadas")).intValue();
        this.assistencias = ((Number) map.get("assistencias")).intValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getGolsMarcados() {
        return golsMarcados;
    }

    public void setGolsMarcados(int golsMarcados) {
        this.golsMarcados = golsMarcados;
    }

    public int getPartidasJogadas() {
        return partidasJogadas;
    }

    public void setPartidasJogadas(int partidasJogadas) {
        this.partidasJogadas = partidasJogadas;
    }

    public int getAssistencias() {
        return assistencias;
    }

    public void setAssistencias(int assistencias) {
        this.assistencias = assistencias;
    }
}


