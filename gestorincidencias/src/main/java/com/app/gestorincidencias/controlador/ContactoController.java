package com.app.gestorincidencias.controlador;

import com.app.gestorincidencias.dto.ContactoForm;
import com.app.gestorincidencias.servicio.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactoController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/contacto")
    public String procesarFormulario(@ModelAttribute ContactoForm contacto) {
        String destinatario = "mesa.francisco@hotmail.com";
        String asunto = "Nuevo mensaje de contacto";
        String cuerpo = "Nombre: " + contacto.getNombre() + "\n"
                + "Correo: " + contacto.getEmail() + "\n"
                + "Mensaje:\n" + contacto.getMensaje();

        emailService.enviarEmail(destinatario, asunto, cuerpo); // Cambié enviarCorreo por enviarEmail
        return "redirect:/info";
    }
}
