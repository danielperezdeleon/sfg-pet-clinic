package com.immutableant.sfgpetclinic.services;

import com.immutableant.sfgpetclinic.model.Vet;

public interface VetService extends CrudService<Vet, Long> {

  Vet findByLastName(String lastName);
}
