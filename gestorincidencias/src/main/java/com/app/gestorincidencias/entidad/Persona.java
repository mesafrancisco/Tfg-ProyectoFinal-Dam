package com.app.gestorincidencias.entidad;

import jakarta.persistence.*;

@Entity
@Table(name="personas")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
