package it.prova.myebay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.prova.myebay.model.Annuncio;
import it.prova.myebay.repository.annuncio.AnnuncioRepository;

public class AnnuncioServiceImpl implements AnnuncioService {

	@Autowired
	AnnuncioRepository repository;

	@Override
	public Annuncio caricaSingoloElemento(Long id) {

		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void aggiorna(Annuncio annuncioInstance) {

		repository.save(annuncioInstance);

	}

	@Override
	@Transactional
	public void inserisciNuovo(Annuncio annuncioInstance) {

		repository.save(annuncioInstance);

	}

	@Override
	@Transactional
	public void rimuovi(Long idToDelete) {

		repository.delete(repository.findById(idToDelete).orElse(null));

	}

}
