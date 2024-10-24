package com.app.gestorincidencias.controlador;

import com.app.gestorincidencias.repositorio.PersonaRepositorio;
import com.app.gestorincidencias.servicio.PersonaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonaControlador {

    @Autowired
    private PersonaServicio servicio;

    @GetMapping({"/personas", "/"})
    public String listarEstudiantes(Model modelo){
        modelo.addAttribute("personas", servicio.listarTodasLasPersona());
        return "personas";
    }


}
