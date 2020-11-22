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
	
	//BUSCAR CÓDIGO CORRECTO SQL
	@Query(value="SELECT * FROM clientes WHERE cliente.apellidos LIKE :apellidos", nativeQuery=true)
	Collection<Cliente> findByApellidos (@Param("apellidos") String apellidos);
	
	//BUSCAR CÓDIGO CORRECTO SQL
//	@Query("SELECT DISTINC cliente FROM Cliente cliente WHERE cliente.id =:id")
//	public Optional<Cliente> findById(@Param("id") int id);
}
