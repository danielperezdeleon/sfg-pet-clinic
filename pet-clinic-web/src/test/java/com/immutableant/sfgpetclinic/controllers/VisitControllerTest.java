package com.immutableant.sfgpetclinic.controllers;

import com.immutableant.sfgpetclinic.model.Owner;
import com.immutableant.sfgpetclinic.model.Pet;
import com.immutableant.sfgpetclinic.model.Visit;
import com.immutableant.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class VisitControllerTest {
  @Mock OwnerService ownerService;

  @InjectMocks VisitController visitController;

  MockMvc mockMvc;

  Owner owner;
  Owner ownerWithPet;

  Pet pet;

  Visit visit;

  @BeforeEach
  void setUp() {
    owner = Owner.builder().id(1L).firstName("test 1").lastName("Test 1").build();
    ownerWithPet = Owner.builder().id(1L).firstName("test 1").lastName("Test 1").build();
    pet = Pet.builder().id(1L).name("name").visits(new HashSet<>()).build();
    visit = Visit.builder().build();
    pet.getVisits().add(visit);
    ownerWithPet.getPets().add(pet);
    mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
  }

  @Test
  void initUpdateForm() throws Exception {
    when(ownerService.findById(1L)).thenReturn(ownerWithPet);
    mockMvc
        .perform(get("/owners/1/pets/1/visits/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("visits/createOrUpdateVisitForm"))
        .andExpect((model().attributeExists("pet")))
        .andExpect((model().attribute("pet", pet)))
        .andExpect((model().attributeExists("owner")))
        .andExpect((model().attribute("owner", ownerWithPet)))
        .andExpect((model().attributeExists("visit")));
    verify(ownerService).findById(ArgumentMatchers.any());
  }

  @Test
  void processUpdateForm() throws Exception {
    when(ownerService.findById(1L)).thenReturn(ownerWithPet);
    when(ownerService.save(ArgumentMatchers.any())).thenReturn(ownerWithPet);
    mockMvc
        .perform(post("/owners/1/pets/1/visits/new").param("description", "description"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/owners/1"));
    verify(ownerService).findById(ArgumentMatchers.any());
    verify(ownerService).save(ArgumentMatchers.any());
  }

  @Test
  void processUpdateFormFail() throws Exception {
    when(ownerService.findById(1L)).thenReturn(ownerWithPet);

    mockMvc
        .perform(post("/owners/1/pets/1/visits/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("visits/createOrUpdateVisitForm"));
    verify(ownerService).findById(ArgumentMatchers.any());
  }
}
