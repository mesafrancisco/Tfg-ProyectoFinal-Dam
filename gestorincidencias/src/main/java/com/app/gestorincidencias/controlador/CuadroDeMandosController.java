package com.app.gestorincidencias.controlador;

import com.app.gestorincidencias.entidad.Incidencia;
import com.app.gestorincidencias.servicio.IncidenciaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CuadroDeMandosController {

    @Autowired
    private IncidenciaServicio servicio;

    @GetMapping({ "/cuadrodemando", "/" })
    public String listarIncidencias(Model modelo,
                                    @RequestParam(value = "palabraClave", required = false) String palabraClave,
                                    @RequestParam(value = "titulo", required = false) String titulo,
                                    @RequestParam(value = "estado", required = false) String estado,
                                    @RequestParam(value = "descripcion", required = false) String descripcion,
                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size) {

        // Parámetros de paginación
        Pageable pageable = PageRequest.of(page, size);

        Page<Incidencia> incidenciasPage;

        // Si hay palabra clave, buscamos incidencias por palabra clave
        if (palabraClave != null && !palabraClave.isEmpty()) {
            incidenciasPage = servicio.listarTodasLasIncidencias(palabraClave, page, size);
        }
        // Si hay filtros de título, estado o descripción, buscamos con los filtros
        else if (titulo != null || estado != null || descripcion != null) {
            incidenciasPage = servicio.buscarPorFiltros(titulo, estado, descripcion, page, size);
        }
        // Si no hay filtros ni palabra clave, mostramos todas las incidencias
        else {
            incidenciasPage = servicio.listarTodasLasIncidencias("", page, size);
        }

        // Obtener el total de incidencias para calcular el total de páginas
        int totalPages = incidenciasPage.getTotalPages();

        // Añadir atributos al modelo para la vista
        modelo.addAttribute("incidencias", incidenciasPage.getContent()); // Aquí pasamos la lista de incidencias de la página
        modelo.addAttribute("palabraClave", palabraClave);
        modelo.addAttribute("currentPage", page);
        modelo.addAttribute("totalPages", totalPages);
        modelo.addAttribute("size", size);

        return "cuadrodemando";
    }
}
