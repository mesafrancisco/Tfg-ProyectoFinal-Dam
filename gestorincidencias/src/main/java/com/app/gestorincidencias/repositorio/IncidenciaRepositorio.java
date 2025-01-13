package com.app.gestorincidencias.repositorio;

import com.app.gestorincidencias.entidad.Incidencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidenciaRepositorio extends JpaRepository<Incidencia, Long> {

    // Método para buscar incidencias con paginación y filtros
    @Query("SELECT i FROM Incidencia i WHERE " +
            "(:titulo IS NULL OR LOWER(i.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))) AND " +
            "(:estado IS NULL OR LOWER(i.estado) LIKE LOWER(CONCAT('%', :estado, '%'))) AND " +
            "(:descripcion IS NULL OR LOWER(i.descripcion) LIKE LOWER(CONCAT('%', :descripcion, '%'))) ")
    Page<Incidencia> findByFiltros(
            @Param("titulo") String titulo,
            @Param("estado") String estado,
            @Param("descripcion") String descripcion,
            Pageable pageable);

    // Método para buscar incidencias por palabra clave
    @Query("SELECT i FROM Incidencia i WHERE " +
            "(:palabraClave IS NULL OR LOWER(i.titulo) LIKE LOWER(CONCAT('%', :palabraClave, '%'))) OR " +
            "(:palabraClave IS NULL OR LOWER(i.estado) LIKE LOWER(CONCAT('%', :palabraClave, '%'))) OR " +
            "(:palabraClave IS NULL OR LOWER(i.descripcion) LIKE LOWER(CONCAT('%', :palabraClave, '%'))) ")
    Page<Incidencia> findAllByPalabraClave(@Param("palabraClave") String palabraClave, Pageable pageable);
}
