package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Integer>{
	
	Cliente save(Cliente cliente) throws DataAccessException;
	
	Iterable<Cliente> findAll() throws DataAccessException;
	
	@Query("SELECT cliente FROM Cliente cliente WHERE cliente.apellidos LIKE %:apellidos%")
	Collection<Cliente> findByApellidos (@Param("apellidos") String apellidos);

	@Query("SELECT cliente FROM Cliente cliente WHERE cliente.username LIKE :username")
	Optional<Cliente> findByUsername(@Param("username") String username);
}
