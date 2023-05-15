package it.prova.myebay.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import it.prova.myebay.model.Acquisto;
import it.prova.myebay.service.CategoriaService;

public class AcquistoDTO {

	private Long id;
	@NotBlank(message = "{testo.notblank}")
	private String descrizione;
	@NotNull(message = "{prezzo.notblank}")
//	@Size(min = 4, max = 40, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")

	private Double prezzo;
	private LocalDate data;

	private UtenteDTO utenteAcquisto;

	@Autowired
	CategoriaService categoriaService;

	public AcquistoDTO(Long id) {
		super();
		this.id = id;
	}

	public AcquistoDTO() {
		super();
		this.data = LocalDate.now();
	}

	public AcquistoDTO(Long id, String testoAnnuncio, Double prezzo, LocalDate data, UtenteDTO utenteInserimento) {
		super();
		this.id = id;
		this.descrizione = testoAnnuncio;
		this.prezzo = prezzo;
		this.data = data;
		this.utenteAcquisto = utenteInserimento;
	}

	public AcquistoDTO(String testoAnnuncio, Double prezzo, LocalDate data, UtenteDTO utenteInserimento,
			Long[] idsCategorie) {
		super();
		this.descrizione = testoAnnuncio;
		this.prezzo = prezzo;
		this.data = data;
		this.utenteAcquisto = utenteInserimento;
	}

	public AcquistoDTO(Long id, String testoAnnuncio, Double prezzo, LocalDate data) {
		super();
		this.id = id;
		this.descrizione = testoAnnuncio;
		this.prezzo = prezzo;
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Acquisto buildAcquistoModel() {
		Acquisto result = new Acquisto(this.id, this.descrizione, this.data, this.prezzo);

		if (this.utenteAcquisto != null) {
			result.setUtenteAcquirente(this.utenteAcquisto.buildUtenteModel(false));

		}

		return result;
	}

	public static AcquistoDTO buildAcquistoDTO(Acquisto acquistoModel, boolean includeUtente) {
		AcquistoDTO result = new AcquistoDTO(acquistoModel.getId(), acquistoModel.getDescrizione(),
				acquistoModel.getPrezzo(),

				acquistoModel.getData());
		if (includeUtente)
			result.setUtenteAcquisto(
					UtenteDTO.buildUtenteDTOFromModel(acquistoModel.getUtenteAcquirente(), includeUtente));

		return result;
	}

	public static List<AcquistoDTO> createAcquistoDTOListFromModelList(List<Acquisto> modelListInput,
			boolean includeUtenti) {
		return modelListInput.stream().map(acquistoEntity -> {
			return AcquistoDTO.buildAcquistoDTO(acquistoEntity, includeUtenti);
		}).collect(Collectors.toList());
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public UtenteDTO getUtenteAcquisto() {
		return utenteAcquisto;
	}

	public void setUtenteAcquisto(UtenteDTO utenteAcquisto) {
		this.utenteAcquisto = utenteAcquisto;
	}

	public CategoriaService getCategoriaService() {
		return categoriaService;
	}

	public void setCategoriaService(CategoriaService categoriaService) {
		this.categoriaService = categoriaService;
	}

}
