package it.prova.myebay.repository.annuncio;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.prova.myebay.model.Annuncio;

public interface AnnuncioRepository extends CrudRepository<Annuncio, Long>, JpaSpecificationExecutor<Annuncio> {

	@Query("select a From Annuncio a left join fetch a.categorie where a.id=:idAnnuncio")
	Annuncio findByIdEagher(@Param("idAnnuncio") Long idAnnuncio);
	
}
