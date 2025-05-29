package com.app.gestorincidencias.repositorio;

import com.app.gestorincidencias.entidad.Incidencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidenciaRepositorio extends JpaRepository<Incidencia, Long>, JpaSpecificationExecutor<Incidencia> {

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

    Page<Incidencia> findByAsignadoA(String email, Pageable pageable);

    @Query("SELECT i.estado AS estado, COUNT(i) AS total FROM Incidencia i WHERE "
            + "(:titulo IS NULL OR LOWER(i.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))) AND "
            + "(:estado IS NULL OR i.estado = :estado) AND "
            + "(:descripcion IS NULL OR LOWER(i.descripcion) LIKE LOWER(CONCAT('%', :descripcion, '%'))) AND "
            + "(:palabraClave IS NULL OR (LOWER(i.titulo) LIKE LOWER(CONCAT('%', :palabraClave, '%')) OR LOWER(i.descripcion) LIKE LOWER(CONCAT('%', :palabraClave, '%')))) "
            + "GROUP BY i.estado")
    List<Object[]> contarPorEstado(@Param("titulo") String titulo, @Param("estado") String estado,
                                   @Param("descripcion") String descripcion, @Param("palabraClave") String palabraClave);

}
