package com.app.gestorincidencias.servicio;

import com.app.gestorincidencias.entidad.Incidencia;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IncidenciaServicio {
    // Cambié el tipo de retorno a Page<Incidencia> en lugar de List<Incidencia>
    public Page<Incidencia> buscarPorFiltros(String titulo, String estado, String descripcion, int page, int size);
    public Page<Incidencia> listarTodasLasIncidencias(String palabraClave, int page, int size);
    public Incidencia guardarIncidencia(Incidencia incidencia);
    public Incidencia obtenerIncidenciaPorId(Long id);
    public Incidencia actualizarIncidencia(Incidencia incidencia);
    public void eliminarIncidencia(Long id);
}

