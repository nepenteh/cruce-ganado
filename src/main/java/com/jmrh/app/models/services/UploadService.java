package com.jmrh.app.models.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService implements UploadServiceInterface {

	// carpeta de imagenes subidas del proyecto
	final static String UPLOAD_FOLDER = "uploads";
	
	@Override
	public Resource load(String filename) throws MalformedURLException {

		// obtiene el path absoluto del archivo
		Path pathArchivo = getPath(filename);
		Resource recurso = null;
		recurso = new UrlResource(pathArchivo.toUri()); // crea un recurso a partir del path
		if (!recurso.exists() || !recurso.isReadable()) // comprueba si el recurso existe (el archivo está)
			throw new RuntimeException("Error, no se puede cargar la imagen: " + pathArchivo.toString());
		return recurso; // devuelve la imagen en forma de recursso
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		//genera un nombre único para el archivo
		String nombreUnico = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		//genera un path absoluto para el archivo
		Path pathUploadsAbsoluto = getPath(nombreUnico);
		//hace una copia del fichero que viene por el formulario al camino absoluto
		Files.copy(file.getInputStream(), pathUploadsAbsoluto);

		return nombreUnico;
	}

	@Override
	public boolean delete(String filename) {
		//obtiene el path absoluto de la imagen
		Path pathArchivo = getPath(filename);
		File archivo = pathArchivo.toFile(); //convierto a objeto file
		if(archivo.exists() && archivo.canRead()) {
			if(archivo.delete()) {
				return true;
			}
		}
		return false;
	}

	public Path getPath(String filename) {
		return Paths.get(UPLOAD_FOLDER).resolve(filename).toAbsolutePath();
	}

}
