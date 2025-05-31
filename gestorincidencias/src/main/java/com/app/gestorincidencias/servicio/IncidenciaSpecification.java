package com.app.gestorincidencias.servicio;

import com.app.gestorincidencias.entidad.Incidencia;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class IncidenciaSpecification {

    public static Specification<Incidencia> filtroCompleto(String titulo, String estado, String descripcion,
                                                           LocalDate fechaInicio, LocalDate fechaFin) {
        return (root, query, cb) -> {
            Predicate p = cb.conjunction();

            if (titulo != null && !titulo.isEmpty()) {
                p = cb.and(p, cb.like(cb.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%"));
            }
            if (estado != null && !estado.isEmpty()) {
                p = cb.and(p, cb.equal(root.get("estado"), estado));
            }
            if (descripcion != null && !descripcion.isEmpty()) {
                p = cb.and(p, cb.like(cb.lower(root.get("descripcion")), "%" + descripcion.toLowerCase() + "%"));
            }
            if (fechaInicio != null) {
                p = cb.and(p, cb.greaterThanOrEqualTo(root.get("fechaCreacion"), fechaInicio.atStartOfDay()));
            }
            if (fechaFin != null) {
                p = cb.and(p, cb.lessThanOrEqualTo(root.get("fechaCreacion"), fechaFin.atTime(23,59,59)));
            }

            return p;
        };
    }

    public static Specification<Incidencia> filtroPorTituloEstadoDescripcion(String titulo, String estado, String descripcion, LocalDate fechaInicio, LocalDate fechaFin) {
        return (root, query, cb) -> {
            Predicate p = cb.conjunction();

            if (titulo != null && !titulo.isEmpty()) {
                p = cb.and(p, cb.like(cb.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%"));
            }
            if (estado != null && !estado.isEmpty()) {
                p = cb.and(p, cb.equal(root.get("estado"), estado));
            }
            if (descripcion != null && !descripcion.isEmpty()) {
                p = cb.and(p, cb.like(cb.lower(root.get("descripcion")), "%" + descripcion.toLowerCase() + "%"));
            }
            if (fechaInicio != null) {
                p = cb.and(p, cb.greaterThanOrEqualTo(root.get("fechaCreacion"), fechaInicio));
            }
            if (fechaFin != null) {
                p = cb.and(p, cb.lessThanOrEqualTo(root.get("fechaCreacion"), fechaFin));
            }

            return p;
        };
    }

    public static Specification<Incidencia> filtroPorPalabraClave(String palabraClave) {
        return (root, query, cb) -> {
            if (palabraClave == null || palabraClave.isEmpty()) {
                return cb.conjunction();
            }
            String likePattern = "%" + palabraClave.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("titulo")), likePattern),
                    cb.like(cb.lower(root.get("descripcion")), likePattern)
            );
        };
    }


}
