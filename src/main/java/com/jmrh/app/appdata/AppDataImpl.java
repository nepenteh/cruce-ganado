package com.jmrh.app.appdata;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class AppDataImpl  implements AppData {

	private String name; //nombre aplicación
	private String author; //autor de la aplicación
	private int year; //año de la aplicación
	private String web; //web de la aplicación
	private String webURL; //enlace de la web
	private HashMap<String,GeneralOption> generalOptions; //opciones de la aplicación
			
	public AppDataImpl() {
		name = "Cruce de Ganado";
		author = "José Manuel Rosado";
		year = 2022;
		web = "ejerciciosmesa.com";
		webURL = "https://ejerciciosmesa.com";
		
		generalOptions = new HashMap<>();
		
		//Animales
		GeneralOption opAnimales = new GeneralOption("Animales","/animal/listado","LISTAR");

		opAnimales.addScreen("LISTAR","Listado de Animales");
		opAnimales.addScreen("ALTA","Alta de Animal");
		opAnimales.addScreen("EDITAR","Modificar Animal");
		opAnimales.addScreen("ASCENDENTES", "Ascendencia");
		
		opAnimales.setEmptyMessage("No hay animales que mostrar. Use el botón de Alta para introducir nuevos animales.");
		
		generalOptions.put("ANIMALES",opAnimales);
		
		
		//Ganaderías
		GeneralOption opGanaderias = new GeneralOption("Ganaderías","/ganaderia/listado","LISTAR");

		opGanaderias.addScreen("LISTAR","Listado de Ganaderías");
		opGanaderias.addScreen("ALTA","Alta de Ganadería");
		opGanaderias.addScreen("EDITAR","Modificar Ganadería");
		
		opGanaderias.setEmptyMessage("No hay ganaderías que mostrar. Use el botón de Alta para introducir nuevas ganaderías.");
		
		generalOptions.put("GANADERIAS",opGanaderias);
	}
	
	/**
	 * Devuelve el nombre de la aplicación
	 * @return
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * Devuelve el autor de la aplicación
	 * @return
	 */
	@Override
	public String getAuthor() {
		return author;
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
	public String getWebUrl() {
		return webURL;
	}

	/**
	 * Devuelve el autor, año y web concatenados
	 * @return
	 */
	@Override
	public String getAuthorship() {
		return author+" "+year+" - "+web;
	}
	
	/**
	 * Devuelve las opciones generales de la aplicación
	 * @return
	 */
	@Override
	public HashMap<String, GeneralOption> getGeneralOptions() {
		return generalOptions;
	}
	
	@Override
	public boolean isMainScreen(String optionCode, String screenCode) {
		return generalOptions.get(optionCode).getMainScreenCode().equals(screenCode);
	}
	
	@Override
	public String getMainScreenName(String optionCode) {
		return generalOptions.get(optionCode).getMainScreenName();
	}
	
	@Override
	public String getMainScreenLink(String optionCode) {
		return generalOptions.get(optionCode).getLink();
	}
	
	@Override
	public String getScreenName(String optionCode, String screenCode) {
		return generalOptions.get(optionCode).getScreen(screenCode);
	}

	@Override
	public String getEmptyMessage(String optionCode) {
		return generalOptions.get(optionCode).getEmptyMessage();
	}
		
}
