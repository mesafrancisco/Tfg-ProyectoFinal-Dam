package com.app.gestorincidencias.controlador;

import com.app.gestorincidencias.entidad.Persona;
import com.app.gestorincidencias.servicio.PersonaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PersonaControlador {

    @Autowired
    private PersonaServicio servicio;

    @GetMapping({ "/personas" })
    public String listarPersonas(Model modelo) {
        modelo.addAttribute("personas", servicio.listarTodasLasPersonas());
        return "personas"; // nombre de la plantilla Thymeleaf
    }

    @GetMapping("/personas/nuevo") // nombre de la url a la que vamos a acceder
    public String mostrarFormularioDeRegistrarPersona(Model modelo) {
        Persona persona = new Persona(); // vamos a crear una persona
        modelo.addAttribute("persona", persona); // vamos a añadir una persona
        return "crear_persona"; /// nombre de la plantilla que vamos a mostrar
    }

    @PostMapping("/personas")
    public String guardarPersona(@ModelAttribute("persona") Persona persona) {
        servicio.guardarPersona(persona);
        return "redirect:personas";
    }

    @GetMapping("/personas/editar/{id}")
    public String mostrarFormularioDeEditar(@PathVariable Long id, Model modelo) {
        modelo.addAttribute("persona", servicio.obtenerPersonaPorId(id));
        return "editar_persona";
    }

    @PostMapping("/personas/{id}")
    public String actualizarPersona(@PathVariable Long id, @ModelAttribute("persona") Persona persona, Model modelo) {
        Persona personaExistente = servicio.obtenerPersonaPorId(id);
        personaExistente.setId(id);
        personaExistente.setNombre(persona.getNombre());
        personaExistente.setApellido(persona.getApellido());
        personaExistente.setEmail(persona.getEmail());

        servicio.actualizarPersona(personaExistente);
        return "redirect:/personas";
    }

    @GetMapping("/personas/{id}")
    public String eliminarPersona(@PathVariable Long id){
        servicio.eliminarPersona(id);
        return "redirect:/personas";
    }

}
