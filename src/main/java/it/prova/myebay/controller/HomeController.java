package it.prova.myebay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.prova.myebay.service.UtenteService;

@Controller
@RequestMapping(value = {"/home"})
public class HomeController {
	
	@Autowired
	UtenteService utenteService;
	
	@RequestMapping()
	public String home() {
		
		
		
		return "redirect:annuncio/search";
	}
}
