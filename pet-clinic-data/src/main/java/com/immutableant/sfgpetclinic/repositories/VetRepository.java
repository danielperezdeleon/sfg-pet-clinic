package com.immutableant.sfgpetclinic.repositories;

import com.immutableant.sfgpetclinic.model.Vet;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VetRepository extends CrudRepository<Vet, Long> {
  public Optional<Vet> findByLastName(String lastname);
}
