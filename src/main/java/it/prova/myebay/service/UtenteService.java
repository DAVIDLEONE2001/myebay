package it.prova.myebay.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Utente;

public interface UtenteService {
	
	public List<Utente> listAllUtenti() ;

	public Utente caricaSingoloUtente(Long id);
	
	public Utente caricaSingoloUtenteConRuoli(Long id);

	public void aggiorna(Utente utenteInstance);

	public void inserisciNuovo(Utente utenteInstance);

	public void rimuovi(Long idToDelete);

	public List<Utente> findByExample(Utente example);
	
	public Utente findByUsernameAndPassword(String username, String password);
	
	public Utente eseguiAccesso(String username, String password);
	
	public void changeUserAbilitation(Long utenteInstanceId);
	
	public Utente findByUsername(String username);
	
	public Page<Utente> findByExampleWithPagination(Utente example, Integer pageNo, Integer pageSize, String sortBy);

	public void resetPasword(Long id);
	
	public void aggiornaPasword(Long id,String newPass);

	List<String> ruoliUtenteSession();

	boolean isAutenticato();

	Utente utenteSession();
	
	void compra(Utente utente, Annuncio annuncio);
	
}
