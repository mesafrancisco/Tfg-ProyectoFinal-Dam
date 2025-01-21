package com.app.gestorincidencias;

import com.app.gestorincidencias.repositorio.IncidenciaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class GestorincidenciasApplication implements CommandLineRunner {
    //Para introducir logs.
    private final static Logger log = Logger.getLogger(GestorincidenciasApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(GestorincidenciasApplication.class, args);
    }
    @Autowired
    private IncidenciaRepositorio repositorio;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("La aplicación ha iniciado correctamente.");
    }
}
 