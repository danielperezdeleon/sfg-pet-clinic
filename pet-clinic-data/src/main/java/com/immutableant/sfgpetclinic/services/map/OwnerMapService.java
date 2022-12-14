package com.immutableant.sfgpetclinic.services.map;

import com.immutableant.sfgpetclinic.model.Owner;
import com.immutableant.sfgpetclinic.model.Pet;
import com.immutableant.sfgpetclinic.services.OwnerService;
import com.immutableant.sfgpetclinic.services.PetService;
import com.immutableant.sfgpetclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Profile({"default", "map"})
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {

  private final PetTypeService petTypeService;

  private final PetService petService;

  public OwnerMapService(PetTypeService petTypeService, PetService petService) {
    this.petTypeService = petTypeService;
    this.petService = petService;
  }

  @Override
  public Set<Owner> findAll() {
    return super.findAll();
  }

  @Override
  public void delete(Owner owner) {
    super.delete(owner);
  }

  @Override
  public Owner save(Owner owner) {
    if (owner != null) {
      if (owner.getPets() != null) {
        owner
            .getPets()
            .forEach(
                pet -> {
                  if (pet.getPetType() != null) {
                    pet.setPetType(petTypeService.save(pet.getPetType()));
                  } else {
                    throw new RuntimeException("Pet Type is required");
                  }
                  if (pet.getId() == null) {
                    Pet savedPet = petService.save(pet);
                    pet.setId(savedPet.getId());
                  }
                });
      }
      return super.save(owner);
    } else {
      return null;
    }
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
  public List<Owner> findByLastName(String lastName) {
    return super.findAll().stream()
        .filter(owner -> owner.getLastName().toLowerCase().contains(lastName.toLowerCase()))
        .collect(Collectors.toList());
  }
}
