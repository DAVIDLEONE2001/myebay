package it.prova.myebay.service;

import it.prova.myebay.model.Annuncio;

public interface AnnuncioService {

	public Annuncio caricaSingoloElemento(Long id) ;

	public void aggiorna(Annuncio annuncioInstance) ;

	public void inserisciNuovo(Annuncio annuncioInstance) ;

	public void rimuovi(Long idToDelete) ;

//	public Annuncio cercaPerDescrizione(String descrizione) ;
	
}
