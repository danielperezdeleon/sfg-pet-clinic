package com.immutableant.sfgpetclinic.controllers;

import com.immutableant.sfgpetclinic.model.Owner;
import com.immutableant.sfgpetclinic.model.Pet;
import com.immutableant.sfgpetclinic.model.PetType;
import com.immutableant.sfgpetclinic.services.OwnerService;
import com.immutableant.sfgpetclinic.services.PetService;
import com.immutableant.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RequestMapping({"/owners/{ownerId}/pets"})
@Controller
public class PetController {

  private final OwnerService ownerService;
  private final PetService petService;

  private final PetTypeService petTypeService;

  public PetController(
      OwnerService ownerService, PetService petService, PetTypeService petTypeService) {
    this.ownerService = ownerService;
    this.petService = petService;
    this.petTypeService = petTypeService;
  }

  @ModelAttribute("types")
  public Set<PetType> populatePetTypes() {
    return petTypeService.findAll();
  }

  @ModelAttribute("owner")
  public Owner findOwner(@PathVariable("ownerId") Long ownerId) {
    return ownerService.findById(ownerId);
  }

  @ModelAttribute("pet")
  public Pet findPet(
      @PathVariable("ownerId") Long ownerId,
      @PathVariable(name = "petId", required = false) Long petId) {
    return petId == null ? Pet.builder().build() : ownerService.findById(ownerId).getPet(petId);
  }

  @InitBinder("owner")
  public void initOwnerBinder(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
  }

  @GetMapping("/new")
  public String initCreationForm(Owner owner, Model model) {
    Pet pet = Pet.builder().build();
    owner.addPet(pet);
    model.addAttribute("pet", pet);
    return "pets/createOrUpdatePetForm";
  }

  @PostMapping("/new")
  public String processCreationForm(
      Owner owner, @Valid Pet pet, BindingResult result, Model model) {
    if (StringUtils.hasLength(pet.getName())
        && pet.isNew()
        && owner.getPet(pet.getName(), true) != null) {
      result.rejectValue("name", "duplicate", "already exists");
    }

    pet.setOwner(owner);
    owner.addPet(pet);

    if (result.hasErrors()) {
      model.addAttribute("pet", pet);
      return "pets/createOrUpdatePetForm";
    }

    Owner savedOwner = ownerService.save(owner);
    return "redirect:/owners/" + savedOwner.getId();
  }

  @GetMapping("/{petId}/edit")
  public String initUpdateForm(Owner owner, @PathVariable("petId") Long petId, Model model) {
    Pet pet = owner.getPet(petId);
    model.addAttribute("pet", pet);
    return "pets/createOrUpdatePetForm";
  }

  @PostMapping("{petId}/edit")
  public String processUpdateForm(@Valid Pet pet, BindingResult result, Owner owner, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("pet", pet);
      return "pets/createOrUpdatePetForm";
    }

    owner.addPet(pet);
    Owner savedOwner = ownerService.save(owner);
    return "redirect:/owners/" + savedOwner.getId();
  }
}
