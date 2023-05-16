package it.prova.myebay.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.myebay.model.Annuncio;
import it.prova.myebay.repository.annuncio.AnnuncioRepository;

@Service
public class AnnuncioServiceImpl implements AnnuncioService {

	@Autowired
	AnnuncioRepository repository;
	@Autowired
	UtenteService utenteService;

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

	@Override
	public List<Annuncio> listAll() {
	 	return (List<Annuncio>) repository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Annuncio> findByExampleWithPagination(Annuncio example, Integer pageNo, Integer pageSize, String sortBy) {
		Specification<Annuncio> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotEmpty(example.getTestoAnnuncio()))
				predicates.add(cb.like(cb.upper(root.get("testoAnnuncio")), "%" + example.getTestoAnnuncio().toUpperCase() + "%"));

			if (example.getPrezzo() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("prezzo"), example.getPrezzo()));

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		Pageable paging = null;
		// se non passo parametri di paginazione non ne tengo conto
		if (pageSize == null || pageSize < 10)
			paging = Pageable.unpaged();
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return repository.findAll(specificationCriteria, paging);
	}

	@Override
	public Annuncio findByIdEagher(Long idAnnuncio) {
		return repository.findByIdEagher(idAnnuncio);
	}
	@Override
	public List<Annuncio> filtraPerUtenteEOAttivo (List<Annuncio> annunci, Boolean attivoFilter){
		
		if (!utenteService.isAutenticato()) {
			return annunci;
		}
		
		List<Annuncio>result = new ArrayList<>();
		for (Annuncio annuncioItem : annunci) {
			if (annuncioItem.getUtenteInserimento().getId().equals(utenteService.utenteSession().getId())){
				
				result.add(annuncioItem);
			}
		}
		
		if (attivoFilter) {
			for (Annuncio annuncioItem : annunci) {
				if (!annuncioItem.getIsAperto()) {

					result.remove(annuncioItem);
				}
			} 
		}
		return result;
		
	}
}
