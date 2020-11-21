package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Empleado;

public interface EmpleadoRepository extends CrudRepository<Empleado, Integer> {}
