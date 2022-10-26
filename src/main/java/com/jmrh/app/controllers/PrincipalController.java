package com.jmrh.app.controllers;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jmrh.app.models.services.IUploadService;

@Controller
public class PrincipalController {

	@Autowired
	private IUploadService uploadService;
	
	@GetMapping({"","/","/index"})
	public String index(Model model) {
		model.addAttribute("titulo", "Cruce de Ganado");
		return "index";
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
