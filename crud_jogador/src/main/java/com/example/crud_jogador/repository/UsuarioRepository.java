package com.example.crud_jogador.repository;


import com.example.crud_jogador.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);

    boolean existsByUsername(String username);
}
