package com.app.gestorincidencias.controlador;

import com.app.gestorincidencias.entidad.Incidencia;
import com.app.gestorincidencias.servicio.IncidenciaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IncidenciaControlador {

    @Autowired
    private IncidenciaServicio servicio;

    @GetMapping({ "/incidencias", "/" })
    public String listarIncidencias(Model modelo, @Param("palabraClave") String palabraClave) {
        modelo.addAttribute("incidencias", servicio.listarTodasLasIncidencias(palabraClave));
        modelo.addAttribute("palabraClave", palabraClave);
        return "incidencias"; // nombre de la plantilla Thymeleaf
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
}
