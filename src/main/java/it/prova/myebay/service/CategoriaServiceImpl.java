package it.prova.myebay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.myebay.model.Categoria;
import it.prova.myebay.repository.categoria.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	CategoriaRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<Categoria> listAll() {

		return (List<Categoria>) repository.findAll();

	}

	@Override
	@Transactional
	public Categoria caricaSingoloElemento(Long id) {

		return repository.findById(id).orElse(null);

	}

	@Override
	@Transactional
	public void aggiorna(Categoria categoriaInstance) {

		repository.save(categoriaInstance);

	}

	@Override
	@Transactional
	public void inserisciNuovo(Categoria categoriaInstance) {

		repository.save(categoriaInstance);
	
	}

	@Override
	@Transactional
	public void rimuovi(Long idToDelete) {

		repository.delete(repository.findById(idToDelete).orElse(null));
		
	}

	@Override
	@Transactional(readOnly = true)
	public Categoria cercaPerDescrizione(String descrizione) {

		return repository.findByDescrizione(descrizione).orElse(null);
		
	}

}
