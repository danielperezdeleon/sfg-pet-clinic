package com.immutableant.sfgpetclinic.services;

import com.immutableant.sfgpetclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {

  Owner findByLastName(String lastName);
}
