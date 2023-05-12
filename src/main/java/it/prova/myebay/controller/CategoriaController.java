package it.prova.myebay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.prova.myebay.dto.CategoriaDTO;
import it.prova.myebay.model.Categoria;
import it.prova.myebay.service.CategoriaService;

@Controller
@RequestMapping(value = "/categoria")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@RequestMapping({ "/list", "" })
	ModelAndView listAllCategoria() {
		ModelAndView mv = new ModelAndView();
		List<Categoria> categorie = categoriaService.listAll();
		// trasformiamo in DTO
		mv.addObject("categoria_list_attribute", CategoriaDTO.createCategoriaDTOListFromModelList(categorie));
		mv.setViewName("categoria/list");
		return mv;
	}

	@GetMapping("/show/{idRegista}")
	public String showCategoria(@PathVariable(required = true) Long idCategoria, Model model) {
		model.addAttribute("show_regista_attr",
				CategoriaDTO.buildCategoriaDTOFromModel(categoriaService.caricaSingoloElemento(idCategoria)));

		return "regista/show";
	}

//	@GetMapping("/showDelete/{idRegista}")
//	public String showDeleteRegista(@PathVariable(required = true) Long idRegista, Model model) {
//		model.addAttribute("delete_regista_attr",
//				RegistaDTO.buildRegistaDTOFromModel(categoriaService.findByIdEagher(idRegista)));
//		List<FilmDTO> listaFilmDTO = FilmDTO.createFilmDTOListFromModelList(
//				(new ArrayList<Film>(categoriaService.findByIdEagher(idRegista).getFilms())), false);
//		model.addAttribute("list_film_regista_attr", listaFilmDTO);
//		return "regista/showDelete";
//	}
//
//	@PostMapping("/delete")
//	public String deleteRegista(@ModelAttribute("delete_regista_attr") RegistaDTO registaDTO, BindingResult result,
//			RedirectAttributes redirectAttrs, HttpServletRequest request) {
//
//		try {
//			categoriaService.rimuovi(registaDTO.getId());
//
//		} catch (
//
//		RegistaConFilmException e) {
//
//			redirectAttrs.addFlashAttribute("errorMessage", "Operazione impossibile regista con film");
//			return "redirect:/regista";
//
//		}
//
//		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
//		return "redirect:/regista";
//	}
//
//	@GetMapping(value = "/searchRegistiAjax", produces = { MediaType.APPLICATION_JSON_VALUE })
//	public @ResponseBody String searchRegista(@RequestParam String term) {
//
//		List<Regista> listaRegistaByTerm = categoriaService.cercaByCognomeENomeILike(term);
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

}
