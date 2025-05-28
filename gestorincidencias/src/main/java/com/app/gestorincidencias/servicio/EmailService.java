package com.app.gestorincidencias.servicio;

public interface EmailService {
    void enviarEmail(String para, String asunto, String contenido);
}
