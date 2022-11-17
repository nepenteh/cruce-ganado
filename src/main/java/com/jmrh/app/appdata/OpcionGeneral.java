package com.jmrh.app.appdata;

import java.util.HashMap;

public class OpcionGeneral {

	private String nombre; //nombre de la opción
	private String enlace; //enlace de la opción general
	private String codigoPantallaPrincipal; //código de la pantalla principal
	
		
	private HashMap<String, String> pantallas; //nombre de pantallas 
	private String mensajeVacio; //mensaje si no hay registros
	

	public OpcionGeneral(String nombre, String enlace, String codigoPantallaPrincipal) {
		this.nombre = nombre;
		this.enlace = enlace;
		this.codigoPantallaPrincipal = codigoPantallaPrincipal;
		pantallas = new HashMap<>();
	}
	
	public String getNombre() {return nombre;}
	public String getEnlace() {return enlace;}
	public String getCodigoPantallaPrincipal() {return codigoPantallaPrincipal;}
	
	
	public void addPantalla(String codigo, String nombre) {
		pantallas.put(codigo, nombre);
	}
	
	public String getPantalla(String codigo) {
		return pantallas.get(codigo);
	}
	
	public String getNombrePantallaPrincipal() {
		return pantallas.get(codigoPantallaPrincipal);
	}

	public String getMensajeVacio() {
		return mensajeVacio;
	}
	
	public void setMensajeVacio(String mensajeVacio) {
		this.mensajeVacio = mensajeVacio;
	}

	
	
}
