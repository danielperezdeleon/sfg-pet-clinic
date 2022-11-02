package com.immutableant.sfgpetclinic.controllers;

import com.immutableant.sfgpetclinic.model.Owner;
import com.immutableant.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RequestMapping({"/owners"})
@Controller
public class OwnerController {

  private final OwnerService ownerService;

  public OwnerController(OwnerService ownerService) {
    this.ownerService = ownerService;
  }

  @InitBinder
  public void setAllowedFields(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
  }

  @RequestMapping({"", "/", "/index", "/index.html"})
  public String listOwners(Model model) {
    model.addAttribute("owners", ownerService.findAll());
    return "owners/index";
  }

  @GetMapping("/{ownerId}")
  public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
    ModelAndView mav = new ModelAndView("owners/ownerDetails");
    Owner owner = ownerService.findById(ownerId);
    mav.addObject(owner);
    return mav;
  }

  @GetMapping("/find")
  public String initFindForm(Model model) {
    model.addAttribute("owner", new Owner());
    return "owners/findOwners";
  }

  @GetMapping("/new")
  public String initCreationForm(Model model) {
    Owner owner = new Owner();
    model.addAttribute("owner", owner);
    return "owners/createOrUpdateOwnerForm";
  }

  @GetMapping("/findOwners")
  public String processFindForm(Owner owner, BindingResult result, Model model) {
    // allow parameterless GET request for /owners to return all records
    if (owner.getLastName() == null) {
      owner.setLastName(""); // empty string signifies broadest possible search
    }

    // find owners by last name
    List<Owner> ownersResults = ownerService.findByLastName(owner.getLastName());
    if (ownersResults.isEmpty()) {
      // no owners found
      result.rejectValue("lastName", "notFound", "not found");
      return "owners/findOwners";
    }

    if (ownersResults.size() == 1) {
      // 1 owner found
      owner = ownersResults.iterator().next();
      return "redirect:/owners/" + owner.getId();
    } else {
      model.addAttribute("selections", ownersResults);
      return "owners/ownersList";
    }

  }

  @PostMapping("/new")
  public String processCreationForm(@Valid Owner owner, BindingResult result) {
    if (result.hasErrors()) {
      return "owners/createOrUpdateOwnerForm";
    }
    Owner savedOwner = ownerService.save(owner);

    return "redirect:/owners/" + savedOwner.getId();
  }

  @GetMapping("/{ownerId}/edit")
  public ModelAndView initUpdateProcess(@PathVariable("ownerId") Long ownerId) {
    ModelAndView mav = new ModelAndView("owners/createOrUpdateOwnerForm");
    Owner owner = ownerService.findById(ownerId);
    mav.addObject(owner);
    return mav;
  }

  @PostMapping("/{ownerId}/edit")
  public String processUpdateProcess(
      @PathVariable("ownerId") Long ownerId, @Valid Owner owner, BindingResult result) {
    if (result.hasErrors()) {
      return "owners/createOrUpdateOwnerForm";
    }
    owner.setId(ownerId);
    Owner savedOwner = ownerService.save(owner);
    return "redirect:/owners/" + savedOwner.getId();
  }
}
