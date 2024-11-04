package com.app.gestorincidencias.servicio;

import com.app.gestorincidencias.entidad.Incidencia;

import java.util.List;

public interface IncidenciaServicio {
    public List<Incidencia> listarTodasLasIncidencias(String palabraClave);
    public Incidencia guardarIncidencia(Incidencia incidencia);
    public Incidencia obtenerIncidenciaPorId(Long id);
    public Incidencia actualizarIncidencia(Incidencia incidencia);
    public void eliminarIncidencia(Long id);
}
