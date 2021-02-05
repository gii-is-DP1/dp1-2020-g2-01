package org.springframework.samples.petclinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Administrador;

public interface AdministradorRespository extends CrudRepository<Administrador, Integer>{

	Optional<Administrador> findAdministradorByUsuarioUsername(String username);

	@Query("SELECT admin FROM Administrador admin WHERE admin.dni LIKE :dni")
	Optional<Administrador> findByDNI(@Param("dni") String dni);

}
