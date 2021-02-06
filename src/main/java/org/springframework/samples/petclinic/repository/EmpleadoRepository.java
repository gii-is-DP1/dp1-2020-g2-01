package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Empleado;

public interface EmpleadoRepository extends CrudRepository<Empleado, Integer> {
	
	Empleado save(Empleado empleado) throws DataAccessException;
	
	@Query("SELECT DISTINCT empleado FROM Empleado empleado WHERE empleado.dni LIKE :dni")
	Optional<Empleado> findEmpleadoDNI(@Param("dni") String dni) throws DataAccessException;
	
	Optional<Empleado> findEmpleadoByUsuarioUsername(String username) throws DataAccessException;

	List<Empleado> findEmpleadoByTallerUbicacion(String ubicacion);
	
}