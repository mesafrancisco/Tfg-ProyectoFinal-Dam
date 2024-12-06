package com.app.gestorincidencias.repositorio;

import com.app.gestorincidencias.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{

    public Usuario findByEmail(String email);

}
