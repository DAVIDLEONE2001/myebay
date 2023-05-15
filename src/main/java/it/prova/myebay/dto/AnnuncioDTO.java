package it.prova.myebay.dto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Categoria;
import it.prova.myebay.service.CategoriaService;

public class AnnuncioDTO {

	private Long id;
	@NotBlank(message = "{testo.notblank}")
	private String testoAnnuncio;
	@NotNull(message = "{prezzo.notblank}")
//	@Size(min = 4, max = 40, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")

	private Double prezzo;
	private LocalDate data;

	private Boolean isAperto;

	private UtenteDTO utenteInserimento;

	private Long[] idsCategorie;

	@Autowired
	CategoriaService categoriaService;

	public AnnuncioDTO(Long id) {
		super();
		this.id = id;
	}

	public AnnuncioDTO() {
		super();
		this.data=LocalDate.now();
	}

	public AnnuncioDTO(Long id, String testoAnnuncio, Double prezzo, LocalDate data, Boolean isAperto,
			Long[] idsCategorie, UtenteDTO utenteInserimento) {
		super();
		this.id = id;
		this.testoAnnuncio = testoAnnuncio;
		this.prezzo = prezzo;
		this.data = data;
		this.isAperto = isAperto;
		this.utenteInserimento = utenteInserimento;
		this.idsCategorie = idsCategorie;
	}

	public AnnuncioDTO(String testoAnnuncio, Double prezzo, LocalDate data, Boolean isAperto,
			UtenteDTO utenteInserimento, Long[] idsCategorie) {
		super();
		this.testoAnnuncio = testoAnnuncio;
		this.prezzo = prezzo;
		this.data = data;
		this.isAperto = isAperto;
		this.utenteInserimento = utenteInserimento;
		this.idsCategorie = idsCategorie;
	}

	public AnnuncioDTO(Long id, String testoAnnuncio, Double prezzo, LocalDate data, Boolean isAperto) {
		super();
		this.id = id;
		this.testoAnnuncio = testoAnnuncio;
		this.prezzo = prezzo;
		this.data = data;
		this.isAperto = isAperto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTestoAnnuncio() {
		return testoAnnuncio;
	}

	public void setTestoAnnuncio(String testoAnnuncio) {
		this.testoAnnuncio = testoAnnuncio;
	}

	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Boolean getIsAperto() {
		return isAperto;
	}

	public void setIsAperto(Boolean isAperto) {
		this.isAperto = isAperto;
	}

	public UtenteDTO getUtenteInserimento() {
		return utenteInserimento;
	}

	public void setUtenteInserimento(UtenteDTO idUtenteInserimento) {
		this.utenteInserimento = idUtenteInserimento;
	}



	public Annuncio buildAnnuncioModel() {
		Annuncio result = new Annuncio(this.id, this.testoAnnuncio, this.prezzo, this.data, this.isAperto);

		if (this.utenteInserimento != null) {
			result.setUtenteInserimento(this.utenteInserimento.buildUtenteModel(false));
		}
		if (this.idsCategorie!=null) {
			
				result.setCategorie(Arrays.asList(idsCategorie).stream().map(id -> new Categoria(id)).collect(Collectors.toSet()));
			
		}

		return result;
	}

	public static AnnuncioDTO buildAnnuncioDTO(Annuncio annuncioModel, boolean includeUtente) {
		AnnuncioDTO result = new AnnuncioDTO(annuncioModel.getId(), annuncioModel.getTestoAnnuncio(),
				annuncioModel.getPrezzo(),

				annuncioModel.getDateCreated(), annuncioModel.getIsAperto());
		if (includeUtente)
			result.setUtenteInserimento(
					UtenteDTO.buildUtenteDTOFromModel(annuncioModel.getUtenteInserimento(), includeUtente));

		return result;
	}

	public static List<AnnuncioDTO> createFilmDTOListFromModelList(List<Annuncio> modelListInput,
			boolean includeUtenti) {
		return modelListInput.stream().map(annuncioEntity -> {
			return AnnuncioDTO.buildAnnuncioDTO(annuncioEntity, includeUtenti);
		}).collect(Collectors.toList());
	}

	public Long[] getIdsCategorie() {
		return idsCategorie;
	}

	public void setIdsCategorie(Long[] idsCategorie) {
		this.idsCategorie = idsCategorie;
	}

}
