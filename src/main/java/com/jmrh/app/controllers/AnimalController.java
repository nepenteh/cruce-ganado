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

import com.jmrh.app.appdata.DataApp;
import com.jmrh.app.models.entities.Animal;
import com.jmrh.app.models.entities.Ganaderia;
import com.jmrh.app.models.services.AnimalService;
import com.jmrh.app.models.services.ArbolAnimalesService;
import com.jmrh.app.models.services.GanaderiaService;
import com.jmrh.app.models.services.UploadService;
import com.jmrh.app.util.paginator.PageRender;

@Controller
@SessionAttributes("animal")
@RequestMapping("/animal")
public class AnimalController {

	private final DataApp datosAplicacion;
	private final AnimalService animalService;
	private final GanaderiaService ganaderiaService;
	private final UploadService uploadService;
	private final ArbolAnimalesService arbolAnimales;

	public static final String OPGEN = "ANIMALES";
	
	public AnimalController(DataApp datosAplicacion, AnimalService animalService, GanaderiaService ganaderiaService,
			UploadService uploadService, ArbolAnimalesService arbolAnimales) {
		this.datosAplicacion = datosAplicacion;
		this.animalService = animalService;
		this.ganaderiaService = ganaderiaService;
		this.uploadService = uploadService;
		this.arbolAnimales = arbolAnimales;
	}

	
	@GetMapping({"","/","/listado"})
	public String listado(@RequestParam(name="pagina", defaultValue="0") int pagina, Model model) {
		
		rellenarDatosAplicacion(model,"LISTAR");
		
		//paginación de elementos de la página pagina, teniendo 10 elementos por página
		Pageable pageRequest = PageRequest.of(pagina, 10);
		//obtengo ese listado de elementos de la página
		Page<Animal> paginaAnimales = animalService.findAll(pageRequest); 
		//creo un paginador (de solo cinco cuadros de página) para la vista
		PageRender<Animal> paginador = new PageRender<>("/animal/listado",paginaAnimales,5);
		
		model.addAttribute("numeroanimales", animalService.count());
		model.addAttribute("animales",paginaAnimales);
		model.addAttribute("paginador", paginador);
				
		return "/animal/listado";
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		Animal animal = new Animal();
		model.addAttribute("animal", animal);
		
		rellenarDatosAplicacion(model,"ALTA");
						
		return "/animal/form";
	}
	
	@GetMapping("/form/{idA}")
	public String form(@PathVariable Long idA, Model model, RedirectAttributes flash) {
		Animal animal = animalService.findOne(idA);
		if(animal==null) {
			flash.addFlashAttribute("error", "Animal no existente");
			return "redirect:/animal/listado";
		}
		
		model.addAttribute("animal", animal);
		
		rellenarDatosAplicacion(model,"EDITAR");
		
		return "/animal/form";
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
			rellenarDatosAplicacion(model,"ALTA");
		else
			rellenarDatosAplicacion(model,"EDITAR");
			
		String mensaje = (animal.getIdA()==null) ? "Animal creado con éxito" : "Animal modificado con éxito";
		
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
		
		
		flash.addFlashAttribute("success", mensaje);
		status.setComplete();
		
		return "redirect:/animal/listado";
		
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
				return "redirect:/animal/listado";
			}
			
			if(animal.getFotoA()!=null)
				uploadService.delete(animal.getFotoA());
			flash.addFlashAttribute("success","Animal eliminado con éxito");
		}
		
		return "redirect:/animal/listado";
	}
	
	@GetMapping("/ver/{idA}")
	public String ver(@PathVariable Long idA, Model model) {
		
		Animal animal = animalService.findOne(idA);
		
		List<Animal> arbol = arbolAnimales.generarArbol(animal);
		
		model.addAttribute("arbol",arbol);
		
		rellenarDatosAplicacion(model,"ASCENDENTES");
		

		return "animal/ver";
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
	
	private void rellenarDatosAplicacion(Model model, String pantalla) {
		model.addAttribute("datosAplicacion",datosAplicacion);
		model.addAttribute("codigoOpcion",OPGEN);
		model.addAttribute("pantalla",pantalla);
	}
	
}
