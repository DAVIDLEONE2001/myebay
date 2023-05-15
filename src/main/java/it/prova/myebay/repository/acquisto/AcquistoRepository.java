package it.prova.myebay.repository.acquisto;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import it.prova.myebay.model.Acquisto;
import it.prova.myebay.model.Annuncio;

public interface AcquistoRepository extends CrudRepository<Acquisto, Long>, JpaSpecificationExecutor<Acquisto> {

	
	 Iterable<Acquisto> findAllByUtenteAcquirente_id(Long id) ;
	
	
}
