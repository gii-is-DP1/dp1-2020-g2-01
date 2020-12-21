package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.ReparacionComun;

public interface ReparacionComunRepository extends CrudRepository<ReparacionComun, Integer> {
	
	@Query("SELECT repCom FROM ReparacionComun repCom WHERE repCom.nombre LIKE %:nombre%")
	Collection<ReparacionComun> findByNombre(@Param("nombre") String nombre);

}
