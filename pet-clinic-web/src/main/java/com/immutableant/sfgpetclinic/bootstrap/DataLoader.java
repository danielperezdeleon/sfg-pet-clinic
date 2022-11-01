package com.immutableant.sfgpetclinic.bootstrap;

import com.immutableant.sfgpetclinic.model.*;
import com.immutableant.sfgpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

  private final OwnerService ownerService;

  private final VetService vetService;

  private final PetTypeService petTypeService;

  private final SpecialtiesService specialtiesService;

  private final VisitService visitService;

  public DataLoader(
      OwnerService ownerService,
      VetService vetService,
      PetTypeService petTypeService,
      SpecialtiesService specialtiesService,
      VisitService visitService) {
    this.ownerService = ownerService;
    this.vetService = vetService;
    this.petTypeService = petTypeService;
    this.specialtiesService = specialtiesService;
    this.visitService = visitService;
  }

  @Override
  public void run(String... args) throws Exception {

    int count = petTypeService.findAll().size();

    if (count == 0) {
      loadData();
    }
  }

  private void loadData() {
    Owner owner1 = new Owner();
    Owner owner2 = new Owner();
    Vet vet1 = new Vet();
    Vet vet2 = new Vet();
    Owner builtOwner =
        Owner.builder()
            .firstName("test1")
            .lastName("test1")
            .address("123 test")
            .city("austin")
            .telephone("9102313213")
            .build();

    Owner builtOwner2 =
        Owner.builder()
            .firstName("test2")
            .lastName("test2")
            .address("123 test")
            .city("austin")
            .telephone("9102313213")
            .build();

    ownerService.save(builtOwner);
    ownerService.save(builtOwner2);

    Specialty radiology = new Specialty();
    Specialty surgery = new Specialty();
    Specialty dentistry = new Specialty();

    radiology.setDescription("radiology");
    surgery.setDescription("surgery");
    dentistry.setDescription("dentistry");

    Specialty savedRadiology = specialtiesService.save(radiology);
    Specialty savedSurgery = specialtiesService.save(surgery);
    Specialty savedDentistry = specialtiesService.save(dentistry);

    PetType dog = new PetType();
    dog.setName("Dog");
    PetType savedDogPetType = petTypeService.save(dog);

    PetType cat = new PetType();
    cat.setName("Cat");
    PetType savedCatPetType = petTypeService.save(cat);

    owner1.setFirstName("Michael");
    owner1.setLastName("Weston");
    owner1.setAddress("123 blah");
    owner1.setCity("Tacoma");
    owner1.setTelephone("1234567897");

    Pet mikesPet = new Pet();
    mikesPet.setPetType(savedDogPetType);
    mikesPet.setOwner(owner1);
    mikesPet.setBirthDate(LocalDate.now());
    mikesPet.setName("Rosco");
    owner1.getPets().add(mikesPet);

    ownerService.save(owner1);

    owner2.setFirstName("Fiona");
    owner2.setLastName("Glenanne");
    owner2.setAddress("123 Bleh");
    owner2.setCity("Austin");
    owner2.setTelephone("7894561233");

    Pet fionaPet = new Pet();
    fionaPet.setPetType(savedCatPetType);
    fionaPet.setOwner(owner2);
    fionaPet.setBirthDate(LocalDate.now());
    fionaPet.setName("Catita");
    owner2.getPets().add(fionaPet);

    ownerService.save(owner2);
    System.out.println("Loaded Visit....");

    Visit fionaPetVisit = new Visit();
    fionaPetVisit.setPet(fionaPet);
    fionaPetVisit.setDescription("Visit 1");
    fionaPetVisit.setDate(LocalDate.now());

    visitService.save(fionaPetVisit);

    System.out.println("Loaded Owners....");

    vet1.setFirstName("Sam");
    vet1.setLastName("Axe");
    vet1.getSpecialties().add(savedRadiology);

    vetService.save(vet1);

    vet2.setFirstName("Jessie");
    vet2.setLastName("Porter");
    vet1.getSpecialties().add(savedSurgery);

    vetService.save(vet2);

    System.out.println("Loaded Vets....");
  }
}
