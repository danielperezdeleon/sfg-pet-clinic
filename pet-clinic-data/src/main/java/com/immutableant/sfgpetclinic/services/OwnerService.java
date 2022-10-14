package com.immutableant.sfgpetclinic.services;

import com.immutableant.sfgpetclinic.model.Owner;

import java.util.Optional;

public interface OwnerService extends CrudService<Owner, Long> {

  Optional<Owner> findByLastName(String lastName);
}
