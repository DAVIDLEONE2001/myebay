package it.prova.myebay.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.myebay.model.Annuncio;

public interface AnnuncioService {
	
	public List<Annuncio> listAll() ;

	public Annuncio caricaSingoloElemento(Long id) ;

	public void aggiorna(Annuncio annuncioInstance) ;

	public void inserisciNuovo(Annuncio annuncioInstance) ;

	public void rimuovi(Long idToDelete) ;

	Page<Annuncio> findByExampleWithPagination(Annuncio example, Integer pageNo, Integer pageSize, String sortBy);

	Annuncio findByIdEagher(Long idAnnuncio);

	List<Annuncio> filtraPerUtenteEOAttivo(List<Annuncio> annunci, Boolean attivoFilter);
	
//	public Annuncio cercaPerDescrizione(String descrizione) ;
	
}
