package com.app.gestorincidencias.controlador;

import com.app.gestorincidencias.entidad.Incidencia;
import com.app.gestorincidencias.entidad.Usuario;
import com.app.gestorincidencias.servicio.IncidenciaServicio;
import com.app.gestorincidencias.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;


import java.util.List;

@Controller
public class IncidenciaControlador {

    @Autowired
    private IncidenciaServicio servicio;

    @Autowired
    private UsuarioServicio usuarioServicio;  // Inyectamos el UsuarioServicio

    @GetMapping({ "/incidencias", "/" })
    public String listarIncidencias(Model modelo,
                                    @RequestParam(value = "palabraClave", required = false) String palabraClave,
                                    @RequestParam(value = "titulo", required = false) String titulo,
                                    @RequestParam(value = "estado", required = false) String estado,
                                    @RequestParam(value = "descripcion", required = false) String descripcion,
                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Incidencia> incidenciasPage;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(rol -> rol.equals("ROLE_ADMIN"));

        if (palabraClave != null && !palabraClave.isEmpty()) {
            incidenciasPage = isAdmin
                    ? servicio.listarTodasLasIncidencias(palabraClave, page, size)
                    : servicio.listarIncidenciasPorUsuario(email, page, size); // O puedes filtrar también aquí por palabra clave si implementas ese método
        } else if (titulo != null || estado != null || descripcion != null) {
            incidenciasPage = isAdmin
                    ? servicio.buscarPorFiltros(titulo, estado, descripcion, page, size)
                    : servicio.listarIncidenciasPorUsuario(email, page, size);
        } else {
            incidenciasPage = isAdmin
                    ? servicio.listarTodasLasIncidencias("", page, size)
                    : servicio.listarIncidenciasPorUsuario(email, page, size);
        }

        modelo.addAttribute("incidencias", incidenciasPage.getContent());
        modelo.addAttribute("palabraClave", palabraClave);
        modelo.addAttribute("currentPage", page);
        modelo.addAttribute("totalPages", incidenciasPage.getTotalPages());
        modelo.addAttribute("size", size);
        modelo.addAttribute("usuarios", usuarioServicio.listarUsuarios());

        return "incidencias";
    }

    @GetMapping("/incidencias/nuevo")
    public String mostrarFormularioDeRegistrarIncidencia(Model modelo) {
        Incidencia incidencia = new Incidencia();
        modelo.addAttribute("incidencia", incidencia);
        return "crear_incidencia"; // nombre de la plantilla que vamos a mostrar
    }

    @PostMapping("/incidencias")
    public String guardarIncidencia(@ModelAttribute("incidencia") Incidencia incidencia) {
        servicio.guardarIncidencia(incidencia);
        return "redirect:/incidencias";
    }

    @GetMapping("/incidencias/editar/{id}")
    public String mostrarFormularioDeEditar(@PathVariable Long id, Model modelo) {
        modelo.addAttribute("incidencia", servicio.obtenerIncidenciaPorId(id));
        return "editar_incidencia";
    }

    @PostMapping("/incidencias/{id}")
    public String actualizarIncidencia(@PathVariable Long id, @ModelAttribute("incidencia") Incidencia incidencia, Model modelo) {
        Incidencia incidenciaExistente = servicio.obtenerIncidenciaPorId(id);
        incidenciaExistente.setId(id);
        incidenciaExistente.setTitulo(incidencia.getTitulo());
        incidenciaExistente.setDescripcion(incidencia.getDescripcion());
        incidenciaExistente.setEstado(incidencia.getEstado());
        incidenciaExistente.setAsignadoA(incidencia.getAsignadoA());
        incidenciaExistente.setPrioridad(incidencia.getPrioridad());
        incidenciaExistente.setFechaCreacion(incidencia.getFechaCreacion());
        incidenciaExistente.setFechaResolucion(incidencia.getFechaResolucion());
        servicio.actualizarIncidencia(incidenciaExistente);
        return "redirect:/incidencias";
    }

    @GetMapping("/incidencias/{id}")
    public String eliminarIncidencia(@PathVariable Long id) {
        servicio.eliminarIncidencia(id);
        return "redirect:/incidencias";
    }
    @GetMapping("/emails")
    public String mostrarCorreosElectronicos(Model modelo) {
        // Obtener todos los correos electrónicos
        List<String> emails = usuarioServicio.listarEmails();
        modelo.addAttribute("emails", emails);
        return "mostrar_emails";  // Nombre de la plantilla que mostraría los correos electrónicos
    }
    @GetMapping("/info")
    public String mostrarInfo() {
        return "info"; // Sin extensión, porque Spring busca en templates/
    }



    @GetMapping("/incidencias/asignar/{id}")
    public String mostrarFormularioDeAsignacion(@PathVariable Long id, Model modelo) {
        Incidencia incidencia = servicio.obtenerIncidenciaPorId(id);
        List<Usuario> usuarios = usuarioServicio.listarUsuarios(); // Lista de usuarios
        modelo.addAttribute("incidencia", incidencia);
        modelo.addAttribute("usuarios", usuarios);
        return "asignar_incidencia"; // Nombre de la vista donde se mostrará el formulario
    }

    @PostMapping("/incidencias/asignar/{id}")
    public String asignarIncidencia(@PathVariable Long id, @RequestParam String correoUsuario) {
        // Obtener la incidencia
        Incidencia incidencia = servicio.obtenerIncidenciaPorId(id);

        // Obtener el usuario por su correo electrónico
        Usuario usuario = usuarioServicio.buscarPorEmail(correoUsuario);

        // Asignar el usuario a la incidencia
        incidencia.setAsignadoA(usuario.getEmail());

        // Guardar los cambios
        servicio.actualizarIncidencia(incidencia);

        return "redirect:/incidencias";  // Redirigir a la lista de incidencias
    }
}
