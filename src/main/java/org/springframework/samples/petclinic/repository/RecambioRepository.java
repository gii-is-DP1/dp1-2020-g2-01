package org.springframework.samples.petclinic.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Recambio;


public interface RecambioRepository extends CrudRepository<Recambio, Integer> {

	Optional<Recambio> findRecambioByName(String name);
	
	

}
