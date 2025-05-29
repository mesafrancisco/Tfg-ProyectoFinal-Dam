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

import java.util.Map;

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
                                    @RequestParam(value = "fechaInicio", required = false) String fechaInicio,
                                    @RequestParam(value = "fechaFin", required = false) String fechaFin,
                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Incidencia> incidenciasPage;

        // Llama a un método del servicio que filtre por todos los campos, incluyendo fechas
        incidenciasPage = servicio.buscarPorFiltrosCompleto(titulo, estado, descripcion, fechaInicio, fechaFin, page, size);

        Map<String, Long> conteoPorEstado = servicio.contarIncidenciasPorEstado(titulo, estado, descripcion, palabraClave);

        modelo.addAttribute("incidencias", incidenciasPage.getContent());
        modelo.addAttribute("titulo", titulo);
        modelo.addAttribute("estado", estado);
        modelo.addAttribute("descripcion", descripcion);
        modelo.addAttribute("fechaInicio", fechaInicio);
        modelo.addAttribute("fechaFin", fechaFin);
        modelo.addAttribute("currentPage", page);
        modelo.addAttribute("totalPages", incidenciasPage.getTotalPages());
        modelo.addAttribute("size", size);
        modelo.addAttribute("conteoPorEstado", conteoPorEstado);

        return "cuadrodemando";
    }


}
