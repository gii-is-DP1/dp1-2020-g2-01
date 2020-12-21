package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.TipoVehiculo;

public interface TipoVehiculoRepository extends CrudRepository<TipoVehiculo,Integer>{

	List<TipoVehiculo> findAll() throws DataAccessException;
}
