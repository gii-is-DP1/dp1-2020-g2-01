package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Vehiculo;

public interface VehiculoRepository extends CrudRepository<Vehiculo, Integer> {
	
	Vehiculo save(Vehiculo vehiculo) throws DataAccessException;
	
	Iterable<Vehiculo> findAll() throws DataAccessException;

	@Query("SELECT DISTINCT vehiculo FROM Vehiculo vehiculo WHERE vehiculo.matricula LIKE :matricula")
	Vehiculo findVehiculoMatricula(@Param("matricula") String matricula) throws DataAccessException;
	
}
