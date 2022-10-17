package com.jmrh.app.models.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {

	//Para cargar la imagen para verla
	public Resource load(String filename) throws MalformedURLException;
	
	//Copiar una imagen en la carpeta del proyecto
	public String copy(MultipartFile file) throws IOException;
	
	//Borrar una imagen de la carpeta del proyecto
	public boolean delete(String filename);
	
	//Devuelve el path absoluto del fichero filename
	public Path getPath(String filename);
}
