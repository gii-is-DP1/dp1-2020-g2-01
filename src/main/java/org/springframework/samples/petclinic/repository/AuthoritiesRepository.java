package org.springframework.samples.petclinic.repository;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Cliente;



public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{

	//Optional<Cliente> findByCliente(Cliente cliente) throws DataAccessException;
	
}
