package com.immutableant.sfgpetclinic.services.map;

import com.immutableant.sfgpetclinic.model.PetType;
import com.immutableant.sfgpetclinic.services.PetTypeService;

import java.util.Set;

public class PetTypeMapService extends AbstractMapService<PetType, Long> implements PetTypeService {
  @Override
  public Set<PetType> findAll() {
    return super.findAll();
  }

  @Override
  public void deleteById(Long id) {
    super.deleteById(id);
  }

  @Override
  public void delete(PetType petType) {
    super.delete(petType);
  }

  @Override
  public PetType save(PetType petType) {
    return super.save(petType);
  }

  @Override
  public PetType findById(Long id) {
    return super.findById(id);
  }
}
