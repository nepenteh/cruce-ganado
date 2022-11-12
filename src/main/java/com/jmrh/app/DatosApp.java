package com.jmrh.app;

import org.springframework.stereotype.Component;

@Component
public class DatosApp {

	private String nombre; //nombre aplicación
	private String autor; //autor de la aplicación
	private int year; //año de la aplicación
	private String web; //web de la aplicación
	private String enlace; //enlace de la web
	
	public DatosApp() {
		nombre = "Cruce de Ganado";
		autor = "José Manuel Rosado";
		year = 2022;
		web = "ejerciciosmesa.com";
		enlace = "http://ejerciciosmesa.com";
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getAutor() {
		return autor;
	}

	public int getYear() {
		return year;
	}

	public String getWeb() {
		return web;
	}
	
	public String getEnlace() {
		return enlace;
	}

	/**
	 * Devuelve el autor, año y web concatenados
	 * @return
	 */
	public String getAutoria() {
		return autor+" "+year+" - "+web;
	}
	
}
