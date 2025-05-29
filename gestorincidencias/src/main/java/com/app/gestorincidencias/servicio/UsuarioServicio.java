package com.app.gestorincidencias.servicio;

import com.app.gestorincidencias.dto.UsuarioRegistroDTO;
import com.app.gestorincidencias.entidad.Incidencia;
import com.app.gestorincidencias.entidad.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsuarioServicio extends UserDetailsService{

    public Usuario guardar(UsuarioRegistroDTO registroDTO);

    public List<Usuario> listarUsuarios();

    List<String> listarEmails();

    Usuario buscarPorEmail(String email);

    public Page<Incidencia> buscarUsuariosPorFiltros(String email, String nombre, String rol, int page, int size);

    void asignarRol(String emailUsuario, String nombreRol);

    Object filtrarUsuarios(String email, String nombre, String rol);

}