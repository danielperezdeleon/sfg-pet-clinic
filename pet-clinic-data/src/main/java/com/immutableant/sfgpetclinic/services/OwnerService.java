package com.immutableant.sfgpetclinic.services;

import com.immutableant.sfgpetclinic.model.Owner;

import java.util.List;

public interface OwnerService extends CrudService<Owner, Long> {

  List<Owner> findByLastName(String lastName);
}
