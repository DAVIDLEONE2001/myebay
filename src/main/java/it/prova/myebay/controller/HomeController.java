package it.prova.myebay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.prova.myebay.service.UtenteService;

@Controller
public class HomeController {
	
	@Autowired
	UtenteService utenteService;
	
	@RequestMapping(value = {"/home",""})
	public String home() {
		
		
		if (utenteService.ruoliUtenteSession()==null){
			return "redirect:annuncio/search";
		}
		if (utenteService.ruoliUtenteSession().contains("ROLE_ADMIN")) {
			return "redirect:annuncio/search";
		}
		if (utenteService.ruoliUtenteSession().contains("ROLE_CLASSIC_USER")) {
			return "redirect:annuncio/search";
		}else
		
		return "redirect:annuncio/search";
	}
}
