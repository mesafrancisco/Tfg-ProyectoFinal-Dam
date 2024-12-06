package com.app.gestorincidencias.servicio;

import com.app.gestorincidencias.dto.UsuarioRegistroDTO;
import com.app.gestorincidencias.entidad.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsuarioServicio extends UserDetailsService{

    public Usuario guardar(UsuarioRegistroDTO registroDTO);

    public List<Usuario> listarUsuarios();

}