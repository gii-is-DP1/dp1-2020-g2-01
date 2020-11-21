package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Taller;

public interface TallerRepository extends CrudRepository<Taller, Integer> {}

