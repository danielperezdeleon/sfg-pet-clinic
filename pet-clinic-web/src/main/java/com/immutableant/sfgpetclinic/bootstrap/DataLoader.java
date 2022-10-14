package com.immutableant.sfgpetclinic.bootstrap;

import com.immutableant.sfgpetclinic.model.Owner;
import com.immutableant.sfgpetclinic.model.Vet;
import com.immutableant.sfgpetclinic.services.OwnerService;
import com.immutableant.sfgpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

  private final OwnerService ownerService;

  private final VetService vetService;

  public DataLoader(OwnerService ownerService, VetService vetService) {
    this.ownerService = ownerService;
    this.vetService = vetService;
  }

  @Override
  public void run(String... args) throws Exception {
    Owner owner1 = new Owner();
    Owner owner2 = new Owner();
    Vet vet1 = new Vet();
    Vet vet2 = new Vet();

    owner1.setId(1L);
    owner1.setFirstName("Michael");
    owner1.setLastName("Weston");

    ownerService.save(owner1);

    owner2.setId(2L);
    owner2.setFirstName("Fiona");
    owner2.setLastName("Glenanne");

    ownerService.save(owner2);

    System.out.println("Loaded Owners....");

    vet1.setId(1L);
    vet1.setFirstName("Sam");
    vet1.setLastName("Axe");

    vetService.save(vet1);

    vet2.setId(2L);
    vet2.setFirstName("Jessie");
    vet2.setLastName("Porter");

    vetService.save(vet2);

    System.out.println("Loaded Vets....");
  }
}
