package it.prova.myebay.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.myebay.dto.UtentePassDTO;
import it.prova.myebay.model.Utente;
import it.prova.myebay.service.UtenteService;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

	@Autowired
	UtenteService utenteService;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public AccountController() {
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/editpswd")
	public String prepareEditPswd(Model model) {

		model.addAttribute("cambia_password_attr", new UtentePassDTO());
		return "utente/editpswd";
	}

	@PostMapping("/cambiaPassword")
	public String changePassword(@Valid @ModelAttribute("cambia_password_attr") UtentePassDTO utentePassDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String nomeU = auth.getName();

		Utente utenteDaControllare = utenteService.findByUsername(nomeU);

		if (!utentePassDTO.getNuovaPassword().equals(utentePassDTO.getConfermaPassword())) {
			result.addError(new FieldError("cambia_password_attr", "confermaPassword", "Le password devono essere uguali"));
		}
		
		if (utentePassDTO.getNuovaPassword().isBlank()) {
			result.addError(new FieldError("cambia_password_attr", "nuovaPassword", "Password vuota"));
		}

		if (!passwordEncoder.matches(utentePassDTO.getPassword(), utenteDaControllare.getPassword())) {

			result.addError(new FieldError("cambia_password_attr", "password", "Password errata"));

		}

		if (result.hasErrors()) {
			model.addAttribute("errorMessage", "Attenzione! Ci sono errori di validazione");
			return "utente/editpswd";
		}

		utenteService.aggiornaPasword(utenteDaControllare.getId(), utentePassDTO.getNuovaPassword());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/executeLogout";
	}
	
}
