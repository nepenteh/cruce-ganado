package com.jmrh.app.appdata;

import java.util.HashMap;

public interface AppData {

	public String getNombre();
	public String getAutor();
	public int getYear();
	public String getWeb();
	public String getEnlaceWeb();
	public String getAutoria();
	public HashMap<String, GeneralOption> getOpcionesGenerales();
	public boolean esPantallaPrincipal(String codigoOpcion, String codigoPantalla);
	public String getNombrePantallaPrincipal(String codigoOpcion);
	public String getEnlacePantallaPrincipal(String codigoOpcion);
	public String getNombrePantalla(String codigoOpcion, String codigoPantalla);
	public String getMensajeVacio(String codigoOpcion);
}
