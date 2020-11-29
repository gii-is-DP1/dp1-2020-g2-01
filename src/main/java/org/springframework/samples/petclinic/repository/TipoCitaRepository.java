package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.TipoCita;

public interface TipoCitaRepository extends CrudRepository<TipoCita, Integer> {
	
	List<TipoCita> findAll() throws DataAccessException;

}
