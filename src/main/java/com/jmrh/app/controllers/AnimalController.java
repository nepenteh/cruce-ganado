package com.jmrh.app.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jmrh.app.appdata.AppData;
import com.jmrh.app.models.entities.Animal;
import com.jmrh.app.models.entities.Ganaderia;
import com.jmrh.app.models.services.AnimalService;
import com.jmrh.app.models.services.ArbolAnimalesService;
import com.jmrh.app.models.services.GanaderiaService;
import com.jmrh.app.models.services.UploadService;
import com.jmrh.app.util.paginator.PageRender;

@Controller
@SessionAttributes("animal")
@RequestMapping("/animales")
public class AnimalController {

	private final AppData datosAplicacion;
	private final AnimalService animalService;
	private final GanaderiaService ganaderiaService;
	private final UploadService uploadService;
	private final ArbolAnimalesService arbolAnimales;

	public static final String OPGEN = "ANIMALES";
	
	public AnimalController(AppData datosAplicacion, AnimalService animalService, GanaderiaService ganaderiaService,
			UploadService uploadService, ArbolAnimalesService arbolAnimales) {
		this.datosAplicacion = datosAplicacion;
		this.animalService = animalService;
		this.ganaderiaService = ganaderiaService;
		this.uploadService = uploadService;
		this.arbolAnimales = arbolAnimales;
	}

	
	@GetMapping({"","/","/list"})
	public String list(@RequestParam(name="page", defaultValue="0") int page, Model model) {
		
		fillApplicationData(model,"LIST");
		
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Animal> pageAnimales = animalService.findAll(pageRequest); 
		PageRender<Animal> paginator = new PageRender<>("/animales/list",pageAnimales,5);
		
		model.addAttribute("numanimales", animalService.count());
		model.addAttribute("listanimales",pageAnimales);
		model.addAttribute("paginator", paginator);
				
		return "/animales/list";
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		Animal animal = new Animal();
		model.addAttribute("animal", animal);
		
		fillApplicationData(model,"CREATE");
						
		return "/animales/form";
	}
	
	@GetMapping("/form/{idA}")
	public String form(@PathVariable Long idA, Model model, RedirectAttributes flash) {
		Animal animal = animalService.findOne(idA);
		if(animal==null) {
			flash.addFlashAttribute("error", "Animal no existente");
			return "redirect:/animales/list";
		}
		
		model.addAttribute("animal", animal);
		
		fillApplicationData(model,"UPDATE");
		
		return "/animales/form";
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/form")
	public String form(@Valid Animal animal, BindingResult result,
					   @RequestParam(name="ganaderia_id", required=false) Long ganaderia_id,
					   @RequestParam(name="madre_id", required=false) Long madre_id,
					   @RequestParam(name="padre_id", required=false) Long padre_id,
					   Model model,
					   @RequestAttribute("file") MultipartFile fotoa,
					   RedirectAttributes flash,
					   SessionStatus status) {
		
		
		if(animal.getIdA()==null)
			fillApplicationData(model,"CREATE");
		else
			fillApplicationData(model,"UPDATE");
			
		String mensaje = (animal.getIdA()==null) ? "Animal creado con éxito" : "Animal modificado con éxito";
		
		/* en caso de ganadería obligatoria y validarla */
		/*
		boolean errorGanaderia = false;
		if(ganaderia_id==null) {
			model.addAttribute("errorGanaderia","La ganadería asignada no existe");
			errorGanaderia = true;
		} */
		
		if(result.hasErrors() /*|| errorGanaderia*/)
			return "/animales/form";
		
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
		
		
		flash.addFlashAttribute("success", mensaje);
		status.setComplete();
		
		return "redirect:/animales/list";
		
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/eliminar/{idA}")
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
				return "redirect:/animales/list";
			}
			
			if(animal.getFotoA()!=null)
				uploadService.delete(animal.getFotoA());
			flash.addFlashAttribute("success","Animal eliminado con éxito");
		}
		
		return "redirect:/animales/list";
	}
	
	@GetMapping("/ver/{idA}")
	public String ver(@PathVariable Long idA, Model model) {
		
		Animal animal = animalService.findOne(idA);
		
		List<Animal> arbol = arbolAnimales.generarArbol(animal);
		
		model.addAttribute("arbol",arbol);
		
		fillApplicationData(model,"ASCENDENTES");
		

		return "animales/ver";
	}
	
	
	
	@ModelAttribute("sexos")
	public Map<String, String> sexos() {
		Map<String, String> sexos = new HashMap<String,String>();
		sexos.put("M", "Macho");
		sexos.put("H", "Hembra");
		return sexos;
	}
	
	@GetMapping(value="/cargar-ganaderias/{cadena}",produces= {"application/json"})
	public @ResponseBody List<Ganaderia> cargarGanaderias(@PathVariable String cadena) {
		return animalService.findByNombre(cadena);
	}
	
	@GetMapping(value="/cargar-madres/{cadena}",produces= {"application/json"})
	public @ResponseBody List<Animal> cargarMadres(@PathVariable String cadena) {
		return animalService.findHembraByNombre(cadena);
	}
	
	@GetMapping(value="/cargar-padres/{cadena}",produces= {"application/json"})
	public @ResponseBody List<Animal> cargarPadres(@PathVariable String cadena) {
		return animalService.findMachoByNombre(cadena);
	}
	
	private void fillApplicationData(Model model, String screen) {
		model.addAttribute("applicationData",datosAplicacion);
		model.addAttribute("optionCode",OPGEN);
		model.addAttribute("screen",screen);
	}
	
}
