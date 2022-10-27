package com.jmrh.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

/**
 * Un paginador.
 * Determina una serie de cuadros con números de páginas en la parte inferior del listado.
 * 
 * @author josep
 *
 * @param <T>
 */

public class PageRender<T> {

	private String url;
	private Page<T> paginaElementos;	//una página de elementos T
	private int totalPaginas;	//total de páginas que hay
	private int paginaActual; //página actual
	private int numElementosPorPagina;
	private int numCuadros; //número de cuadros de paginación que se desean mostrar
	private List<PageItem> paginaItems;  //items de paginación (cuadros en la zona de paginación) 
	
	public PageRender(String url, Page<T> paginaElementos, int numCuadros) {
		this.url = url;
		this.paginaElementos = paginaElementos;
		this.paginaItems = new ArrayList<PageItem>();
		this.numCuadros = numCuadros;
		
		numElementosPorPagina = paginaElementos.getSize();
		totalPaginas = paginaElementos.getTotalPages();
		paginaActual = paginaElementos.getNumber()+1;
		
		int desde, hasta;
		
		if(totalPaginas <= numCuadros) { //número de páginas es menor al número de cuadros que se quiere mostrar
			desde = 1;
			hasta = totalPaginas;
		} else { //número de páginas mayor al número de cuadros a mostrar
			
			desde = paginaActual - (numCuadros/2);
			hasta = desde + numCuadros;
			
			if(desde<=0) {
				desde = 1;
				hasta = numCuadros;
			} else if(hasta>totalPaginas) {
				desde = totalPaginas - numCuadros;
				hasta = totalPaginas;
			}
			
		}
		
		for(int i=desde;i<=hasta;i++) {
			paginaItems.add(new PageItem(i,i==paginaActual));
		}
		
	}
	
	
	
	public String getUrl() {
		return url;
	}



	public Page<T> getPaginaElementos() {
		return paginaElementos;
	}



	public int getTotalPaginas() {
		return totalPaginas;
	}



	public int getPaginaActual() {
		return paginaActual;
	}



	public int getNumElementosPorPagina() {
		return numElementosPorPagina;
	}



	public int getNumCuadros() {
		return numCuadros;
	}



	public List<PageItem> getPaginaItems() {
		return paginaItems;
	}



	public boolean isFirst() {
		return paginaElementos.isFirst();
	}
	
	public boolean isLast() {
		return paginaElementos.isLast();
	}
	
	public boolean isHasNext() {
		return paginaElementos.hasNext();
	}
	
	public boolean isHasPrevious() {
		return paginaElementos.hasPrevious();
	}
	
}
