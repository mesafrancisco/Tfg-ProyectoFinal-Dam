package com.app.gestorincidencias.repositorio;

import com.app.gestorincidencias.entidad.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepositorio extends JpaRepository<Persona, Long> {
    @Query("SELECT p FROM Persona p WHERE " +
            "LOWER(p.nombre) LIKE LOWER(CONCAT('%', :palabraClave, '%')) OR " +
            "LOWER(p.apellido) LIKE LOWER(CONCAT('%', :palabraClave, '%')) OR " +
            "LOWER(p.email) LIKE LOWER(CONCAT('%', :palabraClave, '%'))")
    public List<Persona> findAll(String palabraClave);
}
