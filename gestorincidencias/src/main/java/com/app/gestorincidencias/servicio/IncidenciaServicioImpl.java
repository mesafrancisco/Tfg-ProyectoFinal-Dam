package com.app.gestorincidencias.servicio;

import com.app.gestorincidencias.entidad.Incidencia;
import com.app.gestorincidencias.repositorio.IncidenciaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IncidenciaServicioImpl implements IncidenciaServicio {

    @Autowired
    private IncidenciaRepositorio repositorio;

    @Override
    public Page<Incidencia> listarTodasLasIncidencias(String palabraClave, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (palabraClave != null && !palabraClave.isEmpty()) {
            return
                    repositorio.findAllByPalabraClave(palabraClave, pageable);
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

    @Override
    public Page<Incidencia> listarIncidenciasPorUsuario(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repositorio.findByAsignadoA(email, pageable);
    }

    @Override
    public List<Incidencia> listarTodas() {
        return repositorio.findAll();
    }

    @Override
    public Map<String, Long> contarIncidenciasPorEstado(String titulo, String estado, String descripcion, String palabraClave) {
        // Si todos los filtros están vacíos, pasar null para que la consulta no filtre
        if ((titulo == null || titulo.isEmpty()) &&
                (estado == null || estado.isEmpty()) &&
                (descripcion == null || descripcion.isEmpty()) &&
                (palabraClave == null || palabraClave.isEmpty())) {
            titulo = null;
            estado = null;
            descripcion = null;
            palabraClave = null;
        }

        List<Object[]> resultados = repositorio.contarPorEstado(titulo, estado, descripcion, palabraClave);
        Map<String, Long> conteo = resultados.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> (Long) row[1]
                ));

        conteo.putIfAbsent("Registrada", 0L);
        conteo.putIfAbsent("En_Curso", 0L);
        conteo.putIfAbsent("Cerrada", 0L);

        return conteo;
    }


    @Override
    public Page<Incidencia> buscarPorFiltrosCompleto(String titulo, String estado, String descripcion, String fechaInicioStr, String fechaFinStr, int page, int size) {
        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            if (fechaInicioStr != null && !fechaInicioStr.isEmpty()) {
                fechaInicio = LocalDate.parse(fechaInicioStr, formatter);
            }
            if (fechaFinStr != null && !fechaFinStr.isEmpty()) {
                fechaFin = LocalDate.parse(fechaFinStr, formatter);
            }
        } catch (DateTimeParseException e) {
            // Manejar error parseo fechas si quieres
        }

        Pageable pageable = PageRequest.of(page, size);
        Specification<Incidencia> spec = IncidenciaSpecification.filtroCompleto(titulo, estado, descripcion, fechaInicio, fechaFin);

        return repositorio.findAll(spec, pageable);
    }


}
