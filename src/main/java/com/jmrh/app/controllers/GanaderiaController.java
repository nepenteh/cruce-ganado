package com.jmrh.app.controllers;

import java.io.IOException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jmrh.app.appdata.AppData;
import com.jmrh.app.models.entities.Ganaderia;
import com.jmrh.app.models.services.AnimalService;
import com.jmrh.app.models.services.GanaderiaService;
import com.jmrh.app.models.services.UploadService;
import com.jmrh.app.util.paginator.PageRender;

@Controller
@SessionAttributes("ganaderia")
@RequestMapping("/ganaderias")
public class GanaderiaController {

	private final AppData appData;
	private final GanaderiaService ganaderiaService;
	private final AnimalService animalService;
	private final UploadService uploadService;
	
	public static final String OPGEN = "GANADERIAS"; 
	
	public GanaderiaController(AppData datosAplicacion, GanaderiaService ganaderiaService,
			AnimalService animalService, UploadService uploadService) {
		this.appData = datosAplicacion;
		this.ganaderiaService = ganaderiaService;
		this.animalService = animalService;
		this.uploadService = uploadService;
	}

	
	
	@GetMapping({"","/","/list"})
	public String list(@RequestParam(name="page", defaultValue="0") int page, Model model) {
	
		rellenarDatosAplicacion(model,"LIST");
		
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Ganaderia> pageGanaderias = ganaderiaService.findAll(pageRequest); 
		PageRender<Ganaderia> paginador = new PageRender<>("/ganaderias/list",pageGanaderias,5);
		

		model.addAttribute("numeroganaderias", ganaderiaService.count());
		model.addAttribute("ganaderias", pageGanaderias); 
		model.addAttribute("paginator",paginador);
		
		return "/ganaderias/list";
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		Ganaderia ganaderia = new Ganaderia();		
		model.addAttribute("ganaderia",ganaderia);
		
		rellenarDatosAplicacion(model,"CREATE");
		
		return "/ganaderias/form";
	}
	
	@GetMapping("/form/{idGan}")
	public String form(@PathVariable Long idGan, Model model, RedirectAttributes flash) {
		Ganaderia ganaderia = ganaderiaService.findOne(idGan);
		if(ganaderia==null) {
			flash.addFlashAttribute("error","Ganadería no existente");
			return "redirect:/ganaderias/list";
		}
		
		model.addAttribute("ganaderia", ganaderia);
		
		rellenarDatosAplicacion(model,"UPDATE");
		
		return "/ganaderias/form";
	}
	
	
	@PostMapping("/form")
	@Secured("ROLE_ADMIN")
	public String form(@Valid Ganaderia ganaderia,  BindingResult result, 
					   Model model,
					   @RequestAttribute("file") MultipartFile hierrogan,
					   RedirectAttributes flash,
					   SessionStatus status) {
		
		if(ganaderia.getIdGan()==null)
			rellenarDatosAplicacion(model,"CREATE");
		else
			rellenarDatosAplicacion(model,"UPDATE");
		
		String mensaje = (ganaderia.getIdGan()==null) ? "Ganadería creada con éxito" : "Ganadería modificada con éxito";
		
		//Si errores de validación, corto.
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Cruce de Ganado".concat(ganaderia.getIdGan()==null ? " - Alta de Ganadería" : " - Modificación de Ganadería"));
			return "/ganaderias/form";
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
		
		ganaderiaService.save(ganaderia);
		status.setComplete();
		flash.addFlashAttribute("success",mensaje);
		return "redirect:/ganaderias/list";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/eliminar/{idGan}")
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
				return "redirect:/ganaderias/list";
			}
			
			//elimino la imagen gracias a la copia aux
			if(ganaderia.getHierroGan()!=null)
				uploadService.delete(ganaderia.getHierroGan());
			flash.addFlashAttribute("success","Ganadería eliminada con éxito");
		}
		
		return "redirect:/ganaderias/list";
	}
	
	private void rellenarDatosAplicacion(Model model, String pantalla) {
		model.addAttribute("datosAplicacion",appData);
		model.addAttribute("optionCode",OPGEN);
		model.addAttribute("screen",pantalla);
	}
	
		
}
