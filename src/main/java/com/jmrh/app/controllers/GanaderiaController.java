package com.jmrh.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.jmrh.app.models.entities.Ganaderia;
import com.jmrh.app.models.services.GanaderiaServiceInterface;

@Controller
public class GanaderiaController {

	@Autowired
	private GanaderiaServiceInterface ganaderiaService;
	
	@GetMapping("/ganaderia/listado")
	public String listado(Model model) {
		model.addAttribute("titulo", "Cruce de Ganado - Listado Ganaderías");
		model.addAttribute("ganaderias", ganaderiaService.findAll());
		return "/ganaderia/listado";
	}
	
	@GetMapping("/ganaderia/form")
	public String form(Model model) {
		Ganaderia ganaderia = new Ganaderia();
		model.addAttribute("titulo", "Cruce de Ganado - Alta");
		model.addAttribute("ganaderia",ganaderia);
		return "/ganaderia/form";
	}
	
	@GetMapping("/ganaderia/form/{idGan}")
	public String form(@PathVariable Long idGan, Model model) {
		Ganaderia ganaderia = ganaderiaService.findOne(idGan);
		model.addAttribute("titulo", "Cruce de Ganado - Modificación");
		model.addAttribute("ganaderia",ganaderia);
		return "/ganaderia/form";
	}
	
	@GetMapping("/ganaderia/eliminar/{idGan}")
	public String eliminar(@PathVariable Long idGan) {
		ganaderiaService.remove(idGan);
		return "redirect:/ganaderia/listado";
	}
	
	@PostMapping("/ganaderia/form")
	public String form(Ganaderia ganaderia, Model model) {
		ganaderiaService.save(ganaderia);
		return "redirect:/ganaderia/listado";
	}
	
	@GetMapping("/ganaderia/rellenar")
	public String rellenar(Model model) {
		ganaderiaService.save(new Ganaderia("g1","Martinez"));
		ganaderiaService.save(new Ganaderia("g2","Vitorino"));
		ganaderiaService.save(new Ganaderia("g3","Cebada Gago"));
		return "redirect:/ganaderia/listado";
	}
	
	
}
