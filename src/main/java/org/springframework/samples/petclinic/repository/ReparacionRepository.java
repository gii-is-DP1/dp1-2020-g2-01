package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Reparacion;

public interface ReparacionRepository extends CrudRepository<Reparacion, Integer> {

}
