package com.immutableant.sfgpetclinic.controllers;

import com.immutableant.sfgpetclinic.model.Owner;
import com.immutableant.sfgpetclinic.model.Pet;
import com.immutableant.sfgpetclinic.model.Visit;
import com.immutableant.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping({"/owners/{ownerId}/pets/{petId}/visits"})
@Controller
public class VisitController {

  private final OwnerService ownerService;

  public VisitController(OwnerService ownerService) {
    this.ownerService = ownerService;
  }

  @InitBinder
  public void setAllowedFields(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
  }

  @ModelAttribute("visit")
  public Visit loadPetWithVisit(
      @PathVariable("ownerId") Long ownerId, @PathVariable("petId") Long petId, Model model) {
    Owner owner = ownerService.findById(ownerId);

    Pet pet = owner.getPet(petId);
    model.addAttribute("pet", pet);
    model.addAttribute("owner", owner);

    Visit visit = new Visit();
    pet.getVisits().add(visit);

    return visit;
  }

  @GetMapping("/new")
  public String initNewVisitForm() {
    return "visits/createOrUpdateVisitForm";
  }

  @PostMapping("/new")
  public String processNewVisitForm(
      @ModelAttribute Owner owner,
      @PathVariable Long petId,
      @Valid Visit visit,
      BindingResult result) {
    if (result.hasErrors()) {
      return "visits/createOrUpdateVisitForm";
    }

    visit.setPet(owner.getPet(petId));
    owner.getPet(petId).getVisits().add(visit);
    Owner savedOwner = ownerService.save(owner);
    return "redirect:/owners/" + savedOwner.getId();
  }
}
