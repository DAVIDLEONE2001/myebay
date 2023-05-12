package it.prova.myebay.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import it.prova.myebay.model.Categoria;

public class CategoriaDTO {

	private Long id;
	@NotBlank(message = "{descrizione.notblank}")
	private String descrizione;
	@NotBlank(message = "{codice.notblank}")
	private String codice;

	public CategoriaDTO(Long id, String descrizione, String codice) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.codice = codice;
	}

	public CategoriaDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public Categoria buildCategoriaModel() {

		return new Categoria(this.descrizione, this.codice);

	}

	public static CategoriaDTO buildCategoriaDTOFromModel(Categoria categoriaModel) {

		return new CategoriaDTO(categoriaModel.getId(), categoriaModel.getDescrizione(), categoriaModel.getCodice());

	}

	public static List<CategoriaDTO> createCategoriaDTOListFromModelList(List<Categoria> modelListInput) {
		return modelListInput.stream().map(categoriaEntity -> {
			return CategoriaDTO.buildCategoriaDTOFromModel(categoriaEntity);
		}).collect(Collectors.toList());
	}

}
