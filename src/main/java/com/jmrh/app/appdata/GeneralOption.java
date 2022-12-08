package com.jmrh.app.appdata;

import java.util.HashMap;

public class GeneralOption {

	private String name; //nombre de la opción
	private String link; //enlace de la opción general
	private String mainScreenCode; //código de la pantalla principal
	
		
	private HashMap<String, String> screens; //nombre de pantallas 
	private String emptyMessage; //mensaje si no hay registros
	

	public GeneralOption(String name, String link, String mainScreenCode) {
		this.name = name;
		this.link = link;
		this.mainScreenCode = mainScreenCode;
		screens = new HashMap<>();
	}
	
	public String getName() {return name;}
	public String getLink() {return link;}
	public String getMainScreenCode() {return mainScreenCode;}
	
	
	public void addScreen(String code, String name) {
		screens.put(code, name);
	}
	
	public String getScreen(String code) {
		return screens.get(code);
	}
	
	public String getMainScreenName() {
		return screens.get(mainScreenCode);
	}

	public String getEmptyMessage() {
		return emptyMessage;
	}
	
	public void setEmptyMessage(String emptyMessage) {
		this.emptyMessage = emptyMessage;
	}

	
	
}
