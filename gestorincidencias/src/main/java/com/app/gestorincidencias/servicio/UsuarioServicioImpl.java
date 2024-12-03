package com.app.gestorincidencias.servicio;

import com.app.gestorincidencias.dto.UsuarioRegistroDTO;
import com.app.gestorincidencias.entidad.Rol;
import com.app.gestorincidencias.entidad.Usuario;
import com.app.gestorincidencias.repositorio.UsuarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UsuarioServicioImpl implements UsuarioServicio{

    private UsuarioRepositorio usuarioRepositorio;

    public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio) {
        super();
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public Usuario guardar(UsuarioRegistroDTO registroDTO) {
        Usuario usuario = new Usuario(
                registroDTO.getNombre(),
                registroDTO.getApellido(),
                registroDTO.getEmail(),
                registroDTO.getPassword(),
                Arrays.asList(new Rol("ROLE_USER"))
        );
        return usuarioRepositorio.save(usuario);
    }
}
