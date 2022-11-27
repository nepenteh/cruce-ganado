package com.jmrh.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jmrh.app.appdata.DatosAppImpl;

@Controller
public class LoginController {
	
	private final DatosAppImpl datosAplicacion;
	
	public LoginController(DatosAppImpl datosAplicacion) {
		this.datosAplicacion = datosAplicacion;
	}


	@GetMapping("/login")
	public String login(
			@RequestParam(value="error", required=false) String error, 
			@RequestParam(value="logout", required=false) String logout,
			Model model, Principal principal, RedirectAttributes flash) {
		
		//si ya está logueado se reenvía a la página principal
		//(evitar doble inicio de sesión)
		if(principal != null) {
			flash.addFlashAttribute("info","Ya ha iniciado sesión anteriormente");
			return "redirect:/";
		}
		
		//mensaje de desconexión
		if(logout != null)
			model.addAttribute("success","Ha cerrado la sesión");
		
		//credenciales incorrectas
		if(error != null) 
			model.addAttribute("error","Nombre de usuario o password incorrectos");
		
		model.addAttribute("datosAplicacion", datosAplicacion);
			
		return "/login/login";
	}
	
}
