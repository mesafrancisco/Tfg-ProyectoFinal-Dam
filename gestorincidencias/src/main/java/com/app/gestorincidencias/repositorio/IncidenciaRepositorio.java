package com.app.gestorincidencias.repositorio;

import com.app.gestorincidencias.entidad.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidenciaRepositorio extends JpaRepository<Incidencia, Long> {
    @Query("SELECT i FROM Incidencia i WHERE " +
            "LOWER(i.titulo) LIKE LOWER(CONCAT('%', :palabraClave, '%'))" +
            " OR LOWER(i.estado) LIKE LOWER(CONCAT('%', :palabraClave, '%'))" +
            " OR LOWER(i.descripcion) LIKE LOWER(CONCAT('%', :palabraClave, '%'))")
    public List<Incidencia> findAllByPalabraClave(@Param("palabraClave") String palabraClave);
}
