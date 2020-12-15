package org.springframework.samples.petclinic.repository;


import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.TipoVehiculo;
import org.springframework.samples.petclinic.model.Vehiculo;

public interface VehiculoRepository extends CrudRepository<Vehiculo, Integer>  {
	
	Vehiculo save(Vehiculo vehiculo) throws DataAccessException;
	
	List<Vehiculo> findAll() throws DataAccessException;

	@Query("SELECT DISTINCT vehiculo FROM Vehiculo vehiculo WHERE vehiculo.matricula LIKE :matricula")
	Vehiculo findVehiculoMatricula(@Param("matricula") String matricula) throws DataAccessException;
	
	@Query("SELECT vehiculo FROM Vehiculo vehiculo WHERE vehiculo.cliente LIKE :cliente")
	List<Vehiculo> findVehiculosCliente(@Param("cliente") Cliente cliente) throws DataAccessException;

	@Query("SELECT vtype FROM TipoVehiculo vtype ORDER BY vtype.name")
	List<TipoVehiculo> findVehiculoTypes();

}
