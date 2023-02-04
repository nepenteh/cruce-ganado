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
public class UploadServiceImpl implements UploadService {
	
	final static String UPLOAD_FOLDER = "uploads";
	
	@Override
	public Resource load(String filename) throws MalformedURLException {

		Path pathFile = getPath(filename);
		Resource resource = null;
		resource = new UrlResource(pathFile.toUri()); 
		if (!resource.exists() || !resource.isReadable()) 
			throw new RuntimeException("Error, no se puede cargar la imagen: " + pathFile.toString());
		return resource; 
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		String uniqueName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path pathUploadsAbsolute = getPath(uniqueName);
		Files.copy(file.getInputStream(), pathUploadsAbsolute);

		return uniqueName;
	}

	@Override
	public boolean delete(String filename) {
		Path pathFile = getPath(filename);
		File file = pathFile.toFile(); 
		if(file.exists() && file.canRead()) {
			if(file.delete()) {
				return true;
			}
		}
		return false;
	}

	public Path getPath(String filename) {
		return Paths.get(UPLOAD_FOLDER).resolve(filename).toAbsolutePath();
	}

}
