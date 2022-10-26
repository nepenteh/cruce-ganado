package com.jmrh.app.controllers;

import java.io.IOException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jmrh.app.models.entities.Ganaderia;
import com.jmrh.app.models.services.IAnimalService;
import com.jmrh.app.models.services.IGanaderiaService;
import com.jmrh.app.models.services.IUploadService;

@Controller
@SessionAttributes("ganaderia")
public class GanaderiaController {

	@Autowired
	private IGanaderiaService ganaderiaService;
	
	@Autowired
	private IAnimalService animalService;
	
	@Autowired
	private IUploadService uploadService;
	
	
	@GetMapping("/ganaderia/listado")
	public String listado(Model model) {
		model.addAttribute("titulo", "Cruce de Ganado - Listado Ganaderías");
		model.addAttribute("numeroganaderias", ganaderiaService.count());
		model.addAttribute("ganaderias", ganaderiaService.findAll());
		return "/ganaderia/listado";
	}
	
	@GetMapping("/ganaderia/form")
	public String form(Model model) {
		Ganaderia ganaderia = new Ganaderia();
		model.addAttribute("titulo", "Cruce de Ganado - Alta Ganadería");
		model.addAttribute("ganaderia",ganaderia);
		return "/ganaderia/form";
	}
	
	@GetMapping("/ganaderia/form/{idGan}")
	public String form(@PathVariable Long idGan, Model model, RedirectAttributes flash) {
		Ganaderia ganaderia = ganaderiaService.findOne(idGan);
		if(ganaderia==null) {
			flash.addFlashAttribute("error","Ganadería no existente");
			return "redirect:/ganaderia/listado";
		}
		model.addAttribute("titulo", "Cruce de Ganado - Modificación Ganadería");
		model.addAttribute("ganaderia",ganaderia);
		return "/ganaderia/form";
	}
	
	
	@PostMapping("/ganaderia/form")
	public String form(@Valid Ganaderia ganaderia,  BindingResult result, 
					   Model model,
					   @RequestAttribute("file") MultipartFile hierrogan,
					   RedirectAttributes flash,
					   SessionStatus status) {
		
		
		//Si errores de validación, corto.
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Cruce de Ganado".concat(ganaderia.getIdGan()==null ? " - Alta de Ganadería" : " - Modificación de Ganadería"));
			return "/ganaderia/form";
		}
		
		//si hay foto en el formulario...
		if(!hierrogan.isEmpty()) {
			
			//si la ganadería ya existe y tiene foto (es una modificación)
			if(ganaderia.getIdGan()!=null &&
			   ganaderia.getIdGan()>0 && 
			   ganaderia.getHierroGan()!=null &&
			   ganaderia.getHierroGan().length() > 0) {
			
				//borro la foto anterior de la ganadería que se modifica
				uploadService.delete(ganaderia.getHierroGan());
			}
			
			//ahora añado la foto
			String nombreUnico = null;
			try {
				//la copio en la carpeta y obtengo su nombre único
				nombreUnico = uploadService.copy(hierrogan);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//asigno el nombre único a la entidad
			ganaderia.setHierroGan(nombreUnico);
			
		}
		
		
		//guardo la entidad
		String mensaje = (ganaderia.getIdGan()==null) ? "Ganadería creada con éxito" : "Ganadería modificada con éxito";
		ganaderiaService.save(ganaderia);
		status.setComplete();
		flash.addFlashAttribute("success",mensaje);
		return "redirect:/ganaderia/listado";
	}

	@GetMapping("/ganaderia/eliminar/{idGan}")
	public String eliminar(@PathVariable Long idGan, RedirectAttributes flash) {
		
		if(idGan>0) { //validación id
			//copia aux
			Ganaderia ganaderia = ganaderiaService.findOne(idGan);
			//elimino la ganadería
			if(ganaderia!=null) {
				animalService.quitarGanaderia(idGan);
				ganaderiaService.remove(idGan);
			} else {
				flash.addFlashAttribute("error","Ganadería no existente");
				return "redirect:/ganaderia/listado";
			}
			
			//elimino la imagen gracias a la copia aux
			if(ganaderia.getHierroGan()!=null)
				uploadService.delete(ganaderia.getHierroGan());
			flash.addFlashAttribute("success","Ganadería eliminada con éxito");
		}
		
		return "redirect:/ganaderia/listado";
	}
	
	
	
	
		
}
