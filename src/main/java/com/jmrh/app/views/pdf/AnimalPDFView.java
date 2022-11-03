package com.jmrh.app.views.pdf;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.jmrh.app.models.entities.Animal;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("animal/ver")
public class AnimalPDFView extends AbstractPdfView {

	// carpeta de imagenes subidas del proyecto
	final static String UPLOAD_FOLDER = "uploads";
	final static String NOTORO_RESOURCE = "static/images/notoro.jpg";
	final static String NOHIERRO_RESOURCE = "static/images/nohierro.png";

	@SuppressWarnings("unchecked")
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Animal> arbol = (List<Animal>) model.get("arbol");

		String titulo = arbol.get(0).getNombreA();

		document.addTitle(titulo.toUpperCase()); //título del documento en el navegador.
		//cabecera preparada para visualizar en el navegador el pdf
		//Si se cambiara inline por attachment se descargaría directamente.
		//filename define el título del documento descargado.
		response.setHeader("Content-Disposition", "inline; filename=\"" + titulo.replaceAll(" ", "").toUpperCase() + ".pdf\"");

		anadirGeneracion(arbol, 1, 0, document);
		lineas(1, document);
		anadirGeneracion(arbol, 2, 1, document);
		lineas(2, document);
		anadirGeneracion(arbol, 4, 3, document);
		lineas(4, document);
		anadirGeneracion(arbol, 8, 7, document);

	}

	private void anadirGeneracion(List<Animal> arbol, int nanimales, int ini, Document document)
			throws BadElementException, IOException {
		PdfPTable tablaFoto = new PdfPTable(nanimales);
		tablaFoto.setWidthPercentage(100);
		for (int i = 0; i < nanimales; i++) {
			anadirFotoAnimal(arbol.get(ini + i), tablaFoto);
		}
		document.add(tablaFoto);

		PdfPTable tablaNombre = new PdfPTable(nanimales);
		tablaNombre.setWidthPercentage(100);
		for (int i = 0; i < nanimales; i++) {
			anadirNombreAnimal(arbol.get(ini + i), tablaNombre);
		}
		document.add(tablaNombre);

		PdfPTable tablaHierro = new PdfPTable(nanimales);
		tablaHierro.setWidthPercentage(100);
		for (int i = 0; i < nanimales; i++) {
			anadirHierroAnimal(arbol.get(ini + i), tablaHierro, nanimales);
		}
		document.add(tablaHierro);
	}
	
	private void anadirHierroAnimal(Animal animal, PdfPTable tabla, int nanimales) throws BadElementException, IOException {

		String ganaderia = "???";
		if(animal!=null && animal.getGanaderiaA()!=null)
			ganaderia = animal.getGanaderiaA().getNombreGan();
		
		String pathImagen;

		Resource resource = new ClassPathResource(NOHIERRO_RESOURCE);
		String pathNohierro = resource.getURI().toString();

		if (animal == null || (animal != null && animal.getGanaderiaA().getHierroGan() == null))
			pathImagen = pathNohierro;
		else
			pathImagen = getPath(animal.getGanaderiaA().getHierroGan()).toString();

		Image imagen = Image.getInstance(pathImagen);
		imagen.scaleAbsolute(15, 15);
		imagen.setAlignment(Element.ALIGN_RIGHT);

		PdfPCell celdaHierroGan = new PdfPCell();
		celdaHierroGan.setBorder(Rectangle.NO_BORDER);
		
		PdfPTable tablaInterior=null;
		if(nanimales==1 || nanimales==2)
			tablaInterior = new PdfPTable(new float[] {46,54});
		else if(nanimales==4)
			tablaInterior = new PdfPTable(new float[] {38,62});
		else if(nanimales==8)
			tablaInterior = new PdfPTable(new float[] {14,86});
		
		PdfPCell celdaHierro = new PdfPCell();
		celdaHierro.setBorder(Rectangle.NO_BORDER);
		celdaHierro.addElement(imagen);
		PdfPCell celdaGanaderia = new PdfPCell();
		celdaGanaderia.setBorder(Rectangle.NO_BORDER);
		Paragraph p = new Paragraph(ganaderia);
		p.setFont(FontFactory.getFont(FontFactory.COURIER, 5f));
		p.setLeading(14f);
		celdaGanaderia.addElement(p);
		
		tablaInterior.addCell(celdaHierro);
		tablaInterior.addCell(celdaGanaderia);
			
		celdaHierroGan.addElement(tablaInterior);
		tabla.addCell(celdaHierroGan);

	}

	
	private void anadirFotoAnimal(Animal animal, PdfPTable tabla) throws BadElementException, IOException {

		String pathImagen;

		Resource resource = new ClassPathResource(NOTORO_RESOURCE);
		String pathNotoro = resource.getURI().toString();

		if (animal == null || (animal != null && animal.getFotoA() == null))
			pathImagen = pathNotoro;
		else
			pathImagen = getPath(animal.getFotoA()).toString();

		Image imagen = Image.getInstance(pathImagen);
		imagen.scaleAbsolute(80, 14);
		imagen.setAlignment(Element.ALIGN_CENTER);

		PdfPCell imageCell = new PdfPCell();
		imageCell.setBorder(Rectangle.NO_BORDER);
		imageCell.addElement(imagen);

		tabla.addCell(imageCell);

	}

	private void anadirNombreAnimal(Animal animal, PdfPTable tabla) {

		String nombreAnimal = (animal != null) ? animal.getNombreA() + " (" + animal.getSexoA() + ")" : "???";

		PdfPCell celda = new PdfPCell();
		celda.setBorder(Rectangle.NO_BORDER);
		Paragraph parrafo = new Paragraph(nombreAnimal.toUpperCase());
		parrafo.setFont(FontFactory.getFont(FontFactory.COURIER, 5f));
		parrafo.setLeading(14f);
		parrafo.setAlignment(Element.ALIGN_CENTER);
		celda.addElement(parrafo);
		celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
		tabla.addCell(celda);

	}

	private void lineas(int nanimales, Document document) {

		int nceldas = nanimales * 2;
		PdfPTable tabla = new PdfPTable(nceldas);
		tabla.setWidthPercentage(100);

		for (int i = 0; i < nanimales; i++) {
			PdfPCell celdaizq = new PdfPCell();
			celdaizq.setFixedHeight(7f);
			celdaizq.setBorder(Rectangle.RIGHT);
			celdaizq.addElement(new Paragraph(" "));
			tabla.addCell(celdaizq);
			PdfPCell celdadcha = new PdfPCell();
			celdadcha.addElement(new Paragraph(" "));
			celdadcha.setFixedHeight(7f);
			celdadcha.setBorder(Rectangle.NO_BORDER);
			tabla.addCell(celdadcha);
		}

		document.add(tabla);

		nceldas *= 2;
		nanimales *= 2;

		tabla = new PdfPTable(nceldas);
		tabla.setWidthPercentage(100);

		for (int i = 0; i < nanimales; i++) {
			PdfPCell celdaizq = new PdfPCell();
			if (i % 2 == 0)
				celdaizq.setBorder(Rectangle.NO_BORDER);
			else
				celdaizq.setBorder(Rectangle.TOP | Rectangle.RIGHT);
			celdaizq.addElement(new Paragraph(" "));
			celdaizq.setFixedHeight(10f);
			tabla.addCell(celdaizq);
			PdfPCell celdadcha = new PdfPCell();
			celdadcha.addElement(new Paragraph(" "));
			if (i % 2 == 0)
				celdadcha.setBorder(Rectangle.TOP | Rectangle.LEFT);
			else
				celdadcha.setBorder(Rectangle.NO_BORDER);
			celdadcha.setFixedHeight(10f);
			tabla.addCell(celdadcha);
		}

		document.add(tabla);

	}

	@Override
	protected Document newDocument() {
		Document doc = new Document(PageSize.A4.rotate());
		doc.setMargins(20, 20, 40, 20);
		return doc;
	}

	public Path getPath(String filename) {
		return Paths.get(UPLOAD_FOLDER).resolve(filename).toAbsolutePath();
	}

}
