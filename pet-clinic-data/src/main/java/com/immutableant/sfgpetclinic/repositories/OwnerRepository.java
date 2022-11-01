package com.immutableant.sfgpetclinic.repositories;

import com.immutableant.sfgpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
  List<Owner> findAllByLastNameContainingIgnoreCase(String lastName);
}
