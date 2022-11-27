package com.jmrh.app.controllers;

import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jmrh.app.models.services.UploadService;

@Controller
public class PrincipalController {

	private final UploadService uploadService;
	
	public PrincipalController(UploadService uploadService) {
		this.uploadService = uploadService;
	}

	@GetMapping({"","/","/index"})
	public String index() {
		return "redirect:/animal";
	}
	
	@GetMapping("/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {
		
		Resource recurso = null;
		
		try {
			recurso = uploadService.load(filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+recurso.getFilename()+"\"")
				.body(recurso);
		
	}
	
}
