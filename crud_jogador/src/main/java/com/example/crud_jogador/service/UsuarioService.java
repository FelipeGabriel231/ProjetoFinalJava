package com.example.crud_jogador.service;


import com.example.crud_jogador.model.Usuario;
import com.example.crud_jogador.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Registrar um novo usuário
    public void registrarUsuario(Usuario usuario) throws Exception {
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new Exception("Usuário já existe com o username: " + usuario.getUsername());
        }
        usuarioRepository.save(usuario);
    }

    // Autenticar o usuário
    public boolean autenticarUsuario(String username, String password) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        return usuario != null && usuario.getPassword().equals(password);
    }
}
