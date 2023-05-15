package it.prova.myebay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.myebay.model.Acquisto;
import it.prova.myebay.model.Utente;
import it.prova.myebay.repository.acquisto.AcquistoRepository;
@Service
public class AcquistoServiceImpl implements AcquistoService {

	@Autowired
	AcquistoRepository repository;
	
	public AcquistoServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Acquisto> listAll() {
		return (List<Acquisto>) repository.findAll();
	}

	@Override
	public Acquisto caricaSingoloElemento(Long id) {
		
		return repository.findById(id).orElse(null);
	}

	@Override
	public void aggiorna(Acquisto acquistoInstance) {

		repository.save(acquistoInstance);
		
	}

	@Override
	public void inserisciNuovo(Acquisto acquistoInstance) {
		repository.save(acquistoInstance);
	}

	@Override
	public void rimuovi(Long idToDelete) {
		repository.delete(repository.findById(idToDelete).orElse(null));
	}

	@Override
	public List<Acquisto> acquistiDiUtente(Utente utente) {
		return (List<Acquisto>) repository.findAllByUtenteAcquirente_id(utente.getId());
	}

}
