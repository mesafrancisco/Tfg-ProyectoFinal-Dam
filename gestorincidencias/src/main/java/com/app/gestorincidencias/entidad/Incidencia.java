package com.app.gestorincidencias.entidad;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "incidencias")
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String titulo;
    private String descripcion;
    private String estado; // Ejemplo: "abierta", "en progreso", "cerrada"
    private LocalDate fechaCreacion;
    private LocalDate fechaResolucion;
    private String asignadoA; // Puede ser un ID de usuario o nombre
    private String prioridad; // Ejemplo: "alta", "media", "baja"

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(LocalDate fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public String getAsignadoA() {
        return asignadoA;
    }

    public void setAsignadoA(String asignadoA) {
        this.asignadoA = asignadoA;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }


    //    public LocalDateTime getFechaCreacion() { /*/ COMENTADO POR CAMBIAR LOCALDATETIME A LOCALDATE Y CREAR GETTES Y SETTERS NUEVOS
//        return fechaCreacion;
//    }
//
//    public void setFechaCreacion(LocalDateTime fechaCreacion) {
//        this.fechaCreacion = fechaCreacion;
//    }
//
//    public LocalDateTime getFechaResolucion() {
//        return fechaResolucion;
//    }
//
//    public void setFechaResolucion(LocalDateTime fechaResolucion) {
//        this.fechaResolucion = fechaResolucion;
//    }
}
