package com.jmrh.app.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jmrh.app.models.entities.Animal;
import com.jmrh.app.models.entities.Ganaderia;
import com.jmrh.app.models.services.IAnimalService;
import com.jmrh.app.models.services.IGanaderiaService;
import com.jmrh.app.models.services.IUploadService;

@Controller
@SessionAttributes("animal")
public class AnimalController {

	@Autowired
	private IAnimalService animalService;
	
	@Autowired
	private IGanaderiaService ganaderiaService;
	
	@Autowired
	private IUploadService uploadService;
	
	@GetMapping("/animal/listado")
	public String listado(Model model) {
		
		model.addAttribute("titulo", "Cruce de Ganado - Listado de Animales");
		model.addAttribute("numeroanimales", animalService.count());
		model.addAttribute("animales",animalService.findAll());
				
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
		Animal animal = animalService.findOne(idA);
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
					   @RequestParam(name="ganaderia_id", required=false) Long ganaderia_id,
					   @RequestParam(name="madre_id", required=false) Long madre_id,
					   @RequestParam(name="padre_id", required=false) Long padre_id,
					   Model model,
					   @RequestAttribute("file") MultipartFile fotoa,
					   RedirectAttributes flash,
					   SessionStatus status) {
		
		model.addAttribute("titulo", "Cruce de Ganado".concat(animal.getIdA()==null ? " - Alta de Animal" : " - Modificación de Animal"));
		
		/* en caso de ganadería obligatoria y validarla */
		/*
		boolean errorGanaderia = false;
		if(ganaderia_id==null) {
			model.addAttribute("errorGanaderia","La ganadería asignada no existe");
			errorGanaderia = true;
		} */
		
		if(result.hasErrors() /*|| errorGanaderia*/)
			return "/animal/form";
		
		if(!fotoa.isEmpty()) {
			if(animal.getIdA()!=null && 
			   animal.getIdA()>0 &&
			   animal.getFotoA()!=null &&
			   animal.getFotoA().length()>0) {
				
				uploadService.delete(animal.getFotoA());
			}
			
			String nombreUnico = null;
			
			try {
				nombreUnico = uploadService.copy(fotoa);
			} catch (IOException e) {
				e.printStackTrace();
			}

			animal.setFotoA(nombreUnico);
		}
		
		if(ganaderia_id!=null) animal.setGanaderiaA(ganaderiaService.findOne(ganaderia_id));
		if(madre_id!=null) animal.setMadreA(animalService.findOne(madre_id));
		if(padre_id!=null) animal.setPadreA(animalService.findOne(padre_id));
		animalService.save(animal);
		
		String mensaje = (animal.getIdA()==null) ? "Animal creado con éxito" : "Animal modificado con éxito";
		flash.addFlashAttribute("success", mensaje);
		status.setComplete();
		
		return "redirect:/animal/listado";
		
	}
	
	@GetMapping("/animal/eliminar/{idA}")
	public String eliminar(@PathVariable Long idA, Model model, RedirectAttributes flash) {
		
		if(idA>0) {
			Animal animal = animalService.findOne(idA);
			
			if(animal!=null) {
				animalService.quitarMadres(idA);
				animalService.quitarPadres(idA);
				animalService.remove(idA);
				flash.addFlashAttribute("success","Animal eliminado con éxito");
			} else {
				flash.addFlashAttribute("error","Animal  no existente");
				return "redirect:/animal/listado";
			}
			
			if(animal.getFotoA()!=null)
				uploadService.delete(animal.getFotoA());
			flash.addFlashAttribute("success","Animal eliminado con éxito");
		}
		
		return "redirect:/animal/listado";
	}
	
	
	
	@ModelAttribute("sexos")
	public Map<String, String> sexos() {
		Map<String, String> sexos = new HashMap<String,String>();
		sexos.put("M", "Macho");
		sexos.put("H", "Hembra");
		return sexos;
	}
	
	@GetMapping(value="/animal/cargar-ganaderias/{cadena}",produces= {"application/json"})
	public @ResponseBody List<Ganaderia> cargarGanaderias(@PathVariable String cadena) {
		return animalService.findByNombre(cadena);
	}
	
	@GetMapping(value="/animal/cargar-madres/{cadena}",produces= {"application/json"})
	public @ResponseBody List<Animal> cargarMadres(@PathVariable String cadena) {
		return animalService.findHembraByNombre(cadena);
	}
	
	@GetMapping(value="/animal/cargar-padres/{cadena}",produces= {"application/json"})
	public @ResponseBody List<Animal> cargarPadres(@PathVariable String cadena) {
		return animalService.findMachoByNombre(cadena);
	}
	
}