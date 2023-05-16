package it.prova.myebay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.prova.myebay.dto.AcquistoDTO;
import it.prova.myebay.model.Acquisto;
import it.prova.myebay.service.AcquistoService;
import it.prova.myebay.service.UtenteService;

@Controller
@RequestMapping(value = "/acquisto")
public class AcquistoController {

	@Autowired
	AcquistoService acquistoService;
	@Autowired
	UtenteService utenteService ;
	
	@GetMapping
	public ModelAndView listAllAcquisti() {
		ModelAndView mv = new ModelAndView();
		List<Acquisto> annunci = acquistoService.acquistiDiUtente(utenteService.utenteSession());
		// trasformiamo in DTO
		List<AcquistoDTO> annunciDTO = AcquistoDTO.createAcquistoDTOListFromModelList(annunci, true);
		if (annunciDTO.isEmpty()) {
			mv.addObject("infoMessage","Al momento non hai acquistato nulla!" );
			
		}
		mv.addObject("annuncio_list_attribute",annunciDTO );
		mv.setViewName("utente/acquisto/list");
		return mv;
	}

}
