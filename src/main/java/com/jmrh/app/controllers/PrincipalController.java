package com.jmrh.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrincipalController {

	@GetMapping({"","/","/index"})
	public String index(Model model) {
		model.addAttribute("titulo", "Cruce de Ganado");
		return "index";
	}
	
}
