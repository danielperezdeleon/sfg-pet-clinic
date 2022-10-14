package com.immutableant.sfgpetclinic.services;

import com.immutableant.sfgpetclinic.model.Vet;

import java.util.Optional;

public interface VetService extends CrudService<Vet, Long> {

  Optional<Vet> findByLastName(String lastName);
}
