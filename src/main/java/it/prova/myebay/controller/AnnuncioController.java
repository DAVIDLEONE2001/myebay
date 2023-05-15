package it.prova.myebay.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.myebay.dto.AnnuncioDTO;
import it.prova.myebay.dto.CategoriaDTO;
import it.prova.myebay.dto.UtenteDTO;
import it.prova.myebay.exception.AnnuncioChiusoException;
import it.prova.myebay.exception.CreditoResiduoNonSufficienteException;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Categoria;
import it.prova.myebay.service.AnnuncioService;
import it.prova.myebay.service.CategoriaService;
import it.prova.myebay.service.UtenteService;

@Controller
@RequestMapping(value = "/annuncio")
public class AnnuncioController {

	@Autowired
	private AnnuncioService annuncioService;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private UtenteService utenteService;

	@GetMapping
	public ModelAndView listAllAnnunci() {
		ModelAndView mv = new ModelAndView();
		List<Annuncio> annunci = annuncioService.listAll();
		// trasformiamo in DTO

		mv.addObject("annuncio_list_attribute", AnnuncioDTO.createFilmDTOListFromModelList(annunci, true));
		mv.setViewName("public/annuncio/list");
		return mv;
	}

	@GetMapping("/utente/annuncio/insert")
	public String createAnnuncio(Model model) {
		model.addAttribute("insert_annuncio_attr", new AnnuncioDTO());
		List<CategoriaDTO> categorieTotali = CategoriaDTO
				.createCategoriaDTOListFromModelList(categoriaService.listAll());
		model.addAttribute("categorie_annuncio_list", categorieTotali);
		return "utente/annuncio/insert";
	}

	@PostMapping("/utente/annuncio/save")
	public String saveAnnuncio(@Valid @ModelAttribute("insert_annuncio_attr") AnnuncioDTO annuncioDTO,
			BindingResult result, RedirectAttributes redirectAttrs, Model model) {

		if (result.hasErrors()) {
			List<CategoriaDTO> categorieTotali = CategoriaDTO
					.createCategoriaDTOListFromModelList(categoriaService.listAll());
			model.addAttribute("categorie_annuncio_list", categorieTotali);
			return "utente/annuncio/insert";
		}
		annuncioDTO.setUtenteInserimento(UtenteDTO.buildUtenteDTOFromModel(utenteService.utenteSession(), false));
		annuncioDTO.setIsAperto(true);
		annuncioService.inserisciNuovo(annuncioDTO.buildAnnuncioModel());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/annuncio";
	}

	@GetMapping("/search")
	public String searchAnnuncio(Model model) {
		model.addAttribute("search_annuncio_attr", new AnnuncioDTO());
		List<CategoriaDTO> categorieTotali = CategoriaDTO
				.createCategoriaDTOListFromModelList(categoriaService.listAll());
		model.addAttribute("categorie_totali_attr", categorieTotali);
		return "public/annuncio/search";
	}

	@PostMapping("/list")
	public String listAnnunci(AnnuncioDTO annuncioExample, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			ModelMap model) {

		List<Annuncio> annunci = annuncioService
				.findByExampleWithPagination(annuncioExample.buildAnnuncioModel(), pageNo, pageSize, sortBy)
				.getContent();
//		List<Annuncio> annunciList = annuncioService.filtraPerUtenteEOAttivo(annunci, true);

		model.addAttribute("annuncio_list_attribute", AnnuncioDTO.createFilmDTOListFromModelList(annunci, true));
		return "public/annuncio/list";
	}

//	@GetMapping("/edit/{idRegista}")
//	public String editRegista(@PathVariable(required = true) Long idRegista, Model model) {
//		model.addAttribute("edit_regista_attr",
//				RegistaDTO.buildRegistaDTOFromModel(annuncioService.caricaSingoloElemento(idRegista)));
//		return "regista/edit";
//	}
//
//	@PostMapping("/update")
//	public String updateRegista(@Valid @ModelAttribute("edit_regista_attr") RegistaDTO registaDTO, BindingResult result,
//			RedirectAttributes redirectAttrs, HttpServletRequest request) {
//
//		if (result.hasErrors()) {
//			return "regista/edit";
//		}
//		annuncioService.aggiorna(registaDTO.buildRegistaModel());
//
//		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
//		return "redirect:/regista";
//	}
//	
	@GetMapping("/show/{idAnnuncio}")
	public String showAnnuncio(@PathVariable(required = true) Long idAnnuncio, Model model) {
		model.addAttribute("show_annuncio_attr",
				AnnuncioDTO.buildAnnuncioDTO(annuncioService.findByIdEagher(idAnnuncio), true));
		List<CategoriaDTO> listaCategiorieDTO = CategoriaDTO.createCategoriaDTOListFromModelList(
				(new ArrayList<Categoria>(annuncioService.findByIdEagher(idAnnuncio).getCategorie())));
		model.addAttribute("list_categorie_annuncio_attr", listaCategiorieDTO);
		return "public/annuncio/show";
	}

//
	@PostMapping("/compra")
	public String compraAnnuncio(Long idAnnuncio, Model model, RedirectAttributes redirectAttributes) {
		
		Annuncio annuncio = annuncioService.findByIdEagher(idAnnuncio);
		if (!utenteService.isAutenticato()) {
			
			redirectAttributes.addFlashAttribute("errorMessage", "Accedi per comprare");

			return "redirect:/login";
			
		}
		if (!annuncio.getUtenteInserimento().getId().equals(utenteService.utenteSession().getId())) {
		
			
			try {
				utenteService.compra(utenteService.utenteSession(), annuncio);
			} catch (CreditoResiduoNonSufficienteException e) {
				
				redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
				
				return "redirect:/annuncio/show/"+idAnnuncio;
				
			}catch (AnnuncioChiusoException e) {
				
				
				redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
				
				return "redirect:/annuncio/show/"+idAnnuncio;			}
		
		redirectAttributes.addFlashAttribute("successMessage", "Acquisto avvenuto con successo");
		return "redirect:/annuncio";
		}
		redirectAttributes.addFlashAttribute("errorMessage", "Non puoi comprare i tuoi");
		return "redirect:/annuncio/show/"+idAnnuncio;
	}
}
//	
//	@PostMapping("/delete")
//	public String deleteRegista(@ModelAttribute("delete_regista_attr") RegistaDTO registaDTO, BindingResult result,
//			RedirectAttributes redirectAttrs, HttpServletRequest request) {
//		
//		
//		
//		try {
//			annuncioService.rimuovi(registaDTO.getId());
//
//			}
//		catch(
//
//	RegistaConFilmException e)
//	{
//
//		redirectAttrs.addFlashAttribute("errorMessage", "Operazione impossibile regista con film");
//		return "redirect:/regista";
//
//	}
//
//	redirectAttrs.addFlashAttribute("successMessage","Operazione eseguita correttamente");return"redirect:/regista";
//	}
//
//	@GetMapping(value = "/searchRegistiAjax", produces = { MediaType.APPLICATION_JSON_VALUE })
//	public @ResponseBody String searchRegista(@RequestParam String term) {
//
//		List<Regista> listaRegistaByTerm = annuncioService.cercaByCognomeENomeILike(term);
//		return buildJsonResponse(listaRegistaByTerm);
//	}
//
//	private String buildJsonResponse(List<Regista> listaRegisti) {
//		JsonArray ja = new JsonArray();
//
//		for (Regista registaItem : listaRegisti) {
//			JsonObject jo = new JsonObject();
//			jo.addProperty("value", registaItem.getId());
//			jo.addProperty("label", registaItem.getNome() + " " + registaItem.getCognome());
//			ja.add(jo);
//		}
//
//		return new Gson().toJson(ja);
//	}
//
//}
