package com.jmrh.app.appdata;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class AppDataImpl  implements AppData {

	private String name; 
	private String author; 
	private int year; 
	private String web; 
	private String webURL; 
	private HashMap<String,GeneralOption> generalOptions; 
			
	public AppDataImpl() {
		name = "Cruce de Ganado";
		author = "José Manuel Rosado";
		year = 2022;
		web = "ejerciciosmesa.com";
		webURL = "https://ejerciciosmesa.com";
		
		generalOptions = new HashMap<>();
		
		
		GeneralOption opAnimales = new GeneralOption("Animales","/animales/listado","LIST");

		opAnimales.addScreen("LIST","Listado de Animales");
		opAnimales.addScreen("CREATE","Alta de Animal");
		opAnimales.addScreen("UPDATE","Modificar Animal");
		opAnimales.addScreen("ASCENDENTES", "Ascendencia");
		
		opAnimales.setEmptyMessage("No hay animales que mostrar. Use el botón de Alta para introducir nuevos animales.");
		
		generalOptions.put("ANIMALES",opAnimales);
		
		
		
		GeneralOption opGanaderias = new GeneralOption("Ganaderías","/ganaderias/listado","LIST");

		opGanaderias.addScreen("LIST","Listado de Ganaderías");
		opGanaderias.addScreen("CREATE","Alta de Ganadería");
		opGanaderias.addScreen("UPDATE","Modificar Ganadería");
		
		opGanaderias.setEmptyMessage("No hay ganaderías que mostrar. Use el botón de Alta para introducir nuevas ganaderías.");
		
		generalOptions.put("GANADERIAS",opGanaderias);
	}
	
	
	@Override
	public String getName() {
		return name;
	}
	
	
	@Override
	public String getAuthor() {
		return author;
	}

	
	@Override
	public int getYear() {
		return year;
	}

	
	@Override
	public String getWeb() {
		return web;
	}
	
	
	@Override
	public String getWebUrl() {
		return webURL;
	}

	
	@Override
	public String getAuthorship() {
		return author+" "+year+" - "+web;
	}
	
	
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
