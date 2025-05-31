package com.app.gestorincidencias.repositorio;

import com.app.gestorincidencias.entidad.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c " +
            "WHERE (:nombre IS NULL OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
            "AND (:apellidos IS NULL OR LOWER(c.apellidos) LIKE LOWER(CONCAT('%', :apellidos, '%'))) " +
            "AND (:telefono IS NULL OR c.telefono = :telefono) " +
            "AND (:email IS NULL OR LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%')))")
    Page<Cliente> buscarPorFiltros(
            @Param("nombre") String nombre,
            @Param("apellidos") String apellidos,
            @Param("telefono") Long  telefono,
            @Param("email") String email,
            Pageable pageable);

}
