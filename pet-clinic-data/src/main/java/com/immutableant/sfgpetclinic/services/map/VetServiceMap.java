package com.immutableant.sfgpetclinic.services.map;

import com.immutableant.sfgpetclinic.model.Specialty;
import com.immutableant.sfgpetclinic.model.Vet;
import com.immutableant.sfgpetclinic.services.SpecialtiesService;
import com.immutableant.sfgpetclinic.services.VetService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class VetServiceMap extends AbstractMapService<Vet, Long> implements VetService {

  private SpecialtiesService specialtiesService;

  public VetServiceMap(SpecialtiesService specialtiesService) {
    this.specialtiesService = specialtiesService;
  }

  @Override
  public Set<Vet> findAll() {
    return super.findAll();
  }

  @Override
  public void delete(Vet vet) {
    super.delete(vet);
  }

  @Override
  public Vet save(Vet vet) {
    if (vet.getSpecialties().size() > 0) {
      vet.getSpecialties()
          .forEach(
              specialty -> {
                if (specialty.getId() == null) {
                  Specialty savedSpecialty = specialtiesService.save(specialty);
                  specialty.setId(savedSpecialty.getId());
                }
              });
    }
    return super.save(vet);
  }

  @Override
  public void deleteById(Long id) {
    super.deleteById(id);
  }

  @Override
  public Vet findById(Long id) {
    return super.findById(id);
  }

  @Override
  public Optional<Vet> findByLastName(String lastName) {
    return super.findAll().stream().filter(vet -> vet.getLastName() == lastName).findFirst();
  }
}
