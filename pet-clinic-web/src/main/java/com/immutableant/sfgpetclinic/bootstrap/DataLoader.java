package com.immutableant.sfgpetclinic.bootstrap;

import com.immutableant.sfgpetclinic.model.Owner;
import com.immutableant.sfgpetclinic.model.PetType;
import com.immutableant.sfgpetclinic.model.Vet;
import com.immutableant.sfgpetclinic.services.OwnerService;
import com.immutableant.sfgpetclinic.services.PetTypeService;
import com.immutableant.sfgpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

  private final OwnerService ownerService;

  private final VetService vetService;

  private final PetTypeService petTypeService;

  public DataLoader(
      OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
    this.ownerService = ownerService;
    this.vetService = vetService;
    this.petTypeService = petTypeService;
  }

  @Override
  public void run(String... args) throws Exception {
    Owner owner1 = new Owner();
    Owner owner2 = new Owner();
    Vet vet1 = new Vet();
    Vet vet2 = new Vet();

    PetType dog = new PetType();
    dog.setName("Dog");
    PetType savedDogPetType = petTypeService.save(dog);

    PetType cat = new PetType();
    dog.setName("Cat");
    PetType savedCatPetType = petTypeService.save(cat);

    owner1.setFirstName("Michael");
    owner1.setLastName("Weston");

    ownerService.save(owner1);

    owner2.setFirstName("Fiona");
    owner2.setLastName("Glenanne");

    ownerService.save(owner2);

    System.out.println("Loaded Owners....");

    vet1.setFirstName("Sam");
    vet1.setLastName("Axe");

    vetService.save(vet1);

    vet2.setFirstName("Jessie");
    vet2.setLastName("Porter");

    vetService.save(vet2);

    System.out.println("Loaded Vets....");
  }
}
