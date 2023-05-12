package it.prova.myebay.repository.categoria;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import it.prova.myebay.model.Categoria;
import it.prova.myebay.model.Utente;

public interface CategoriaRepository extends CrudRepository<Categoria, Long>, JpaSpecificationExecutor<Categoria> {

	Optional<Categoria> findByDescrizione(String username);
	
}
