package com.jmrh.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jmrh.app.models.entities.Animal;
import com.jmrh.app.models.services.IAnimalService;

@Controller
@SessionAttributes("animal")
public class AnimalController {

	@Autowired
	private IAnimalService animalServicio;
	
	@GetMapping("/animal/listado")
	public String listado(Model model) {
		
		model.addAttribute("titulo", "Cruce de Ganado - Listado de Animales");
		model.addAttribute("numeroanimales", animalServicio.count());
		model.addAttribute("animales",animalServicio.findAll());
				
		return "/animal/listado";
	}
	
	@GetMapping("/animal/form")
	public String form(Model model) {
		Animal animal = new Animal();
		model.addAttribute("animal", animal);
		model.addAttribute("titulo", "Cruce de Ganado - Alta de Animal");
				
		return "/animal/form";
	}
	
	@GetMapping("/animal/form/{idA}")
	public String form(@PathVariable Long idA, Model model, RedirectAttributes flash) {
		Animal animal = animalServicio.findOne(idA);
		if(animal==null) {
			flash.addFlashAttribute("error", "Animal no existente");
			return "redirect:/animal/listado";
		}
		model.addAttribute("titulo", "Cruce de Ganado - Modificación de Animal");
		model.addAttribute("animal", animal);
		return "/animal/form";
	}
	
	@PostMapping("/animal/form")
	public String form(@Valid Animal animal, BindingResult result,
					   Model model,
					   RedirectAttributes flash,
					   SessionStatus status) {
		
		
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Cruce de Ganado".concat(animal.getIdA()==null ? " - Alta de Animal" : " - Modificación de Animal"));
			return "/animal/form";
		}
		
		String mensaje = (animal.getIdA()==null) ? "Animal creado con éxito" : "Animal modificado con éxito";
		animalServicio.save(animal);
		flash.addFlashAttribute("success", mensaje);
		status.setComplete();
		
		return "redirect:/animal/listado";
		
	}
	
	@GetMapping("/animal/eliminar/{idA}")
	public String eliminar(@PathVariable Long idA, Model model, RedirectAttributes flash) {
		
		if(idA>0) {
			Animal animal = animalServicio.findOne(idA);
			
			if(animal!=null) {
				animalServicio.remove(idA);
				flash.addFlashAttribute("success","Animal eliminado con éxito");
			} else {
				flash.addFlashAttribute("error","Animal  no existente");
				return "redirect:/animal/listado";
			}
		}
		
		return "redirect:/animal/listado";
	}
	
}
