package com.immutableant.sfgpetclinic.repositories;

import com.immutableant.sfgpetclinic.model.PetType;
import org.springframework.data.repository.CrudRepository;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {}
