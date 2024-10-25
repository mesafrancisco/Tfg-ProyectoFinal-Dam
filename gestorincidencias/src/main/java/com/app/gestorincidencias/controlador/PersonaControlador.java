package com.app.gestorincidencias.controlador;

import com.app.gestorincidencias.servicio.PersonaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonaControlador {

    @Autowired
    private PersonaServicio servicio;

    @GetMapping({"/personas"})
    public String listarPersonas(Model modelo) {
        modelo.addAttribute("personas", servicio.listarTodasLasPersonas());
        return "personas"; // nombre de la plantilla Thymeleaf
    }
}
