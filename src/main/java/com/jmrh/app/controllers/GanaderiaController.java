package com.jmrh.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.jmrh.app.models.dao.GanaderiaDAOInterface;
import com.jmrh.app.models.entities.Ganaderia;

@Controller
public class GanaderiaController {

	@Autowired
	private GanaderiaDAOInterface ganaderiaDAO;
	
	@GetMapping("/ganaderia/listado")
	public String listado(Model model) {
		model.addAttribute("titulo", "Cruce de Ganado - Listado Ganaderías");
		model.addAttribute("ganaderias", ganaderiaDAO.findAll());
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
		Ganaderia ganaderia = ganaderiaDAO.findOne(idGan);
		model.addAttribute("titulo", "Cruce de Ganado - Modificación");
		model.addAttribute("ganaderia",ganaderia);
		return "/ganaderia/form";
	}
	
	@GetMapping("/ganaderia/eliminar/{idGan}")
	public String eliminar(@PathVariable Long idGan) {
		ganaderiaDAO.remove(idGan);
		return "redirect:/ganaderia/listado";
	}
	
	@PostMapping("/ganaderia/form")
	public String form(Ganaderia ganaderia, Model model) {
		ganaderiaDAO.save(ganaderia);
		return "redirect:/ganaderia/listado";
	}
	
	@GetMapping("/ganaderia/rellenar")
	public String rellenar(Model model) {
		ganaderiaDAO.save(new Ganaderia("g1","Martinez"));
		ganaderiaDAO.save(new Ganaderia("g2","Vitorino"));
		ganaderiaDAO.save(new Ganaderia("g3","Cebada Gago"));
		return "redirect:/ganaderia/listado";
	}
	
	
}
