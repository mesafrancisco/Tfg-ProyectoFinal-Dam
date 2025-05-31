package com.app.gestorincidencias.controlador;

import com.app.gestorincidencias.entidad.Incidencia;
import com.app.gestorincidencias.entidad.Usuario;
import com.app.gestorincidencias.servicio.IncidenciaServicio;
import com.app.gestorincidencias.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                                    @RequestParam(value = "fechaInicio", required = false) String fechaInicioStr,
                                    @RequestParam(value = "fechaFin", required = false) String fechaFinStr,
                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size) {

        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE; // "yyyy-MM-dd"

        try {
            if (fechaInicioStr != null && !fechaInicioStr.isEmpty()) {
                fechaInicio = LocalDate.parse(fechaInicioStr, formatter);
            }
            if (fechaFinStr != null && !fechaFinStr.isEmpty()) {
                fechaFin = LocalDate.parse(fechaFinStr, formatter);
            }
        } catch (DateTimeParseException e) {
            // Opcional: agregar mensaje de error en el modelo para mostrar en la vista
            modelo.addAttribute("errorFecha", "Formato de fecha inválido. Use yyyy-MM-dd.");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(rol -> rol.equals("ROLE_ADMIN"));

        Page<Incidencia> incidenciasPage;

        if ((titulo != null && !titulo.isEmpty()) ||
                (estado != null && !estado.isEmpty()) ||
                (descripcion != null && !descripcion.isEmpty()) ||
                (fechaInicio != null) ||
                (fechaFin != null) ||
                (palabraClave != null && !palabraClave.isEmpty())) {

            if (isAdmin) {
                incidenciasPage = servicio.buscarPorFiltrosCompleto(titulo, estado, descripcion, fechaInicioStr, fechaFinStr, page, size);
            } else {
                incidenciasPage = servicio.listarIncidenciasPorUsuarioYFiltros(email, titulo, estado, descripcion, fechaInicio, fechaFin, palabraClave, page, size);
            }

        } else {
            if (isAdmin) {
                incidenciasPage = servicio.listarTodasLasIncidencias("", page, size);
            } else {
                incidenciasPage = servicio.listarIncidenciasPorUsuario(email, page, size);
            }
        }

        modelo.addAttribute("incidencias", incidenciasPage.getContent());
        modelo.addAttribute("palabraClave", palabraClave);
        modelo.addAttribute("currentPage", page);
        modelo.addAttribute("totalPages", incidenciasPage.getTotalPages());
        modelo.addAttribute("size", size);
        modelo.addAttribute("fechaInicio", fechaInicioStr);
        modelo.addAttribute("fechaFin", fechaFinStr);
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

    //Calendario
    @GetMapping("/incidencias/calendario")
    public String verCalendario() {
        return "calendario";
    }

    @GetMapping("/api/incidencias/calendario")
    @ResponseBody
    public List<Map<String, Object>> obtenerIncidenciasParaCalendario() {
        List<Incidencia> incidencias = servicio.listarTodas();
        return incidencias.stream().map(i -> {
            Map<String, Object> evento = new HashMap<>();
            evento.put("title", i.getTitulo());
            evento.put("start", i.getFechaCreacion()); // debe estar en formato ISO (ej: 2025-05-29)
            evento.put("url", "/incidencias/editar/" + i.getId()); // opcional: clic lleva a editar
            return evento;
        }).collect(Collectors.toList());
    }


}
