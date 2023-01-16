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
	
		fillApplicationData(model,"LIST");
		
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Ganaderia> pageGanaderias = ganaderiaService.findAll(pageRequest); 
		PageRender<Ganaderia> paginador = new PageRender<>("/ganaderias/list",pageGanaderias,5);
		

		model.addAttribute("numganaderias", ganaderiaService.count());
		model.addAttribute("listganaderias", pageGanaderias); 
		model.addAttribute("paginator",paginador);
				
		return "/ganaderias/list";
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		Ganaderia ganaderia = new Ganaderia();		
		model.addAttribute("ganaderia",ganaderia);
		
		fillApplicationData(model,"CREATE");
		
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
		
		fillApplicationData(model,"UPDATE");
		
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
			fillApplicationData(model,"CREATE");
		else
			fillApplicationData(model,"UPDATE");
		
		String msg = (ganaderia.getIdGan()==null) ? "Ganadería creada con éxito" : "Ganadería modificada con éxito";
		
		if(result.hasErrors()) {
			return "/ganaderias/form";
		}
		
		AddUpdateImageHierrogan(hierrogan,ganaderia);
		
		ganaderiaService.save(ganaderia);
		status.setComplete();
		flash.addFlashAttribute("success",msg);
		return "redirect:/ganaderias/list";
	}
	
	private void AddUpdateImageHierrogan(MultipartFile image, Ganaderia ganaderia) {
		if(!image.isEmpty()) {
			
			if(ganaderia.getIdGan()!=null &&
			   ganaderia.getIdGan()>0 && 
			   ganaderia.getHierroGan()!=null &&
			   ganaderia.getHierroGan().length() > 0) {
			
				uploadService.delete(ganaderia.getHierroGan());
			}
			
			String uniqueName = null;
			try {
				uniqueName = uploadService.copy(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			ganaderia.setHierroGan(uniqueName);
		}
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/delete/{idGan}")
	public String delete(@PathVariable Long idGan, RedirectAttributes flash) {
		
		if(idGan>0) { 			
			Ganaderia ganaderia = ganaderiaService.findOne(idGan);
			if(ganaderia!=null) {
				animalService.deleteGanaderia(idGan);
				ganaderiaService.remove(idGan);
			} else {
				flash.addFlashAttribute("error","Ganadería no existente");
				return "redirect:/ganaderias/list";
			}
			
			if(ganaderia.getHierroGan()!=null)
				uploadService.delete(ganaderia.getHierroGan());
			flash.addFlashAttribute("success","Ganadería eliminada con éxito");
		}
		
		return "redirect:/ganaderias/list";
	}
	
	private void fillApplicationData(Model model, String screen) {
		model.addAttribute("applicationData",appData);
		model.addAttribute("optionCode",OPGEN);
		model.addAttribute("screen",screen);
	}
	
		
}
