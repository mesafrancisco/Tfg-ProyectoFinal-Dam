package com.app.gestorincidencias;

import com.app.gestorincidencias.entidad.Persona;
import com.app.gestorincidencias.repositorio.PersonaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestorincidenciasApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GestorincidenciasApplication.class, args);
	}

	@Autowired
	private PersonaRepositorio repositorio;

	@Override
	public void run(String... args) throws Exception {
		Persona persona1 = new Persona("Francisco","Mesa","francisco@hotmail.com");
		repositorio.save(persona1);

		Persona persona2 = new Persona("Maria","Morte","Maria@hotmail.com");
		repositorio.save(persona2);

		Persona persona3 = new Persona("Miso","Nicolic","Miso@hotmail.com");
		repositorio.save(persona3);
	}




}
