package com.immutableant.sfgpetclinic.repositories;

import com.immutableant.sfgpetclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {}
