package com.app.gestorincidencias.servicio;

import com.app.gestorincidencias.dto.UsuarioRegistroDTO;
import com.app.gestorincidencias.entidad.Incidencia;
import com.app.gestorincidencias.entidad.Rol;
import com.app.gestorincidencias.entidad.Usuario;
import com.app.gestorincidencias.repositorio.RolRepositorio;
import com.app.gestorincidencias.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {


    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RolRepositorio rolRepositorio;

    public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio) {
        super();
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public List<Usuario> filtrarUsuarios(String email, String nombre, String rol) {
        List<Usuario> usuarios = usuarioRepositorio.findAll(); // o algo más optimizado si usas JPA Criteria/QueryDSL

        if (email != null && !email.isEmpty()) {
            usuarios = usuarios.stream()
                    .filter(u -> u.getEmail().toLowerCase().contains(email.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (nombre != null && !nombre.isEmpty()) {
            usuarios = usuarios.stream()
                    .filter(u -> (u.getNombre() + " " + u.getApellido()).toLowerCase().contains(nombre.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (rol != null && !rol.isEmpty()) {
            usuarios = usuarios.stream()
                    .filter(u -> u.getRoles().stream().anyMatch(r -> r.getNombre().equals(rol)))
                    .collect(Collectors.toList());
        }

        return usuarios;
    }

    @Override
    public Usuario guardar(UsuarioRegistroDTO registroDTO) {
        Rol rolUsuario = rolRepositorio.findByNombre("ROLE_USER");
        if (rolUsuario == null) {
            throw new RuntimeException("El rol ROLE_USER no existe en la base de datos");
        }

        Usuario usuario = new Usuario(
                registroDTO.getNombre(),
                registroDTO.getApellido(),
                registroDTO.getEmail(),
                passwordEncoder.encode(registroDTO.getPassword()),
                List.of(rolUsuario) // Usamos el rol existente
        );

        return usuarioRepositorio.save(usuario);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(username);
        if(usuario == null) {
            throw new UsernameNotFoundException("Usuario o password inválidos");
        }
        return new User(usuario.getEmail(),usuario.getPassword(), mapearAutoridadesRoles(usuario.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    @Override
    public List<String> listarEmails() {
        // Este método recupera solo los correos electrónicos de todos los usuarios
        return usuarioRepositorio.findAll().stream()
                .map(Usuario::getEmail)
                .collect(Collectors.toList());
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return usuarioRepositorio.findByEmail(email);
    }

    @Override
    public Page<Incidencia> buscarUsuariosPorFiltros(String email, String nombre, String rol, int page, int size) {
        return null;
    }

    @Override
    public void asignarRol(String emailUsuario, String nombreRol) {
        Usuario usuario = usuarioRepositorio.findByEmail(emailUsuario);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + emailUsuario);
        }

        Rol nuevoRol = rolRepositorio.findByNombre(nombreRol);
        if (nuevoRol == null) {
            throw new IllegalArgumentException("Rol no existe: " + nombreRol);
        }

        // Reemplazar todos los roles anteriores con el nuevo rol
        usuario.setRoles(new ArrayList<>(List.of(nuevoRol)));
        usuarioRepositorio.save(usuario);
    }
}