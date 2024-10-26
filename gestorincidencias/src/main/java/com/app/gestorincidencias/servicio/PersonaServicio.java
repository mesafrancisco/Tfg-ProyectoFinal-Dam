package com.app.gestorincidencias.servicio;

import com.app.gestorincidencias.entidad.Persona;

import java.util.List;

public interface PersonaServicio {
    public List<Persona> listarTodasLasPersonas();

    public Persona guardarPersona(Persona persona);

    public Persona obtenerPersonaPorId(Long id);

    public Persona actualizarPersona(Persona persona);

    public void eliminarPersona(Long id);
}

