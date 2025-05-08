package com.app.gestorincidencias.repositorio;

import com.app.gestorincidencias.entidad.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, Long> {
    Rol findByNombre(String nombre);
}
