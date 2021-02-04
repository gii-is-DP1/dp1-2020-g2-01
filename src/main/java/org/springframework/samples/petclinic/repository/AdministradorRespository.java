package org.springframework.samples.petclinic.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Administrador;

public interface AdministradorRespository extends CrudRepository<Administrador, Integer>{

	Optional<Administrador> findAdministradorByUsuarioUsername(String username);

}
