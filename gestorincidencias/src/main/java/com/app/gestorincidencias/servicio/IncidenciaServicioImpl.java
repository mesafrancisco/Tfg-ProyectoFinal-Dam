package com.app.gestorincidencias.servicio;

import com.app.gestorincidencias.entidad.Incidencia;
import com.app.gestorincidencias.repositorio.IncidenciaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidenciaServicioImpl implements IncidenciaServicio {

    @Autowired
    private IncidenciaRepositorio repositorio;

    @Override
    public List<Incidencia> listarTodasLasIncidencias(String palabraClave) {
        if (palabraClave != null && !palabraClave.isEmpty()) {
            return repositorio.findAllByPalabraClave(palabraClave);
        }
        return repositorio.findAll(); // Esto es de Jpa
    }

    @Override
    public Incidencia guardarIncidencia(Incidencia incidencia) {
        return repositorio.save(incidencia);
    }

    @Override
    public Incidencia obtenerIncidenciaPorId(Long id) {
        return repositorio.findById(id).get();
    }

    @Override
    public Incidencia actualizarIncidencia(Incidencia incidencia) {
        return repositorio.save(incidencia);
    }

    @Override
    public void eliminarIncidencia(Long id) {
        repositorio.deleteById(id);
    }
}
