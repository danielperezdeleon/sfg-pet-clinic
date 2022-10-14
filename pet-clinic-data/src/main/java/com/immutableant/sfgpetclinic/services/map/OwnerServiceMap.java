package com.immutableant.sfgpetclinic.services.map;

import com.immutableant.sfgpetclinic.model.Owner;
import com.immutableant.sfgpetclinic.services.OwnerService;

import java.util.Optional;
import java.util.Set;

public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {
  @Override
  public Set<Owner> findAll() {
    return super.findAll();
  }

  @Override
  public void delete(Owner object) {
    super.delete(object);
  }

  @Override
  public Owner save(Owner object) {
    return super.save(object.getId(), object);
  }

  @Override
  public void deleteById(Long id) {
    super.deleteById(id);
  }

  @Override
  public Owner findById(Long id) {
    return super.findById(id);
  }

  @Override
  public Optional<Owner> findByLastName(String lastName) {
    return super.findAll().stream().filter(owner -> owner.getLastName() == lastName).findFirst();
  }
}
