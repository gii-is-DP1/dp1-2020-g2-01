package org.springframework.samples.petclinic.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.LineaFactura;
import org.springframework.samples.petclinic.model.Recambio;


public interface RecambioRepository extends CrudRepository<Recambio, Integer> {

	
	Optional<Recambio> findRecambioByName(String nombre) throws DataAccessException;

}
