package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Vehiculo;

public interface VehiculoRepository extends CrudRepository<Vehiculo, Integer> {
	
	Vehiculo save(Vehiculo vehiculo) throws DataAccessException;
	
	Iterable<Vehiculo> findAll() throws DataAccessException;

}
