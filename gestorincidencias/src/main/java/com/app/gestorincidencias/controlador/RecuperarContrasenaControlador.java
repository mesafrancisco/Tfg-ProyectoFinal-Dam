package com.app.gestorincidencias.controlador;

import com.app.gestorincidencias.entidad.Usuario;
import com.app.gestorincidencias.repositorio.UsuarioRepositorio;
import com.app.gestorincidencias.servicio.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
public class RecuperarContrasenaControlador {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private EmailService emailService; // la creamos luego

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/recuperar")
    public String procesarRecuperacion(@RequestParam String email, Model modelo) {
        Usuario usuario = usuarioRepositorio.findByEmail(email);

        if (usuario == null) {
            modelo.addAttribute("error", "No existe un usuario con ese email.");
            return "recuperar-password"; // página para pedir el email
        }

        // Generar token único y fecha de expiración (por ejemplo, 1 hora)
        String token = UUID.randomUUID().toString();
        LocalDateTime expiracion = LocalDateTime.now().plusHours(1);

        // Guardar token y expiración en el usuario (debes añadir estos campos en Usuario)
        usuario.setTokenRecuperacion(token);
        usuario.setTokenExpiracion(expiracion);
        usuarioRepositorio.save(usuario);

        // Enviar email con link para restablecer la contraseña
        String urlRestablecer = "http://localhost:8080/restablecer?token=" + token;
        String mensaje = "Para restablecer tu contraseña, haz clic en el siguiente enlace: " + urlRestablecer;

        emailService.enviarEmail(usuario.getEmail(), "Recuperar contraseña", mensaje);

        modelo.addAttribute("mensaje", "Se ha enviado un correo para restablecer la contraseña.");
        return "recuperar-password";
    }

    @GetMapping("/restablecer")
    public String mostrarFormularioRestablecer(@RequestParam String token, Model modelo) {
        Usuario usuario = usuarioRepositorio.findByTokenRecuperacion(token);

        if (usuario == null || usuario.getTokenExpiracion().isBefore(LocalDateTime.now())) {
            modelo.addAttribute("error", "Token inválido o expirado.");
            return "error-token";
        }

        modelo.addAttribute("token", token);
        return "restablecer-password"; // formulario para poner nueva contraseña
    }

    @PostMapping("/guardar-nueva-contrasena")
    public String guardarNueva(@RequestParam String token, @RequestParam("password") String contrasena, Model modelo) {
        Usuario usuario = usuarioRepositorio.findByTokenRecuperacion(token);

        if (usuario == null || usuario.getTokenExpiracion().isBefore(LocalDateTime.now())) {
            modelo.addAttribute("error", "Token inválido o expirado.");
            return "error-token";
        }

        // Guardar la nueva contraseña encriptada
        usuario.setPassword(passwordEncoder.encode(contrasena));

        // Limpiar token y expiración para que no se pueda usar otra vez
        usuario.setTokenRecuperacion(null);
        usuario.setTokenExpiracion(null);

        usuarioRepositorio.save(usuario);

        modelo.addAttribute("mensaje", "Contraseña restablecida correctamente.");
        return "login"; // página de login después de cambiar contraseña
    }
}
