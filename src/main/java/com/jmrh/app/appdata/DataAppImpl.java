package com.jmrh.app.appdata;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class DataAppImpl  implements DataApp {

	private String nombre; //nombre aplicación
	private String autor; //autor de la aplicación
	private int year; //año de la aplicación
	private String web; //web de la aplicación
	private String enlaceWeb; //enlace de la web
	private HashMap<String,GeneralOption> opcionesGenerales; //opciones de la aplicación
			
	public DataAppImpl() {
		nombre = "Cruce de Ganado";
		autor = "José Manuel Rosado";
		year = 2022;
		web = "ejerciciosmesa.com";
		enlaceWeb = "https://ejerciciosmesa.com";
		
		opcionesGenerales = new HashMap<>();
		
		//Animales
		GeneralOption opAnimales = new GeneralOption("Animales","/animal/listado","LISTAR");

		opAnimales.addPantalla("LISTAR","Listado de Animales");
		opAnimales.addPantalla("ALTA","Alta de Animal");
		opAnimales.addPantalla("EDITAR","Modificar Animal");
		opAnimales.addPantalla("ASCENDENTES", "Ascendencia");
		
		opAnimales.setMensajeVacio("No hay animales que mostrar. Use el botón de Alta para introducir nuevos animales.");
		
		opcionesGenerales.put("ANIMALES",opAnimales);
		
		
		//Ganaderías
		GeneralOption opGanaderias = new GeneralOption("Ganaderías","/ganaderia/listado","LISTAR");

		opGanaderias.addPantalla("LISTAR","Listado de Ganaderías");
		opGanaderias.addPantalla("ALTA","Alta de Ganadería");
		opGanaderias.addPantalla("EDITAR","Modificar Ganadería");
		
		opGanaderias.setMensajeVacio("No hay ganaderías que mostrar. Use el botón de Alta para introducir nuevas ganaderías.");
		
		opcionesGenerales.put("GANADERIAS",opGanaderias);
	}
	
	/**
	 * Devuelve el nombre de la aplicación
	 * @return
	 */
	@Override
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Devuelve el autor de la aplicación
	 * @return
	 */
	@Override
	public String getAutor() {
		return autor;
	}

	/**
	 * Devuelve el año de la aplicación
	 * @return
	 */
	@Override
	public int getYear() {
		return year;
	}

	/**
	 * Devuelve la página web de la aplicación
	 * @return
	 */
	@Override
	public String getWeb() {
		return web;
	}
	
	/**
	 * Devuelve el enlace de la web de la aplicación
	 * @return
	 */
	@Override
	public String getEnlaceWeb() {
		return enlaceWeb;
	}

	/**
	 * Devuelve el autor, año y web concatenados
	 * @return
	 */
	@Override
	public String getAutoria() {
		return autor+" "+year+" - "+web;
	}
	
	/**
	 * Devuelve las opciones generales de la aplicación
	 * @return
	 */
	@Override
	public HashMap<String, GeneralOption> getOpcionesGenerales() {
		return opcionesGenerales;
	}
	
	@Override
	public boolean esPantallaPrincipal(String codigoOpcion, String codigoPantalla) {
		return opcionesGenerales.get(codigoOpcion).getCodigoPantallaPrincipal().equals(codigoPantalla);
	}
	
	@Override
	public String getNombrePantallaPrincipal(String codigoOpcion) {
		return opcionesGenerales.get(codigoOpcion).getNombrePantallaPrincipal();
	}
	
	@Override
	public String getEnlacePantallaPrincipal(String codigoOpcion) {
		return opcionesGenerales.get(codigoOpcion).getEnlace();
	}
	
	@Override
	public String getNombrePantalla(String codigoOpcion, String codigoPantalla) {
		return opcionesGenerales.get(codigoOpcion).getPantalla(codigoPantalla);
	}

	@Override
	public String getMensajeVacio(String codigoOpcion) {
		return opcionesGenerales.get(codigoOpcion).getMensajeVacio();
	}
		
}
