package com.app.gestorincidencias.controlador;

import com.app.gestorincidencias.repositorio.RolRepositorio;
import com.app.gestorincidencias.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private RolRepositorio rolRepositorio;

    @GetMapping("/usuarios")
    public String listarUsuarios(Model modelo) {
        modelo.addAttribute("usuarios", usuarioServicio.listarUsuarios());
        modelo.addAttribute("roles", rolRepositorio.findAll());
        return "usuarios";
    }

    @GetMapping("/usuarios/asignar-rol")
    public String asignarRolFormulario(Model modelo) {
        modelo.addAttribute("usuarios", usuarioServicio.listarUsuarios());
        modelo.addAttribute("roles", rolRepositorio.findAll());
        return "usuarios";
    }

    @PostMapping("/usuarios/asignar-rol")
    public String asignarRol(@RequestParam String emailUsuario, @RequestParam String rol) {
        usuarioServicio.asignarRol(emailUsuario, rol);
        return "redirect:/usuarios";
    }
}

