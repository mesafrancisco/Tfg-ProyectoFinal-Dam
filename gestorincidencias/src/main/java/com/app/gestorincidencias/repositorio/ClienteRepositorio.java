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

    @Query("SELECT c FROM Cliente c WHERE " +
            "(:nombre IS NULL OR c.nombre LIKE %:nombre%) AND " +
            "(:apellidos IS NULL OR c.apellidos LIKE %:apellidos%) AND " +
            "(:telefono IS NULL OR c.telefono LIKE %:telefono%) AND " +
            "(:email IS NULL OR c.email LIKE %:email%)")
    Page<Cliente> findByFiltros(
            @Param("nombre") String nombre,
            @Param("apellidos") String apellidos,
            @Param("telefono") String telefono,
            @Param("email") String email,
            Pageable pageable);
}
