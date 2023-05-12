package it.prova.myebay.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.myebay.model.Ruolo;
import it.prova.myebay.model.StatoUtente;
import it.prova.myebay.model.Utente;
import it.prova.myebay.repository.utente.UtenteRepository;

@Service
public class UtenteServiceImpl implements UtenteService {

	@Autowired
	private UtenteRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${password.reset.newvalue}")
	private String passwordResetValue;

	
	

	@Transactional(readOnly = true)
	public List<Utente> listAllUtenti() {
		return (List<Utente>) repository.findAll();
	}

	@Transactional(readOnly = true)
	public Utente caricaSingoloUtente(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public Utente caricaSingoloUtenteConRuoli(Long id) {
		return repository.findByIdConRuoli(id).orElse(null);
	}

	@Transactional
	public void aggiorna(Utente utenteInstance) {
		// deve aggiornare solo nome, cognome, username, ruoli
		Utente utenteReloaded = repository.findById(utenteInstance.getId()).orElse(null);
		if (utenteReloaded == null)
			throw new RuntimeException("Elemento non trovato");
		utenteReloaded.setNome(utenteInstance.getNome());
		utenteReloaded.setCognome(utenteInstance.getCognome());
		utenteReloaded.setUsername(utenteInstance.getUsername());
		utenteReloaded.setRuoli(utenteInstance.getRuoli());
		repository.save(utenteReloaded);
	}

	@Transactional
	public void inserisciNuovo(Utente utenteInstance) {
		utenteInstance.setStato(StatoUtente.CREATO);
		utenteInstance.setPassword(passwordEncoder.encode(utenteInstance.getPassword()));
		utenteInstance.setDateCreated(LocalDate.now());
		repository.save(utenteInstance);
	}

	@Transactional
	public void resetPasword(Long id) {
		Utente utente = repository.findById(id).orElseThrow(() -> new RuntimeException());
		utente.setPassword(passwordEncoder.encode(passwordResetValue));
		repository.save(utente);
	}

	@Transactional
	public void rimuovi(Long idToDelete) {
		repository.deleteById(idToDelete);
	}

	@Transactional(readOnly = true)
	public List<Utente> findByExample(Utente example) {
		// TODO Da implementare
		return listAllUtenti();
	}

	@Transactional(readOnly = true)
	public Utente eseguiAccesso(String username, String password) {
		return repository.findByUsernameAndPasswordAndStato(username, password, StatoUtente.ATTIVO);
	}

	@Override
	public Utente findByUsernameAndPassword(String username, String password) {
		return repository.findByUsernameAndPassword(username, password);
	}

	@Transactional
	public void changeUserAbilitation(Long utenteInstanceId) {
		Utente utenteInstance = caricaSingoloUtente(utenteInstanceId);
		if (utenteInstance == null)
			throw new RuntimeException("Elemento non trovato.");

		if (utenteInstance.getStato() == null || utenteInstance.getStato().equals(StatoUtente.CREATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
		else if (utenteInstance.getStato().equals(StatoUtente.ATTIVO))
			utenteInstance.setStato(StatoUtente.DISABILITATO);
		else if (utenteInstance.getStato().equals(StatoUtente.DISABILITATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
	}

	@Transactional
	public Utente findByUsername(String username) {
		return repository.findByUsername(username).orElse(null);
	}

	@Override
	public Page<Utente> findByExampleWithPagination(Utente example, Integer pageNo, Integer pageSize, String sortBy) {
		Specification<Utente> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotEmpty(example.getNome()))
				predicates.add(cb.like(cb.upper(root.get("nome")), "%" + example.getNome().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getCognome()))
				predicates.add(cb.like(cb.upper(root.get("cognome")), "%" + example.getCognome().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getUsername()))
				predicates
						.add(cb.like(cb.upper(root.get("username")), "%" + example.getUsername().toUpperCase() + "%"));

			if (example.getStato() != null)
				predicates.add(cb.equal(root.get("stato"), example.getStato()));

			if (example.getDateCreated() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dateCreated"), example.getDateCreated()));

			if (!example.getRuoli().isEmpty()) {
				Join<Utente, Ruolo> ruoloJoin = root.joinSet("ruoli");
				predicates.add(ruoloJoin.in(example.getRuoli()));
			}

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
	public void aggiornaPasword(Long id, String newPass) {

		Utente utente = repository.findById(id).orElseThrow(() -> new RuntimeException());
		utente.setPassword(passwordEncoder.encode(newPass));
		repository.save(utente);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> ruoliUtenteSession() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth!=null) {

		Utente u = repository.findByUsername(auth.getName()).orElse(null);
		if (u==null){
			return new ArrayList<String>();
		}
		
		return u.getRuoli().stream().map(ruolo -> ruolo.getCodice()).collect(Collectors.toList());
	}
		return null;
	}

}
