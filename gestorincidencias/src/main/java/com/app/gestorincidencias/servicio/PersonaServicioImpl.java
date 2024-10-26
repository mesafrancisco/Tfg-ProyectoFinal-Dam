package com.app.gestorincidencias.servicio;

import com.app.gestorincidencias.entidad.Persona;
import com.app.gestorincidencias.repositorio.PersonaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Esto es para indicarle que es un servicio
public class PersonaServicioImpl implements PersonaServicio {

    @Autowired
    private PersonaRepositorio repositorio;

    @Override // Esto se sobreescribe del interface
    public List<Persona> listarTodasLasPersonas() {
        return repositorio.findAll();
    }

    @Override
    public Persona guardarPersona(Persona persona) {
        return repositorio.save(persona);
    }

    @Override
    public Persona obtenerPersonaPorId(Long id) {
        return repositorio.findById(id).get();
    }

    @Override
    public Persona actualizarPersona(Persona persona) {
        return repositorio.save(persona);
    }

    @Override
    public void eliminarPersona(Long id) {
        repositorio.deleteById(id);
    }


}
