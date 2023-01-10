package com.jmrh.app.util.paginator;

/**
 * Define un item de paginación
 * (Uno de los cuadros que aparecerán en la zona de paginación de un listado)
 * Este cuadro contendrá un número, el número de la página.
 * Y podrá estar activo (si es la página que se está mostrando ahora)
 * @author josep
 *
 */
public class PageItem {

	private int pageNumber; //número de página que muestra
	private boolean actual; //es la página que se está mostrando
	
	public PageItem(int pageNumber, boolean actual) {
		this.pageNumber = pageNumber;
		this.actual = actual;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public boolean isActual() {
		return actual;
	}
	
	
}
