package com.app.gestorincidencias.servicio;

import com.app.gestorincidencias.entidad.Incidencia;
import com.app.gestorincidencias.repositorio.IncidenciaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IncidenciaServicioImpl implements IncidenciaServicio {

    @Autowired
    private IncidenciaRepositorio repositorio;

    @Override
    public Page<Incidencia> listarTodasLasIncidencias(String palabraClave, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (palabraClave != null && !palabraClave.isEmpty()) {
            return repositorio.findAllByPalabraClave(palabraClave, pageable);
        }
        return repositorio.findAll(pageable); // Si no hay palabra clave, devuelve todas las incidencias con paginación
    }

    @Override
    public Page<Incidencia> buscarPorFiltros(String titulo, String estado, String descripcion, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repositorio.findByFiltros(titulo, estado, descripcion, pageable); // Devuelve las incidencias con filtros aplicados
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
