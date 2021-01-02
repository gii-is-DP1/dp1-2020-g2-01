package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Recambio;


public interface RecambioRepository extends CrudRepository<Recambio, Integer> {
	
	

}
