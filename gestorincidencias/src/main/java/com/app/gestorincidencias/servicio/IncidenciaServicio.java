package com.app.gestorincidencias.servicio;

import com.app.gestorincidencias.entidad.Incidencia;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IncidenciaServicio {
    // Cambié el tipo de retorno a Page<Incidencia> en lugar de List<Incidencia>
    public Page<Incidencia> buscarPorFiltros(String titulo, String estado, String descripcion, int page, int size);
    public Page<Incidencia> listarTodasLasIncidencias(String palabraClave, int page, int size);
    public Incidencia guardarIncidencia(Incidencia incidencia);
    public Incidencia obtenerIncidenciaPorId(Long id);
    public Incidencia actualizarIncidencia(Incidencia incidencia);
    public void eliminarIncidencia(Long id);
    Page<Incidencia> listarIncidenciasPorUsuario(String email, int page, int size);

    List<Incidencia> listarTodas();

    Page<Incidencia> buscarPorFiltrosCompleto(String titulo, String estado, String descripcion, String fechaInicio, String fechaFin, int page, int size);

    Page<Incidencia> listarIncidenciasPorUsuarioYFiltros(String email, String titulo, String estado, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, int page, int size);

    Map<String, Long> contarIncidenciasPorEstado(String titulo, String estado, String descripcion, String palabraClave);

    Page<Incidencia> listarIncidenciasPorUsuarioYFiltros(String email, String titulo, String estado, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, String palabraClave, int page, int size);

    Page<Incidencia> filtroPorTituloEstadoDescripcion(String titulo, String estado, String descripcion, String fechaInicio, String fechaFin, int page, int size);

    List<Incidencia> listarPorClienteId(Long clienteId);

    List<Incidencia> obtenerIncidenciasPorClienteId(Long id);
}

