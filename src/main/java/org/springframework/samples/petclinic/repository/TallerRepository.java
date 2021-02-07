package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Taller;

public interface TallerRepository extends CrudRepository<Taller, Integer> {

	Optional<Taller> findByUbicacion(String ubicacion) throws DataAccessException;
	
	List<Taller> findAll();
	
}

